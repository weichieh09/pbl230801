package com.wcc.pbl230801.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A VwEventResult.
 */
@Entity
@Table(name = "vw_event_result")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VwEventResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "e_id")
    private Long eId;

    @Column(name = "p_id")
    private Long pId;

    @Column(name = "m_id")
    private Long mId;

    @Column(name = "win_fg")
    private String winFg;

    @Column(name = "plyr_nm")
    private String plyrNm;

    @Column(name = "plyr_lvl")
    private String plyrLvl;

    @Column(name = "mtch_end_time")
    private ZonedDateTime mtchEndTime;

    @Column(name = "tot_matchs")
    private String totMatchs;

    @Column(name = "tot_wins")
    private String totWins;

    @Column(name = "acml_wins")
    private String acmlWins;

    @Column(name = "chk_fg")
    private String chkFg;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VwEventResult id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long geteId() {
        return this.eId;
    }

    public VwEventResult eId(Long eId) {
        this.seteId(eId);
        return this;
    }

    public void seteId(Long eId) {
        this.eId = eId;
    }

    public Long getpId() {
        return this.pId;
    }

    public VwEventResult pId(Long pId) {
        this.setpId(pId);
        return this;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Long getmId() {
        return this.mId;
    }

    public VwEventResult mId(Long mId) {
        this.setmId(mId);
        return this;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public String getWinFg() {
        return this.winFg;
    }

    public VwEventResult winFg(String winFg) {
        this.setWinFg(winFg);
        return this;
    }

    public void setWinFg(String winFg) {
        this.winFg = winFg;
    }

    public String getPlyrNm() {
        return this.plyrNm;
    }

    public VwEventResult plyrNm(String plyrNm) {
        this.setPlyrNm(plyrNm);
        return this;
    }

    public void setPlyrNm(String plyrNm) {
        this.plyrNm = plyrNm;
    }

    public String getPlyrLvl() {
        return this.plyrLvl;
    }

    public VwEventResult plyrLvl(String plyrLvl) {
        this.setPlyrLvl(plyrLvl);
        return this;
    }

    public void setPlyrLvl(String plyrLvl) {
        this.plyrLvl = plyrLvl;
    }

    public ZonedDateTime getMtchEndTime() {
        return this.mtchEndTime;
    }

    public VwEventResult mtchEndTime(ZonedDateTime mtchEndTime) {
        this.setMtchEndTime(mtchEndTime);
        return this;
    }

    public void setMtchEndTime(ZonedDateTime mtchEndTime) {
        this.mtchEndTime = mtchEndTime;
    }

    public String getTotMatchs() {
        return this.totMatchs;
    }

    public VwEventResult totMatchs(String totMatchs) {
        this.setTotMatchs(totMatchs);
        return this;
    }

    public void setTotMatchs(String totMatchs) {
        this.totMatchs = totMatchs;
    }

    public String getTotWins() {
        return this.totWins;
    }

    public VwEventResult totWins(String totWins) {
        this.setTotWins(totWins);
        return this;
    }

    public void setTotWins(String totWins) {
        this.totWins = totWins;
    }

    public String getAcmlWins() {
        return this.acmlWins;
    }

    public VwEventResult acmlWins(String acmlWins) {
        this.setAcmlWins(acmlWins);
        return this;
    }

    public void setAcmlWins(String acmlWins) {
        this.acmlWins = acmlWins;
    }

    public String getChkFg() {
        return this.chkFg;
    }

    public VwEventResult chkFg(String chkFg) {
        this.setChkFg(chkFg);
        return this;
    }

    public void setChkFg(String chkFg) {
        this.chkFg = chkFg;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VwEventResult)) {
            return false;
        }
        return id != null && id.equals(((VwEventResult) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VwEventResult{" +
            "id=" + getId() +
            ", eId=" + geteId() +
            ", pId=" + getpId() +
            ", mId=" + getmId() +
            ", winFg='" + getWinFg() + "'" +
            ", plyrNm='" + getPlyrNm() + "'" +
            ", plyrLvl='" + getPlyrLvl() + "'" +
            ", mtchEndTime='" + getMtchEndTime() + "'" +
            ", totMatchs='" + getTotMatchs() + "'" +
            ", totWins='" + getTotWins() + "'" +
            ", acmlWins='" + getAcmlWins() + "'" +
            ", chkFg='" + getChkFg() + "'" +
            "}";
    }
}
