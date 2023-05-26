package com.project.RTRT.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity

public class Tisch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer tableId;

    @Column(nullable = false)
    private String tableDescription;

    public Tisch() {
    }

    public Tisch(Integer tableId, String tableDescription) {

        this.tableId = tableId;
        this.tableDescription = tableDescription;
    }

}
