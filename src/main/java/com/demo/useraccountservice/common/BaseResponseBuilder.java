package com.demo.useraccountservice.common;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

public class BaseResponseBuilder {
    private boolean ok;
    private String error;
    private Map<String, String> fieldValidationErrors;
    private Object data;

    public BaseResponseBuilder succeeded() {
        ok = true;
        return this;
    }

    public BaseResponseBuilder failed() {
        ok = false;
        return this;
    }

    public BaseResponseBuilder withError(String error) {
        this.error = error;
        return this;
    }

    public BaseResponseBuilder withFieldValidationErrors(Map<String, String> fieldValidationErrors) {
        this.fieldValidationErrors = fieldValidationErrors;
        return this;
    }

    public BaseResponseBuilder withBindingResult(BindingResult bindingResult) {
        error = bindingResult
                .getAllErrors()
                .stream()
                .filter(e -> !(e instanceof FieldError))
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(System.lineSeparator()));
        fieldValidationErrors = bindingResult
                .getAllErrors()
                .stream()
                .filter(e -> e instanceof FieldError)
                .map(e -> (FieldError) e)
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return this;
    }

    public BaseResponseBuilder withData(Object data) {
        this.data = data;
        return this;
    }

    public BaseResponse build() {
        var resp = new BaseResponse();
        resp.setOk(ok);
        resp.setError(error);
        resp.setFieldValidationErrors(fieldValidationErrors);
        resp.setData(data);
        return resp;
    }
}
