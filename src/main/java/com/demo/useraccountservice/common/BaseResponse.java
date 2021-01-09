package com.demo.useraccountservice.common;

import java.util.List;
import java.util.Map;

public class BaseResponse {
    private boolean ok;
    private String error;
    private Map<String, String> fieldValidationErrors;
    private Object data;

    public BaseResponse(boolean ok, String error, Map<String, String> fieldValidationErrors, Object data) {
        this.ok = ok;
        this.error = error;
        this.fieldValidationErrors = fieldValidationErrors;
        this.data = data;
    }

    public BaseResponse(boolean ok, String error) {
        this.ok = ok;
        this.error = error;
    }

    public BaseResponse(Object data) {
        this.data = data;
    }

    public BaseResponse() {
    }

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
