package br.com.test.exception;

import br.com.test.model.entity.CampaignEntity;

import java.util.List;

/**
 * Created by Eduardo on 11/06/2017.
 */
public class EmailAlreadyRegisteredException extends Exception {

    private String message;
    private List<CampaignEntity> campaigns;

    public EmailAlreadyRegisteredException() {}

    public EmailAlreadyRegisteredException(String message, List<CampaignEntity> campaigns) {
        this.message = message;
        this.campaigns = campaigns;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CampaignEntity> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<CampaignEntity> campaigns) {
        this.campaigns = campaigns;
    }

}
