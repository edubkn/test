package br.com.test.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eduardo on 11/06/2017.
 */
public class CampaignDto implements Serializable {

    private static final long serialVersionUID = -8995254929872361489L;

    private Long id;
    private String name;
    private TeamDto team;
    private Date startDate;
    private Date endDate;

    public CampaignDto() {}

    public CampaignDto(Long id) {
        this.id = id;
    }

    public CampaignDto(Long id, String name, TeamDto team, Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public TeamDto getTeam() {
        return team;
    }

    public void setTeam(TeamDto team) {
        this.team = team;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "CampaignDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", team=" + team +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
