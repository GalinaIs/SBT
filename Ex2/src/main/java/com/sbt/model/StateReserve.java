package com.sbt.model;

public class StateReserve {
    private boolean allowedReserve;
    private boolean illegalReserve;
    private boolean cancelReserve;

    public boolean isAllowedReserve() {
        return allowedReserve;
    }

    public void setAllowedReserve(boolean allowedReserve) {
        this.allowedReserve = allowedReserve;
    }

    public boolean isIllegalReserve() {
        return illegalReserve;
    }

    public void setIllegalReserve(boolean illegalReserve) {
        this.illegalReserve = illegalReserve;
    }

    public boolean isCancelReserve() {
        return cancelReserve;
    }

    public void setCancelReserve(boolean cancelReserve) {
        this.cancelReserve = cancelReserve;
    }
}
