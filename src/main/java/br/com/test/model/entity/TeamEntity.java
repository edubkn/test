package br.com.test.model.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Eduardo on 11/06/2017.
 */
@Entity
public class TeamEntity implements Serializable {

    private static final long serialVersionUID = 2928347844300656707L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    public TeamEntity() {}

    public TeamEntity(Long id, String name) {
        this.id = id;
        this.name = name;
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

}
