package br.com.test.service;


import br.com.test.converter.CampaignConverter;
import br.com.test.dto.CampaignDto;
import br.com.test.model.entity.CampaignEntity;
import br.com.test.model.entity.CustomerEntity;
import br.com.test.model.repository.CampaignRepository;
import br.com.test.model.repository.CustomerRepository;
import br.com.test.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Eduardo on 11/06/2017.
 */
@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final CustomerRepository customerRepository;

    public CampaignService(@Autowired CampaignRepository campaignRepository,
                           @Autowired CustomerRepository customerRepository) {
        this.campaignRepository = campaignRepository;
        this.customerRepository = customerRepository;
    }

    public CampaignDto findCampaign(Long id) {
        return CampaignConverter.toDto(campaignRepository.findOneNotDue(id, DateUtil.now()));
    }

    public List<CampaignDto> listCampaigns() {
        return CampaignConverter.toDto(campaignRepository.findAll());
    }

    public void insertCampaign(CampaignDto campaign) {
        CampaignEntity entity = CampaignConverter.toEntity(campaign);
        List<CampaignEntity> concurrentCampaigns = campaignRepository
                .findRunningCampaigns(entity.getStartDate(), entity.getEndDate());
        concurrentCampaigns = updateConcurrentCampaigns(concurrentCampaigns, entity);
        campaignRepository.save(concurrentCampaigns);
    }

    private List<CampaignEntity> updateConcurrentCampaigns(List<CampaignEntity> runningCampaigns, CampaignEntity newCampaign) {
        if (runningCampaigns != null) {
            // Adiciona um dia a todas as campanhas
            runningCampaigns = runningCampaigns.stream()
                    .peek(c -> c.setEndDate(DateUtil.plusDays(c.getEndDate(), 1)))
                    .collect(Collectors.toList());

            runningCampaigns.add(0, newCampaign);
            // Adiciona um dia às campanhas que ficaram com datas iguais
            updateCampaigns(runningCampaigns);
        }
        return runningCampaigns;
    }

    // Método recursivo que adiciona um dia às campanhas caso elas tenham a mesma data de vigência
    private void updateCampaigns(List<CampaignEntity> runningCampaigns) {
        for (int i = 0; i < runningCampaigns.size(); i++) {
            for (int j = i + 1; j < runningCampaigns.size(); j++) {
                if (runningCampaigns.get(i).getEndDate().compareTo(runningCampaigns.get(j).getEndDate()) == 0) {
                    runningCampaigns.get(j).setEndDate(DateUtil.plusDays(runningCampaigns.get(j).getEndDate(), 1));
                    runningCampaigns.sort(Comparator.comparing(CampaignEntity::getEndDate));
                    updateCampaigns(runningCampaigns);
                }
            }
        }
    }

    public boolean deleteCampaign(Long id) {
        boolean exists = campaignRepository.exists(id);
        if (exists) {
            campaignRepository.delete(id);
        }
        return exists;
    }

    // Estamos adicionando uma dependência desnecessária com o cliente. Deveria ser feito no serviço do cliente
    public void associateCampaigns(String email, List<Long> campaignIds) {
        CustomerEntity customer = customerRepository.findOneByEmail(email);
        if (customer != null) {
            List<CampaignEntity> campaignList = new ArrayList<>();
            campaignIds.forEach(c -> campaignList.add(new CampaignEntity(c)));
            customer.getCampaigns().addAll(campaignList);
            customerRepository.save(customer);
        }
    }
}
