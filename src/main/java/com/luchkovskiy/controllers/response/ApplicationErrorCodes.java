package com.luchkovskiy.controllers.response;


public enum ApplicationErrorCodes {

    SQL_ERROR(10),
    NOT_FOUND_ERROR(40),
    FATAL_ERROR(1);

    public int getCodeId() {
        return codeId;
    }

    private final int codeId;

    ApplicationErrorCodes(int codeId) {
        this.codeId = codeId;
    }

}
