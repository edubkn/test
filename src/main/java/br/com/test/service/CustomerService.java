package br.com.test.service;


import br.com.test.configuration.RabbitConfiguration;
import br.com.test.converter.CustomerConverter;
import br.com.test.dto.AssociateCampaignDto;
import br.com.test.dto.CampaignDto;
import br.com.test.dto.CustomerDto;
import br.com.test.exception.EmailAlreadyRegisteredException;
import br.com.test.model.entity.CampaignEntity;
import br.com.test.model.entity.CustomerEntity;
import br.com.test.model.repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Eduardo on 11/06/2017.
 */
@Service
public class CustomerService {

    private final RabbitTemplate rabbitTemplate;
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;


    public CustomerService(@Autowired RabbitTemplate rabbitTemplate, @Autowired CustomerRepository customerRepository,
                           @Autowired ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.customerRepository = customerRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Método que insere novos clientes
     * @param customer Cliente para ser cadastrado
     * @return Id do cliente caso efetue o cadastro
     * @throws EmailAlreadyRegisteredException Exceção com as campanhas associadas
     */
    public Long insertCustomer(CustomerDto customer) throws EmailAlreadyRegisteredException {
        CustomerEntity entity = customerRepository.findOneByEmail(customer.getEmail());
        if (entity != null) {
            List<CampaignEntity> campaigns = customerRepository.findCampaignsById(entity.getId());
            throw new EmailAlreadyRegisteredException("This e-mail already has a registered account!", campaigns);
        } else {
            Long id = customerRepository.save(CustomerConverter.toEntity(customer)).getId();
            try {
                associateCampaigns(customer);
            } catch (JsonProcessingException e) {
                // tratar
            }

            return id;
        }
    }

    private void associateCampaigns(CustomerDto customer) throws JsonProcessingException {
        if (customer.getCampaigns() != null && !customer.getCampaigns().isEmpty()) {
            AssociateCampaignDto dto = new AssociateCampaignDto(customer.getEmail(),
                    customer.getCampaigns().stream().map(CampaignDto::getId).collect(Collectors.toList()));
            Message msg = buildMessage(dto);
            rabbitTemplate.send(RabbitConfiguration.EX_ASSOCIATE_CAMPAIGN, "", msg);
        }
    }

    private Message buildMessage(Object obj) throws JsonProcessingException {
        byte[] array = objectMapper.writeValueAsString(obj).getBytes();
        return MessageBuilder.withBody(array).setContentType(MessageProperties.CONTENT_TYPE_JSON).build();
    }
}
