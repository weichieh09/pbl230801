package com.wcc.pbl230801.pblService.dto;

import java.io.Serializable;
import java.util.Objects;

public class RespDTOC implements Serializable {

    private String status;

    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
