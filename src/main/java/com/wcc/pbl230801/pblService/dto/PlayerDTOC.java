package com.wcc.pbl230801.pblService.dto;

import java.io.Serializable;

public class PlayerDTOC implements Serializable {

    private String id;
    private String plyrLvl;
    private String plyrNm;
    private String joinEv;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlyrLvl() {
        return plyrLvl;
    }

    public void setPlyrLvl(String plyrLvl) {
        this.plyrLvl = plyrLvl;
    }

    public String getPlyrNm() {
        return plyrNm;
    }

    public void setPlyrNm(String plyrNm) {
        this.plyrNm = plyrNm;
    }

    public String getJoinEv() {
        return joinEv;
    }

    public void setJoinEv(String joinEv) {
        this.joinEv = joinEv;
    }
}
