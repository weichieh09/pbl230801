package com.wcc.pbl230801.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.wcc.pbl230801.domain.MatchZ} entity. This class is used
 * in {@link com.wcc.pbl230801.web.rest.MatchZResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /match-zs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchZCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter eId;

    private ZonedDateTimeFilter mtchEndTime;

    private StringFilter wPlyr1;

    private StringFilter wPlyr2;

    private StringFilter wScr;

    private StringFilter lPlyr1;

    private StringFilter lPlyr2;

    private StringFilter lScr;

    private StringFilter lstMtnUsr;

    private ZonedDateTimeFilter lstMtnDt;

    private Boolean distinct;

    public MatchZCriteria() {}

    public MatchZCriteria(MatchZCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.eId = other.eId == null ? null : other.eId.copy();
        this.mtchEndTime = other.mtchEndTime == null ? null : other.mtchEndTime.copy();
        this.wPlyr1 = other.wPlyr1 == null ? null : other.wPlyr1.copy();
        this.wPlyr2 = other.wPlyr2 == null ? null : other.wPlyr2.copy();
        this.wScr = other.wScr == null ? null : other.wScr.copy();
        this.lPlyr1 = other.lPlyr1 == null ? null : other.lPlyr1.copy();
        this.lPlyr2 = other.lPlyr2 == null ? null : other.lPlyr2.copy();
        this.lScr = other.lScr == null ? null : other.lScr.copy();
        this.lstMtnUsr = other.lstMtnUsr == null ? null : other.lstMtnUsr.copy();
        this.lstMtnDt = other.lstMtnDt == null ? null : other.lstMtnDt.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MatchZCriteria copy() {
        return new MatchZCriteria(this);
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

    public StringFilter getwPlyr1() {
        return wPlyr1;
    }

    public StringFilter wPlyr1() {
        if (wPlyr1 == null) {
            wPlyr1 = new StringFilter();
        }
        return wPlyr1;
    }

    public void setwPlyr1(StringFilter wPlyr1) {
        this.wPlyr1 = wPlyr1;
    }

    public StringFilter getwPlyr2() {
        return wPlyr2;
    }

    public StringFilter wPlyr2() {
        if (wPlyr2 == null) {
            wPlyr2 = new StringFilter();
        }
        return wPlyr2;
    }

    public void setwPlyr2(StringFilter wPlyr2) {
        this.wPlyr2 = wPlyr2;
    }

    public StringFilter getwScr() {
        return wScr;
    }

    public StringFilter wScr() {
        if (wScr == null) {
            wScr = new StringFilter();
        }
        return wScr;
    }

    public void setwScr(StringFilter wScr) {
        this.wScr = wScr;
    }

    public StringFilter getlPlyr1() {
        return lPlyr1;
    }

    public StringFilter lPlyr1() {
        if (lPlyr1 == null) {
            lPlyr1 = new StringFilter();
        }
        return lPlyr1;
    }

    public void setlPlyr1(StringFilter lPlyr1) {
        this.lPlyr1 = lPlyr1;
    }

    public StringFilter getlPlyr2() {
        return lPlyr2;
    }

    public StringFilter lPlyr2() {
        if (lPlyr2 == null) {
            lPlyr2 = new StringFilter();
        }
        return lPlyr2;
    }

    public void setlPlyr2(StringFilter lPlyr2) {
        this.lPlyr2 = lPlyr2;
    }

    public StringFilter getlScr() {
        return lScr;
    }

    public StringFilter lScr() {
        if (lScr == null) {
            lScr = new StringFilter();
        }
        return lScr;
    }

    public void setlScr(StringFilter lScr) {
        this.lScr = lScr;
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
        final MatchZCriteria that = (MatchZCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(eId, that.eId) &&
            Objects.equals(mtchEndTime, that.mtchEndTime) &&
            Objects.equals(wPlyr1, that.wPlyr1) &&
            Objects.equals(wPlyr2, that.wPlyr2) &&
            Objects.equals(wScr, that.wScr) &&
            Objects.equals(lPlyr1, that.lPlyr1) &&
            Objects.equals(lPlyr2, that.lPlyr2) &&
            Objects.equals(lScr, that.lScr) &&
            Objects.equals(lstMtnUsr, that.lstMtnUsr) &&
            Objects.equals(lstMtnDt, that.lstMtnDt) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eId, mtchEndTime, wPlyr1, wPlyr2, wScr, lPlyr1, lPlyr2, lScr, lstMtnUsr, lstMtnDt, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchZCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (eId != null ? "eId=" + eId + ", " : "") +
            (mtchEndTime != null ? "mtchEndTime=" + mtchEndTime + ", " : "") +
            (wPlyr1 != null ? "wPlyr1=" + wPlyr1 + ", " : "") +
            (wPlyr2 != null ? "wPlyr2=" + wPlyr2 + ", " : "") +
            (wScr != null ? "wScr=" + wScr + ", " : "") +
            (lPlyr1 != null ? "lPlyr1=" + lPlyr1 + ", " : "") +
            (lPlyr2 != null ? "lPlyr2=" + lPlyr2 + ", " : "") +
            (lScr != null ? "lScr=" + lScr + ", " : "") +
            (lstMtnUsr != null ? "lstMtnUsr=" + lstMtnUsr + ", " : "") +
            (lstMtnDt != null ? "lstMtnDt=" + lstMtnDt + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
