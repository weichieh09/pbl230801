package com.wcc.pbl230801.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.wcc.pbl230801.domain.Prod} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProdDTO implements Serializable {

    private Long id;

    @Size(max = 30)
    private String prodNo;

    @Size(max = 100)
    private String chName;

    private LocalDate issuDt;

    private LocalDate expDt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public LocalDate getIssuDt() {
        return issuDt;
    }

    public void setIssuDt(LocalDate issuDt) {
        this.issuDt = issuDt;
    }

    public LocalDate getExpDt() {
        return expDt;
    }

    public void setExpDt(LocalDate expDt) {
        this.expDt = expDt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProdDTO)) {
            return false;
        }

        ProdDTO prodDTO = (ProdDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, prodDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProdDTO{" +
            "id=" + getId() +
            ", prodNo='" + getProdNo() + "'" +
            ", chName='" + getChName() + "'" +
            ", issuDt='" + getIssuDt() + "'" +
            ", expDt='" + getExpDt() + "'" +
            "}";
    }
}
