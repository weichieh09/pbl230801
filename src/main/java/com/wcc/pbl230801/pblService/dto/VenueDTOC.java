package com.wcc.pbl230801.pblService.dto;

import java.io.Serializable;
import java.util.Objects;

public class VenueDTOC implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VenueDTOC venueDTOC = (VenueDTOC) o;
        return Objects.equals(name, venueDTOC.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
