package com.demo.dto;

public class BaseOutput {
    private boolean isSuccess;
    private String rtnMsg;

    public BaseOutput(boolean isSuccess, String rtnMsg) {
        this.isSuccess = isSuccess;
        this.rtnMsg = rtnMsg;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }
}
