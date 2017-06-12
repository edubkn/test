package br.com.test.converter;


import br.com.test.dto.CustomerDto;
import br.com.test.model.entity.CustomerEntity;

/**
 * Created by Eduardo on 11/06/2017.
 */
public class CustomerConverter {

    public static CustomerEntity toEntity(CustomerDto dto) {
        if (dto == null) {
            return null;
        }
        return new CustomerEntity(dto.getId(), dto.getName(), dto.getEmail(), dto.getBirthDate(),
                TeamConverter.toEntity(dto.getTeam()));
    }
}
