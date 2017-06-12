package br.com.test.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Eduardo on 11/06/2017.
 */
public class CustomerDto implements Serializable {

    private Long id;
    private String name;
    private String email;
    private Date birthDate;
    private TeamDto team;
    private List<CampaignDto> campaigns;

    public CustomerDto() {}

    public CustomerDto(Long id, String name, String email, Date birthDate, TeamDto team) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.team = team;
    }

    public CustomerDto(Long id, String name, String email, Date birthDate, TeamDto team, List<CampaignDto> campaigns) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.team = team;
        this.campaigns = campaigns;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public TeamDto getTeam() {
        return team;
    }

    public void setTeam(TeamDto team) {
        this.team = team;
    }

    public List<CampaignDto> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<CampaignDto> campaigns) {
        this.campaigns = campaigns;
    }
}
