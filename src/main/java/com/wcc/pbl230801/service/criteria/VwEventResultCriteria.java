package com.wcc.pbl230801.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.wcc.pbl230801.domain.VwEventResult} entity. This class is used
 * in {@link com.wcc.pbl230801.web.rest.VwEventResultResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vw-event-results?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VwEventResultCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter eId;

    private LongFilter tId;

    private LongFilter pId;

    private LongFilter mId;

    private StringFilter winFg;

    private StringFilter plyrNm;

    private StringFilter plyrLvl;

    private ZonedDateTimeFilter mtchEndTime;

    private StringFilter totMatchs;

    private StringFilter totWins;

    private StringFilter acmlWins;

    private StringFilter chkFg;

    private Boolean distinct;

    public VwEventResultCriteria() {}

    public VwEventResultCriteria(VwEventResultCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.eId = other.eId == null ? null : other.eId.copy();
        this.tId = other.tId == null ? null : other.tId.copy();
        this.pId = other.pId == null ? null : other.pId.copy();
        this.mId = other.mId == null ? null : other.mId.copy();
        this.winFg = other.winFg == null ? null : other.winFg.copy();
        this.plyrNm = other.plyrNm == null ? null : other.plyrNm.copy();
        this.plyrLvl = other.plyrLvl == null ? null : other.plyrLvl.copy();
        this.mtchEndTime = other.mtchEndTime == null ? null : other.mtchEndTime.copy();
        this.totMatchs = other.totMatchs == null ? null : other.totMatchs.copy();
        this.totWins = other.totWins == null ? null : other.totWins.copy();
        this.acmlWins = other.acmlWins == null ? null : other.acmlWins.copy();
        this.chkFg = other.chkFg == null ? null : other.chkFg.copy();
        this.distinct = other.distinct;
    }

    @Override
    public VwEventResultCriteria copy() {
        return new VwEventResultCriteria(this);
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

    public LongFilter gettId() {
        return tId;
    }

    public LongFilter tId() {
        if (tId == null) {
            tId = new LongFilter();
        }
        return tId;
    }

    public void settId(LongFilter tId) {
        this.tId = tId;
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

    public StringFilter getPlyrNm() {
        return plyrNm;
    }

    public StringFilter plyrNm() {
        if (plyrNm == null) {
            plyrNm = new StringFilter();
        }
        return plyrNm;
    }

    public void setPlyrNm(StringFilter plyrNm) {
        this.plyrNm = plyrNm;
    }

    public StringFilter getPlyrLvl() {
        return plyrLvl;
    }

    public StringFilter plyrLvl() {
        if (plyrLvl == null) {
            plyrLvl = new StringFilter();
        }
        return plyrLvl;
    }

    public void setPlyrLvl(StringFilter plyrLvl) {
        this.plyrLvl = plyrLvl;
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

    public StringFilter getTotMatchs() {
        return totMatchs;
    }

    public StringFilter totMatchs() {
        if (totMatchs == null) {
            totMatchs = new StringFilter();
        }
        return totMatchs;
    }

    public void setTotMatchs(StringFilter totMatchs) {
        this.totMatchs = totMatchs;
    }

    public StringFilter getTotWins() {
        return totWins;
    }

    public StringFilter totWins() {
        if (totWins == null) {
            totWins = new StringFilter();
        }
        return totWins;
    }

    public void setTotWins(StringFilter totWins) {
        this.totWins = totWins;
    }

    public StringFilter getAcmlWins() {
        return acmlWins;
    }

    public StringFilter acmlWins() {
        if (acmlWins == null) {
            acmlWins = new StringFilter();
        }
        return acmlWins;
    }

    public void setAcmlWins(StringFilter acmlWins) {
        this.acmlWins = acmlWins;
    }

    public StringFilter getChkFg() {
        return chkFg;
    }

    public StringFilter chkFg() {
        if (chkFg == null) {
            chkFg = new StringFilter();
        }
        return chkFg;
    }

    public void setChkFg(StringFilter chkFg) {
        this.chkFg = chkFg;
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
        final VwEventResultCriteria that = (VwEventResultCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(eId, that.eId) &&
            Objects.equals(tId, that.tId) &&
            Objects.equals(pId, that.pId) &&
            Objects.equals(mId, that.mId) &&
            Objects.equals(winFg, that.winFg) &&
            Objects.equals(plyrNm, that.plyrNm) &&
            Objects.equals(plyrLvl, that.plyrLvl) &&
            Objects.equals(mtchEndTime, that.mtchEndTime) &&
            Objects.equals(totMatchs, that.totMatchs) &&
            Objects.equals(totWins, that.totWins) &&
            Objects.equals(acmlWins, that.acmlWins) &&
            Objects.equals(chkFg, that.chkFg) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eId, tId, pId, mId, winFg, plyrNm, plyrLvl, mtchEndTime, totMatchs, totWins, acmlWins, chkFg, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VwEventResultCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (eId != null ? "eId=" + eId + ", " : "") +
            (tId != null ? "tId=" + tId + ", " : "") +
            (pId != null ? "pId=" + pId + ", " : "") +
            (mId != null ? "mId=" + mId + ", " : "") +
            (winFg != null ? "winFg=" + winFg + ", " : "") +
            (plyrNm != null ? "plyrNm=" + plyrNm + ", " : "") +
            (plyrLvl != null ? "plyrLvl=" + plyrLvl + ", " : "") +
            (mtchEndTime != null ? "mtchEndTime=" + mtchEndTime + ", " : "") +
            (totMatchs != null ? "totMatchs=" + totMatchs + ", " : "") +
            (totWins != null ? "totWins=" + totWins + ", " : "") +
            (acmlWins != null ? "acmlWins=" + acmlWins + ", " : "") +
            (chkFg != null ? "chkFg=" + chkFg + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
