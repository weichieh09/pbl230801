package com.wcc.pbl230801.pblService.dto;

import java.io.Serializable;
import java.util.Objects;

public class TeamDTOC implements Serializable {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
