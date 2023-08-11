package com.wcc.pbl230801.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.wcc.pbl230801.domain.MatchPlayer} entity. This class is used
 * in {@link com.wcc.pbl230801.web.rest.MatchPlayerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /match-players?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchPlayerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter mId;

    private LongFilter pId;

    private LongFilter eId;

    private ZonedDateTimeFilter mtchEndTime;

    private StringFilter score;

    private StringFilter winFg;

    private StringFilter lstMtnUsr;

    private ZonedDateTimeFilter lstMtnDt;

    private Boolean distinct;

    public MatchPlayerCriteria() {}

    public MatchPlayerCriteria(MatchPlayerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.mId = other.mId == null ? null : other.mId.copy();
        this.pId = other.pId == null ? null : other.pId.copy();
        this.eId = other.eId == null ? null : other.eId.copy();
        this.mtchEndTime = other.mtchEndTime == null ? null : other.mtchEndTime.copy();
        this.score = other.score == null ? null : other.score.copy();
        this.winFg = other.winFg == null ? null : other.winFg.copy();
        this.lstMtnUsr = other.lstMtnUsr == null ? null : other.lstMtnUsr.copy();
        this.lstMtnDt = other.lstMtnDt == null ? null : other.lstMtnDt.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MatchPlayerCriteria copy() {
        return new MatchPlayerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getmId() {
        return mId;
    }

    public LongFilter mId() {
        if (mId == null) {
            mId = new LongFilter();
        }
        return mId;
    }

    public void setmId(LongFilter mId) {
        this.mId = mId;
    }

    public LongFilter getpId() {
        return pId;
    }

    public LongFilter pId() {
        if (pId == null) {
            pId = new LongFilter();
        }
        return pId;
    }

    public void setpId(LongFilter pId) {
        this.pId = pId;
    }

    public LongFilter geteId() {
        return eId;
    }

    public LongFilter eId() {
        if (eId == null) {
            eId = new LongFilter();
        }
        return eId;
    }

    public void seteId(LongFilter eId) {
        this.eId = eId;
    }

    public ZonedDateTimeFilter getMtchEndTime() {
        return mtchEndTime;
    }

    public ZonedDateTimeFilter mtchEndTime() {
        if (mtchEndTime == null) {
            mtchEndTime = new ZonedDateTimeFilter();
        }
        return mtchEndTime;
    }

    public void setMtchEndTime(ZonedDateTimeFilter mtchEndTime) {
        this.mtchEndTime = mtchEndTime;
    }

    public StringFilter getScore() {
        return score;
    }

    public StringFilter score() {
        if (score == null) {
            score = new StringFilter();
        }
        return score;
    }

    public void setScore(StringFilter score) {
        this.score = score;
    }

    public StringFilter getWinFg() {
        return winFg;
    }

    public StringFilter winFg() {
        if (winFg == null) {
            winFg = new StringFilter();
        }
        return winFg;
    }

    public void setWinFg(StringFilter winFg) {
        this.winFg = winFg;
    }

    public StringFilter getLstMtnUsr() {
        return lstMtnUsr;
    }

    public StringFilter lstMtnUsr() {
        if (lstMtnUsr == null) {
            lstMtnUsr = new StringFilter();
        }
        return lstMtnUsr;
    }

    public void setLstMtnUsr(StringFilter lstMtnUsr) {
        this.lstMtnUsr = lstMtnUsr;
    }

    public ZonedDateTimeFilter getLstMtnDt() {
        return lstMtnDt;
    }

    public ZonedDateTimeFilter lstMtnDt() {
        if (lstMtnDt == null) {
            lstMtnDt = new ZonedDateTimeFilter();
        }
        return lstMtnDt;
    }

    public void setLstMtnDt(ZonedDateTimeFilter lstMtnDt) {
        this.lstMtnDt = lstMtnDt;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MatchPlayerCriteria that = (MatchPlayerCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(mId, that.mId) &&
            Objects.equals(pId, that.pId) &&
            Objects.equals(eId, that.eId) &&
            Objects.equals(mtchEndTime, that.mtchEndTime) &&
            Objects.equals(score, that.score) &&
            Objects.equals(winFg, that.winFg) &&
            Objects.equals(lstMtnUsr, that.lstMtnUsr) &&
            Objects.equals(lstMtnDt, that.lstMtnDt) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mId, pId, eId, mtchEndTime, score, winFg, lstMtnUsr, lstMtnDt, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchPlayerCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (mId != null ? "mId=" + mId + ", " : "") +
            (pId != null ? "pId=" + pId + ", " : "") +
            (eId != null ? "eId=" + eId + ", " : "") +
            (mtchEndTime != null ? "mtchEndTime=" + mtchEndTime + ", " : "") +
            (score != null ? "score=" + score + ", " : "") +
            (winFg != null ? "winFg=" + winFg + ", " : "") +
            (lstMtnUsr != null ? "lstMtnUsr=" + lstMtnUsr + ", " : "") +
            (lstMtnDt != null ? "lstMtnDt=" + lstMtnDt + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
