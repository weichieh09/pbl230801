package com.wcc.pbl230801.pblService.utils;

import java.util.List;
import tech.jhipster.service.filter.LongFilter;

public class LongFilterUtils {

    public static LongFilter toEqualLongFilter(Long ln) {
        LongFilter ls = new LongFilter();
        ls.setEquals(ln);
        return ls;
    }

    public static LongFilter toBiggerLongFilter(Long ln) {
        LongFilter ls = new LongFilter();
        ls.setGreaterThan(ln);
        return ls;
    }

    public static LongFilter toSmallerLongFilter(Long ln) {
        LongFilter ls = new LongFilter();
        ls.setLessThan(ln);
        return ls;
    }

    public static LongFilter toInLongFilter(List<Long> lnList) {
        LongFilter ls = new LongFilter();
        ls.setIn(lnList);
        return ls;
    }
}
