package br.com.test.model.repository;

import br.com.test.model.entity.CampaignEntity;
import br.com.test.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Eduardo on 11/06/2017.
 */
@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

    CustomerEntity findOneByEmail(@Param("email") String email);

    @Query("SELECT c.campaigns FROM #{#entityName} c WHERE c.id = :id")
    List<CampaignEntity> findCampaignsById(@Param("id") Long id);
}
