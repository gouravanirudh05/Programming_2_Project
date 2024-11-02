package com.operatoroverloaded.hotel.models;
// Error Codes:
// 0 - No Error
// 1 - Segmentation Fault
// 2 - Argument Error
// 3 - File Not Found
// 4 - Misc. Error

public class Error extends Exception{
    private int code;

    public Error(String message) {
        super(message);
        this.code = 0;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public int getCodeText(){
        switch (code){
            case 1:
                return "Segmentation Fault";
            case 2:
                return "Argument Error";
            case 3:
                return "File Not Found";
            case 4:
                return "Misc. Error";
            default:
                return "No Error";
        }
    }
}