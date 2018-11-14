package com.adrian.util;


import com.adrian.util.enums.OutputState;

/**
 * @author fengdan
 */
public class HttpStatusContent {

    private int code;
    private String message;
    private Object obj;

    public HttpStatusContent() {
    }

    public HttpStatusContent(OutputState state) {
        // TODO Auto-generated constructor stub
        this.code = state.getValue();
        this.message = state.getTxt();
    }

    public HttpStatusContent(OutputState state, Object obj) {
        // TODO Auto-generated constructor stub
        this.code = state.getValue();
        this.message = state.getTxt();
        this.obj = obj;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
