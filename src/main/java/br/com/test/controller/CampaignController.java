package br.com.test.controller;

import br.com.test.dto.CampaignDto;
import br.com.test.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Eduardo on 11/06/2017.
 */
@RestController
public class CampaignController {

    public static final String PATH = "/campaign";
    public static final String ID = "/{id}";
    public static final String LIST = "/list";
    public static final String ASSOCIATE = "/associate";

    private final CampaignService campaignService;

    public CampaignController(@Autowired CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping(PATH + ID)
    public ResponseEntity<?> findCampaign(@PathVariable Long id) {
        CampaignDto dto = campaignService.findCampaign(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(PATH + LIST)
    public ResponseEntity<?> listCampaigns() {
        List<CampaignDto> list = campaignService.listCampaigns();
        if (list != null) {
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping(PATH)
    public ResponseEntity<?> insertCampaign(@RequestBody CampaignDto campaign) {
        campaignService.insertCampaign(campaign);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(PATH + ID)
    public ResponseEntity<?> updateCampaign(@PathVariable Long id) {
        boolean deleted = campaignService.deleteCampaign(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping(PATH + ID)
    public ResponseEntity<?> deleteCampaign(@PathVariable Long id) {
        boolean deleted = campaignService.deleteCampaign(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping(PATH + ASSOCIATE)
    public ResponseEntity<?> associateCampaigns(@PathVariable String email, @RequestBody List<Long> campaignIds) {
        if (email == null || campaignIds == null || campaignIds.isEmpty()) {
            throw new IllegalArgumentException("No customer or campaigns to associate!");
        }
        campaignService.associateCampaigns(email, campaignIds);
        return ResponseEntity.ok().build();
    }
}
