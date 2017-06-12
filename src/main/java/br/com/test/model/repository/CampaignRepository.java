package br.com.test.model.repository;

import br.com.test.model.entity.CampaignEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Eduardo on 11/06/2017.
 */
@Repository
public interface CampaignRepository extends CrudRepository<CampaignEntity, Long> {

    @Query("SELECT c FROM #{#entityName} c WHERE c.id = :id and c.endDate > :currentDate")
    CampaignEntity findOneNotDue(@Param("id") Long id, @Param("currentDate") Date currentDate);

    @Query("SELECT c FROM #{#entityName} c WHERE :startDate <= c.endDate AND :endDate >= c.startDate")
    List<CampaignEntity> findRunningCampaigns(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
