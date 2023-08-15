package com.wcc.pbl230801.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.wcc.pbl230801.domain.VwWcc701Result} entity. This class is used
 * in {@link com.wcc.pbl230801.web.rest.VwWcc701ResultResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vw-wcc-701-results?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VwWcc701ResultCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter eId;

    private StringFilter evntNm;

    private ZonedDateTimeFilter evntDt;

    private StringFilter venue;

    private LongFilter mId;

    private ZonedDateTimeFilter mtchEndTime;

    private LongFilter wPlyr1Id;

    private StringFilter wPlyr1Lvl;

    private StringFilter wPlyr1Nm;

    private LongFilter wPlyr2Id;

    private StringFilter wPlyr2Lvl;

    private StringFilter wPlyr2Nm;

    private StringFilter vs;

    private LongFilter lPlyr1Id;

    private StringFilter lPlyr1Lvl;

    private StringFilter lPlyr1Nm;

    private LongFilter lPlyr2Id;

    private StringFilter lPlyr2Lvl;

    private StringFilter lPlyr2Nm;

    private StringFilter wScr;

    private StringFilter lScr;

    private Boolean distinct;

    public VwWcc701ResultCriteria() {}

    public VwWcc701ResultCriteria(VwWcc701ResultCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.eId = other.eId == null ? null : other.eId.copy();
        this.evntNm = other.evntNm == null ? null : other.evntNm.copy();
        this.evntDt = other.evntDt == null ? null : other.evntDt.copy();
        this.venue = other.venue == null ? null : other.venue.copy();
        this.mId = other.mId == null ? null : other.mId.copy();
        this.mtchEndTime = other.mtchEndTime == null ? null : other.mtchEndTime.copy();
        this.wPlyr1Id = other.wPlyr1Id == null ? null : other.wPlyr1Id.copy();
        this.wPlyr1Lvl = other.wPlyr1Lvl == null ? null : other.wPlyr1Lvl.copy();
        this.wPlyr1Nm = other.wPlyr1Nm == null ? null : other.wPlyr1Nm.copy();
        this.wPlyr2Id = other.wPlyr2Id == null ? null : other.wPlyr2Id.copy();
        this.wPlyr2Lvl = other.wPlyr2Lvl == null ? null : other.wPlyr2Lvl.copy();
        this.wPlyr2Nm = other.wPlyr2Nm == null ? null : other.wPlyr2Nm.copy();
        this.vs = other.vs == null ? null : other.vs.copy();
        this.lPlyr1Id = other.lPlyr1Id == null ? null : other.lPlyr1Id.copy();
        this.lPlyr1Lvl = other.lPlyr1Lvl == null ? null : other.lPlyr1Lvl.copy();
        this.lPlyr1Nm = other.lPlyr1Nm == null ? null : other.lPlyr1Nm.copy();
        this.lPlyr2Id = other.lPlyr2Id == null ? null : other.lPlyr2Id.copy();
        this.lPlyr2Lvl = other.lPlyr2Lvl == null ? null : other.lPlyr2Lvl.copy();
        this.lPlyr2Nm = other.lPlyr2Nm == null ? null : other.lPlyr2Nm.copy();
        this.wScr = other.wScr == null ? null : other.wScr.copy();
        this.lScr = other.lScr == null ? null : other.lScr.copy();
        this.distinct = other.distinct;
    }

    @Override
    public VwWcc701ResultCriteria copy() {
        return new VwWcc701ResultCriteria(this);
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

    public StringFilter getEvntNm() {
        return evntNm;
    }

    public StringFilter evntNm() {
        if (evntNm == null) {
            evntNm = new StringFilter();
        }
        return evntNm;
    }

    public void setEvntNm(StringFilter evntNm) {
        this.evntNm = evntNm;
    }

    public ZonedDateTimeFilter getEvntDt() {
        return evntDt;
    }

    public ZonedDateTimeFilter evntDt() {
        if (evntDt == null) {
            evntDt = new ZonedDateTimeFilter();
        }
        return evntDt;
    }

    public void setEvntDt(ZonedDateTimeFilter evntDt) {
        this.evntDt = evntDt;
    }

    public StringFilter getVenue() {
        return venue;
    }

    public StringFilter venue() {
        if (venue == null) {
            venue = new StringFilter();
        }
        return venue;
    }

    public void setVenue(StringFilter venue) {
        this.venue = venue;
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

    public LongFilter getwPlyr1Id() {
        return wPlyr1Id;
    }

    public LongFilter wPlyr1Id() {
        if (wPlyr1Id == null) {
            wPlyr1Id = new LongFilter();
        }
        return wPlyr1Id;
    }

    public void setwPlyr1Id(LongFilter wPlyr1Id) {
        this.wPlyr1Id = wPlyr1Id;
    }

    public StringFilter getwPlyr1Lvl() {
        return wPlyr1Lvl;
    }

    public StringFilter wPlyr1Lvl() {
        if (wPlyr1Lvl == null) {
            wPlyr1Lvl = new StringFilter();
        }
        return wPlyr1Lvl;
    }

    public void setwPlyr1Lvl(StringFilter wPlyr1Lvl) {
        this.wPlyr1Lvl = wPlyr1Lvl;
    }

    public StringFilter getwPlyr1Nm() {
        return wPlyr1Nm;
    }

    public StringFilter wPlyr1Nm() {
        if (wPlyr1Nm == null) {
            wPlyr1Nm = new StringFilter();
        }
        return wPlyr1Nm;
    }

    public void setwPlyr1Nm(StringFilter wPlyr1Nm) {
        this.wPlyr1Nm = wPlyr1Nm;
    }

    public LongFilter getwPlyr2Id() {
        return wPlyr2Id;
    }

    public LongFilter wPlyr2Id() {
        if (wPlyr2Id == null) {
            wPlyr2Id = new LongFilter();
        }
        return wPlyr2Id;
    }

    public void setwPlyr2Id(LongFilter wPlyr2Id) {
        this.wPlyr2Id = wPlyr2Id;
    }

    public StringFilter getwPlyr2Lvl() {
        return wPlyr2Lvl;
    }

    public StringFilter wPlyr2Lvl() {
        if (wPlyr2Lvl == null) {
            wPlyr2Lvl = new StringFilter();
        }
        return wPlyr2Lvl;
    }

    public void setwPlyr2Lvl(StringFilter wPlyr2Lvl) {
        this.wPlyr2Lvl = wPlyr2Lvl;
    }

    public StringFilter getwPlyr2Nm() {
        return wPlyr2Nm;
    }

    public StringFilter wPlyr2Nm() {
        if (wPlyr2Nm == null) {
            wPlyr2Nm = new StringFilter();
        }
        return wPlyr2Nm;
    }

    public void setwPlyr2Nm(StringFilter wPlyr2Nm) {
        this.wPlyr2Nm = wPlyr2Nm;
    }

    public StringFilter getVs() {
        return vs;
    }

    public StringFilter vs() {
        if (vs == null) {
            vs = new StringFilter();
        }
        return vs;
    }

    public void setVs(StringFilter vs) {
        this.vs = vs;
    }

    public LongFilter getlPlyr1Id() {
        return lPlyr1Id;
    }

    public LongFilter lPlyr1Id() {
        if (lPlyr1Id == null) {
            lPlyr1Id = new LongFilter();
        }
        return lPlyr1Id;
    }

    public void setlPlyr1Id(LongFilter lPlyr1Id) {
        this.lPlyr1Id = lPlyr1Id;
    }

    public StringFilter getlPlyr1Lvl() {
        return lPlyr1Lvl;
    }

    public StringFilter lPlyr1Lvl() {
        if (lPlyr1Lvl == null) {
            lPlyr1Lvl = new StringFilter();
        }
        return lPlyr1Lvl;
    }

    public void setlPlyr1Lvl(StringFilter lPlyr1Lvl) {
        this.lPlyr1Lvl = lPlyr1Lvl;
    }

    public StringFilter getlPlyr1Nm() {
        return lPlyr1Nm;
    }

    public StringFilter lPlyr1Nm() {
        if (lPlyr1Nm == null) {
            lPlyr1Nm = new StringFilter();
        }
        return lPlyr1Nm;
    }

    public void setlPlyr1Nm(StringFilter lPlyr1Nm) {
        this.lPlyr1Nm = lPlyr1Nm;
    }

    public LongFilter getlPlyr2Id() {
        return lPlyr2Id;
    }

    public LongFilter lPlyr2Id() {
        if (lPlyr2Id == null) {
            lPlyr2Id = new LongFilter();
        }
        return lPlyr2Id;
    }

    public void setlPlyr2Id(LongFilter lPlyr2Id) {
        this.lPlyr2Id = lPlyr2Id;
    }

    public StringFilter getlPlyr2Lvl() {
        return lPlyr2Lvl;
    }

    public StringFilter lPlyr2Lvl() {
        if (lPlyr2Lvl == null) {
            lPlyr2Lvl = new StringFilter();
        }
        return lPlyr2Lvl;
    }

    public void setlPlyr2Lvl(StringFilter lPlyr2Lvl) {
        this.lPlyr2Lvl = lPlyr2Lvl;
    }

    public StringFilter getlPlyr2Nm() {
        return lPlyr2Nm;
    }

    public StringFilter lPlyr2Nm() {
        if (lPlyr2Nm == null) {
            lPlyr2Nm = new StringFilter();
        }
        return lPlyr2Nm;
    }

    public void setlPlyr2Nm(StringFilter lPlyr2Nm) {
        this.lPlyr2Nm = lPlyr2Nm;
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
        final VwWcc701ResultCriteria that = (VwWcc701ResultCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(eId, that.eId) &&
            Objects.equals(evntNm, that.evntNm) &&
            Objects.equals(evntDt, that.evntDt) &&
            Objects.equals(venue, that.venue) &&
            Objects.equals(mId, that.mId) &&
            Objects.equals(mtchEndTime, that.mtchEndTime) &&
            Objects.equals(wPlyr1Id, that.wPlyr1Id) &&
            Objects.equals(wPlyr1Lvl, that.wPlyr1Lvl) &&
            Objects.equals(wPlyr1Nm, that.wPlyr1Nm) &&
            Objects.equals(wPlyr2Id, that.wPlyr2Id) &&
            Objects.equals(wPlyr2Lvl, that.wPlyr2Lvl) &&
            Objects.equals(wPlyr2Nm, that.wPlyr2Nm) &&
            Objects.equals(vs, that.vs) &&
            Objects.equals(lPlyr1Id, that.lPlyr1Id) &&
            Objects.equals(lPlyr1Lvl, that.lPlyr1Lvl) &&
            Objects.equals(lPlyr1Nm, that.lPlyr1Nm) &&
            Objects.equals(lPlyr2Id, that.lPlyr2Id) &&
            Objects.equals(lPlyr2Lvl, that.lPlyr2Lvl) &&
            Objects.equals(lPlyr2Nm, that.lPlyr2Nm) &&
            Objects.equals(wScr, that.wScr) &&
            Objects.equals(lScr, that.lScr) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            eId,
            evntNm,
            evntDt,
            venue,
            mId,
            mtchEndTime,
            wPlyr1Id,
            wPlyr1Lvl,
            wPlyr1Nm,
            wPlyr2Id,
            wPlyr2Lvl,
            wPlyr2Nm,
            vs,
            lPlyr1Id,
            lPlyr1Lvl,
            lPlyr1Nm,
            lPlyr2Id,
            lPlyr2Lvl,
            lPlyr2Nm,
            wScr,
            lScr,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VwWcc701ResultCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (eId != null ? "eId=" + eId + ", " : "") +
            (evntNm != null ? "evntNm=" + evntNm + ", " : "") +
            (evntDt != null ? "evntDt=" + evntDt + ", " : "") +
            (venue != null ? "venue=" + venue + ", " : "") +
            (mId != null ? "mId=" + mId + ", " : "") +
            (mtchEndTime != null ? "mtchEndTime=" + mtchEndTime + ", " : "") +
            (wPlyr1Id != null ? "wPlyr1Id=" + wPlyr1Id + ", " : "") +
            (wPlyr1Lvl != null ? "wPlyr1Lvl=" + wPlyr1Lvl + ", " : "") +
            (wPlyr1Nm != null ? "wPlyr1Nm=" + wPlyr1Nm + ", " : "") +
            (wPlyr2Id != null ? "wPlyr2Id=" + wPlyr2Id + ", " : "") +
            (wPlyr2Lvl != null ? "wPlyr2Lvl=" + wPlyr2Lvl + ", " : "") +
            (wPlyr2Nm != null ? "wPlyr2Nm=" + wPlyr2Nm + ", " : "") +
            (vs != null ? "vs=" + vs + ", " : "") +
            (lPlyr1Id != null ? "lPlyr1Id=" + lPlyr1Id + ", " : "") +
            (lPlyr1Lvl != null ? "lPlyr1Lvl=" + lPlyr1Lvl + ", " : "") +
            (lPlyr1Nm != null ? "lPlyr1Nm=" + lPlyr1Nm + ", " : "") +
            (lPlyr2Id != null ? "lPlyr2Id=" + lPlyr2Id + ", " : "") +
            (lPlyr2Lvl != null ? "lPlyr2Lvl=" + lPlyr2Lvl + ", " : "") +
            (lPlyr2Nm != null ? "lPlyr2Nm=" + lPlyr2Nm + ", " : "") +
            (wScr != null ? "wScr=" + wScr + ", " : "") +
            (lScr != null ? "lScr=" + lScr + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
