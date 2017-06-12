package br.com.test.receiver;

import br.com.test.configuration.RabbitConfiguration;
import br.com.test.dto.AssociateCampaignDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Eduardo on 11/06/2017.
 */
@Component
public class AssociateCampaignReceiver {

    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = RabbitConfiguration.QUEUE_ASSOCIATE_CAMPAIGN, containerFactory = "container")
    public void receiveMessage(Message message) throws Exception {
        AssociateCampaignDto dto = objectMapper.readValue(message.getBody(), AssociateCampaignDto.class);
        restTemplate.put("http://localhost:8080/campaign/associate/" + dto.getEmail(), dto.getCampaignIds());
    }

}
