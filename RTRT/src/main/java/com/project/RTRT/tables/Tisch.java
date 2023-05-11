package com.project.RTRT.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity

public class Tisch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tableId;
    private String tableDescription;

    public Tisch() {
    }

    public Tisch(Integer tableId, String tableDescription) {
        this.tableId = tableId;
        this.tableDescription = tableDescription;
    }





}
