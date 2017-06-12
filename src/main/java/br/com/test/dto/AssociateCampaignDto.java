package br.com.test.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eduardo on 11/06/2017.
 */
public class AssociateCampaignDto implements Serializable {

    private static final long serialVersionUID = -9175946668674325902L;

    private String email;
    private List<Long> campaignIds;

    public AssociateCampaignDto() {}

    public AssociateCampaignDto(String email, List<Long> campaignIds) {
        this.email = email;
        this.campaignIds = campaignIds;
    }

    public String getEmail() {
        return email;
    }

    public List<Long> getCampaignIds() {
        return campaignIds;
    }
}
