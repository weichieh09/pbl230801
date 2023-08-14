package com.wcc.pbl230801.pblService.dto;

import java.io.Serializable;
import java.util.Objects;

public class PlayersReqDTOC implements Serializable {

    private String tId;

    private String pId;

    private String plyrLvl;

    private String plyrNm;

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
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
}
