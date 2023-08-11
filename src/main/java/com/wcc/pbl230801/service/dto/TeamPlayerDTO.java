package com.wcc.pbl230801.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.wcc.pbl230801.domain.TeamPlayer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TeamPlayerDTO implements Serializable {

    private Long id;

    private Long tId;

    private Long pId;

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

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
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
        if (!(o instanceof TeamPlayerDTO)) {
            return false;
        }

        TeamPlayerDTO teamPlayerDTO = (TeamPlayerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, teamPlayerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamPlayerDTO{" +
            "id=" + getId() +
            ", tId=" + gettId() +
            ", pId=" + getpId() +
            ", lstMtnUsr='" + getLstMtnUsr() + "'" +
            ", lstMtnDt='" + getLstMtnDt() + "'" +
            "}";
    }
}
