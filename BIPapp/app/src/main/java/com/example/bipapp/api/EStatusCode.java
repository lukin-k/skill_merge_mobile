package com.example.bipapp.api;

public enum EStatusCode {
    OK("OK"),
    NOT_OK("NOT_OK"),

    BAD("");

    private final String strStatusCode;

    EStatusCode(String strStatus) {
        this.strStatusCode = strStatus;
    }

    public String getStrStatusCode() {
        return strStatusCode;
    }

    public static EStatusCode getStatusCode(String strStatus) {
        String UpCaseStrStatus = strStatus.toUpperCase();
        EStatusCode[] statusCodes = EStatusCode.values();
        for (EStatusCode statusCode : statusCodes) {
            if (statusCode.getStrStatusCode().equals(UpCaseStrStatus)) {
                return statusCode;
            }
        }

        return BAD;
    }
}
