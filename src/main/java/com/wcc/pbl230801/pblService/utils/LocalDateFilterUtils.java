package com.wcc.pbl230801.pblService.utils;

import java.time.LocalDate;
import java.util.List;
import tech.jhipster.service.filter.LocalDateFilter;

public class LocalDateFilterUtils {

    public static LocalDateFilter toEqualLongFilter(LocalDate ln) {
        LocalDateFilter ls = new LocalDateFilter();
        ls.setEquals(ln);
        return ls;
    }

    public static LocalDateFilter toBiggerLongFilter(LocalDate ln) {
        LocalDateFilter ls = new LocalDateFilter();
        ls.setGreaterThan(ln);
        return ls;
    }

    public static LocalDateFilter toSmallerLongFilter(LocalDate ln) {
        LocalDateFilter ls = new LocalDateFilter();
        ls.setLessThan(ln);
        return ls;
    }

    public static LocalDateFilter toInLongFilter(List<LocalDate> lnList) {
        LocalDateFilter ls = new LocalDateFilter();
        ls.setIn(lnList);
        return ls;
    }
}
