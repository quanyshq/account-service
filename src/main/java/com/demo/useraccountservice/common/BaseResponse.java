package com.demo.useraccountservice.common;

import java.util.Map;

public class BaseResponse {
    private boolean ok;
    private String error;
    private Map<String, String> fieldValidationErrors;
    private Object data;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Map<String, String> getFieldValidationErrors() {
        return fieldValidationErrors;
    }

    public void setFieldValidationErrors(Map<String, String> fieldValidationErrors) {
        this.fieldValidationErrors = fieldValidationErrors;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
