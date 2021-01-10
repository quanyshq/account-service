package com.demo.useraccountservice.useraccount;

import com.demo.useraccountservice.common.BaseResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserAccountController {
    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/user-accounts")
    public ResponseEntity<BaseResponse> registerUserAccount(BindingResult bindingResult,
                                                            @Valid @RequestBody UserAccountRegistrationRequest userAccountRegistrationRequest) {
        var resp = new BaseResponse();
        if (bindingResult.hasErrors()) {
            resp.setOk(false);
            resp.setError(bindingResult
                    .getAllErrors()
                    .stream()
                    .filter(e -> e instanceof FieldError)
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(System.lineSeparator()))
            );
            resp.setFieldValidationErrors(bindingResult
                    .getAllErrors()
                    .stream()
                    .filter(e -> e instanceof FieldError)
                    .map(e -> (FieldError) e)
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
            );
            return new ResponseEntity<>(resp, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        userAccountService.registerWithPassword(userAccountRegistrationRequest.getUserAccount(), userAccountRegistrationRequest.getPassword());
        resp.setOk(true);
        resp.setData(userAccountRegistrationRequest.getUserAccount());
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PostMapping("/authentication-requests")
    public ResponseEntity<BaseResponse> authenticateUserAccount(@RequestBody String email, @RequestBody String password) {
        var resp = new BaseResponse();
        var acc = userAccountService.authenticateWithPassword(email, password);
        if (acc == null) {
            resp.setOk(false);
            resp.setError("User account authentication attempt failed.");
            return new ResponseEntity<>(resp, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        resp.setOk(true);
        resp.setData(acc);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
