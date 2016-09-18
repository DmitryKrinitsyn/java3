package com.home.beans;

import com.home.model.StatusType;

/**
 * Created by dmitry on 9/17/16.
 */
public class Status {
    private StatusType status;

    public Status() {
        status = StatusType.failed;
    }

    public Status(StatusType status) {
        this.status = status;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }
}
