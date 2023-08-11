package com.wcc.pbl230801.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A MatchPlayer.
 */
@Entity
@Table(name = "match_player")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchPlayer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "m_id")
    private Long mId;

    @Column(name = "p_id")
    private Long pId;

    @Column(name = "e_id")
    private Long eId;

    @Column(name = "mtch_end_time")
    private ZonedDateTime mtchEndTime;

    @Column(name = "score")
    private String score;

    @Column(name = "win_fg")
    private String winFg;

    @Column(name = "lst_mtn_usr")
    private String lstMtnUsr;

    @Column(name = "lst_mtn_dt")
    private ZonedDateTime lstMtnDt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MatchPlayer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getmId() {
        return this.mId;
    }

    public MatchPlayer mId(Long mId) {
        this.setmId(mId);
        return this;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public Long getpId() {
        return this.pId;
    }

    public MatchPlayer pId(Long pId) {
        this.setpId(pId);
        return this;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Long geteId() {
        return this.eId;
    }

    public MatchPlayer eId(Long eId) {
        this.seteId(eId);
        return this;
    }

    public void seteId(Long eId) {
        this.eId = eId;
    }

    public ZonedDateTime getMtchEndTime() {
        return this.mtchEndTime;
    }

    public MatchPlayer mtchEndTime(ZonedDateTime mtchEndTime) {
        this.setMtchEndTime(mtchEndTime);
        return this;
    }

    public void setMtchEndTime(ZonedDateTime mtchEndTime) {
        this.mtchEndTime = mtchEndTime;
    }

    public String getScore() {
        return this.score;
    }

    public MatchPlayer score(String score) {
        this.setScore(score);
        return this;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getWinFg() {
        return this.winFg;
    }

    public MatchPlayer winFg(String winFg) {
        this.setWinFg(winFg);
        return this;
    }

    public void setWinFg(String winFg) {
        this.winFg = winFg;
    }

    public String getLstMtnUsr() {
        return this.lstMtnUsr;
    }

    public MatchPlayer lstMtnUsr(String lstMtnUsr) {
        this.setLstMtnUsr(lstMtnUsr);
        return this;
    }

    public void setLstMtnUsr(String lstMtnUsr) {
        this.lstMtnUsr = lstMtnUsr;
    }

    public ZonedDateTime getLstMtnDt() {
        return this.lstMtnDt;
    }

    public MatchPlayer lstMtnDt(ZonedDateTime lstMtnDt) {
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
        if (!(o instanceof MatchPlayer)) {
            return false;
        }
        return id != null && id.equals(((MatchPlayer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchPlayer{" +
            "id=" + getId() +
            ", mId=" + getmId() +
            ", pId=" + getpId() +
            ", eId=" + geteId() +
            ", mtchEndTime='" + getMtchEndTime() + "'" +
            ", score='" + getScore() + "'" +
            ", winFg='" + getWinFg() + "'" +
            ", lstMtnUsr='" + getLstMtnUsr() + "'" +
            ", lstMtnDt='" + getLstMtnDt() + "'" +
            "}";
    }
}
