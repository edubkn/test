package br.com.test.dto;

import java.io.Serializable;

/**
 * Created by Eduardo on 11/06/2017.
 */
public class TeamDto implements Serializable {

    private static final long serialVersionUID = 5507279260173886196L;

    private Long id;
    private String name;

    public TeamDto() {}

    public TeamDto(Long id, String name) {
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

    @Override
    public String toString() {
        return "TeamDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
