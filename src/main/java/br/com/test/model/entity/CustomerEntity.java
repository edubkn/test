package br.com.test.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Eduardo on 11/06/2017.
 */
@Entity
public class CustomerEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Date birthDate;

    @ManyToOne
    private TeamEntity team;

    @OneToMany(fetch = FetchType.LAZY)
    private List<CampaignEntity> campaigns;

    public CustomerEntity() {}

    public CustomerEntity(Long id, String name, String email, Date birthDate, TeamEntity team) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.team = team;
    }

    public CustomerEntity(Long id, String name, String email, Date birthDate, TeamEntity team, List<CampaignEntity> campaigns) {
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

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }

    public List<CampaignEntity> getCampaigns() {
        if (campaigns == null) {
            campaigns = new ArrayList<>();
        }
        return campaigns;
    }

    public void setCampaigns(List<CampaignEntity> campaigns) {
        this.campaigns = campaigns;
    }
}
