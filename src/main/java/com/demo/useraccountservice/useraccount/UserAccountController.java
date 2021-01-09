package com.demo.useraccountservice.useraccount;

import com.demo.useraccountservice.common.BaseResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user-accounts")
public class UserAccountController {
    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/")
    public ResponseEntity<BaseResponse> registerUserAccount(BindingResult bindingResult,
                                                            @Valid @RequestBody UserAccountRegistrationRequest userAccountRegistrationRequest) {
        var userAccountResponse = new BaseResponse(userAccountRegistrationRequest);
        if (bindingResult.hasErrors()) {
            userAccountResponse.setOk(false);
            userAccountResponse.setError(bindingResult
                    .getAllErrors()
                    .stream()
                    .filter(e -> e instanceof FieldError)
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(System.lineSeparator()))
            );
            userAccountResponse.setFieldValidationErrors(bindingResult
                    .getAllErrors()
                    .stream()
                    .filter(e -> e instanceof FieldError)
                    .map(e -> (FieldError) e)
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
            );
            return new ResponseEntity<>(userAccountResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        userAccountService.registerWithPassword(userAccountRegistrationRequest.getUserAccount(), userAccountRegistrationRequest.getPassword());
        userAccountResponse.setOk(true);
        return new ResponseEntity<>(userAccountResponse, HttpStatus.CREATED);
    }
}
