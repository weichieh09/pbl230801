package com.wcc.pbl230801.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.wcc.pbl230801.domain.TeamEvent} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TeamEventDTO implements Serializable {

    private Long id;

    private Long tId;

    private Long eId;

    private String lstMtnUsr;

    private ZonedDateTime lstMtnDt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public Long geteId() {
        return eId;
    }

    public void seteId(Long eId) {
        this.eId = eId;
    }

    public String getLstMtnUsr() {
        return lstMtnUsr;
    }

    public void setLstMtnUsr(String lstMtnUsr) {
        this.lstMtnUsr = lstMtnUsr;
    }

    public ZonedDateTime getLstMtnDt() {
        return lstMtnDt;
    }

    public void setLstMtnDt(ZonedDateTime lstMtnDt) {
        this.lstMtnDt = lstMtnDt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamEventDTO)) {
            return false;
        }

        TeamEventDTO teamEventDTO = (TeamEventDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, teamEventDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamEventDTO{" +
            "id=" + getId() +
            ", tId=" + gettId() +
            ", eId=" + geteId() +
            ", lstMtnUsr='" + getLstMtnUsr() + "'" +
            ", lstMtnDt='" + getLstMtnDt() + "'" +
            "}";
    }
}
