package br.com.test.converter;


import br.com.test.dto.TeamDto;
import br.com.test.model.entity.TeamEntity;

/**
 * Created by Eduardo on 11/06/2017.
 */
public class TeamConverter {

    public static TeamDto toDto(TeamEntity entity) {
        return new TeamDto(entity.getId(), entity.getName());
    }

    public static TeamEntity toEntity(TeamDto dto) {
        return new TeamEntity(dto.getId(), dto.getName());
    }
}
