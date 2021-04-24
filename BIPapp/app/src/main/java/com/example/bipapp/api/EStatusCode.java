package com.example.bipapp.api;

public enum EStatusCode {
    OK      ("OK"),
    NOT_OK  ("NOT_OK"),

    BAD     ("");

    private String strStatusCode;
    EStatusCode(String strStatus){
        this.strStatusCode = strStatus;
    }
    public String getStrStatusCode(){
        return strStatusCode;
    }

    public static EStatusCode getStatusCode(String strStatus){
        String UpCaseStrStatus = strStatus.toUpperCase();
        EStatusCode[] statusCodes = EStatusCode.values();
        for (int i = 0; i < statusCodes.length; i++) {
            if(statusCodes[i].getStrStatusCode().equals(UpCaseStrStatus)){
                return statusCodes[i];
            }
        }

        return BAD;
    }
}
