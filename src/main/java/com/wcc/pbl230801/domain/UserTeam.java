package com.wcc.pbl230801.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A UserTeam.
 */
@Entity
@Table(name = "user_team")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserTeam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "u_id")
    private Long uId;

    @Column(name = "t_id")
    private Long tId;

    @Column(name = "lst_mtn_usr")
    private String lstMtnUsr;

    @Column(name = "lst_mtn_dt")
    private ZonedDateTime lstMtnDt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserTeam id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getuId() {
        return this.uId;
    }

    public UserTeam uId(Long uId) {
        this.setuId(uId);
        return this;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public Long gettId() {
        return this.tId;
    }

    public UserTeam tId(Long tId) {
        this.settId(tId);
        return this;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public String getLstMtnUsr() {
        return this.lstMtnUsr;
    }

    public UserTeam lstMtnUsr(String lstMtnUsr) {
        this.setLstMtnUsr(lstMtnUsr);
        return this;
    }

    public void setLstMtnUsr(String lstMtnUsr) {
        this.lstMtnUsr = lstMtnUsr;
    }

    public ZonedDateTime getLstMtnDt() {
        return this.lstMtnDt;
    }

    public UserTeam lstMtnDt(ZonedDateTime lstMtnDt) {
        this.setLstMtnDt(lstMtnDt);
        return this;
    }

    public void setLstMtnDt(ZonedDateTime lstMtnDt) {
        this.lstMtnDt = lstMtnDt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserTeam)) {
            return false;
        }
        return id != null && id.equals(((UserTeam) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserTeam{" +
            "id=" + getId() +
            ", uId=" + getuId() +
            ", tId=" + gettId() +
            ", lstMtnUsr='" + getLstMtnUsr() + "'" +
            ", lstMtnDt='" + getLstMtnDt() + "'" +
            "}";
    }
}
