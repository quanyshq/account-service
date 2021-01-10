package com.demo.useraccountservice.useraccount;

import com.demo.useraccountservice.common.BaseResponse;
import com.demo.useraccountservice.common.BaseResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserAccountController {
    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/user-accounts")
    public ResponseEntity<BaseResponse> registerUserAccount(
            @Valid @RequestBody UserAccountRegistrationRequest registrationRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new BaseResponseBuilder().failed().withBindingResult(bindingResult).build(),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        userAccountService.registerWithPassword(registrationRequest.getUserAccount(), registrationRequest.getPassword());
        return new ResponseEntity<>(new BaseResponseBuilder().succeeded().withData(registrationRequest.getUserAccount()).build(),
                HttpStatus.CREATED);
    }

    @PostMapping("/authentication-requests")
    public ResponseEntity<BaseResponse> authenticateUserAccount(
            @Valid @RequestBody UserAccountAuthenticationRequest authenticationRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new BaseResponseBuilder().failed().withBindingResult(bindingResult).build(),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        var acc = userAccountService.authenticateWithPassword(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        if (acc == null) {
            return new ResponseEntity<>(new BaseResponseBuilder().failed().withError("User account authentication attempt failed.").build(),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(new BaseResponseBuilder().succeeded().withData(acc).build(), HttpStatus.OK);
    }
}
