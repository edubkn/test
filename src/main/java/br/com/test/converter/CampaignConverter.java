package br.com.test.converter;


import br.com.test.dto.CampaignDto;
import br.com.test.model.entity.CampaignEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 11/06/2017.
 */
public class CampaignConverter {

    public static CampaignDto toDto(CampaignEntity entity) {
        if (entity == null) {
            return null;
        }
        return new CampaignDto(entity.getId(), entity.getName(), TeamConverter.toDto(entity.getTeam()),
                entity.getStartDate(), entity.getEndDate());
    }

    public static CampaignEntity toEntity(CampaignDto dto) {
        if (dto == null) {
            return null;
        }
        return new CampaignEntity(dto.getId(), dto.getName(), TeamConverter.toEntity(dto.getTeam()),
                dto.getStartDate(), dto.getEndDate());
    }

    public static List<CampaignDto> toDto(Iterable<CampaignEntity> campaigns) {
        if (campaigns == null) {
            return null;
        }
        List<CampaignDto> campaignDtos = new ArrayList<>();
        campaigns.forEach(c -> campaignDtos.add(toDto(c)));
        return campaignDtos;
    }
}
