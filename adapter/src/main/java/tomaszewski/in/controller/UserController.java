package tomaszewski.in.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tomaszewski.in.UserUseCase;
import tomaszewski.in.mapper.SecurityUserMapper;
import tomaszewski.openapi.api.UserApi;
import tomaszewski.openapi.model.UserData;
import tomaszewski.security.UserSecurityDetails;

@RestController
@Log4j2
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserUseCase userUseCase;
    private final SecurityUserMapper securityUserMapper;

    @GetMapping("api/user")
    public ResponseEntity<UserData> getUserData(@AuthenticationPrincipal UserSecurityDetails userSecurityDetails) {
        UserData userData = securityUserMapper.toUserData(userSecurityDetails);
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }
}
