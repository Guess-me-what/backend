package com.guessmewhat.backend.domain.auth.client.api;

import com.guessmewhat.backend.domain.auth.client.request.RefreshTokenRequest;
import com.guessmewhat.backend.domain.auth.client.request.SignInRequest;
import com.guessmewhat.backend.domain.auth.client.request.SignUpRequest;
import com.guessmewhat.backend.domain.auth.service.AuthService;
import com.guessmewhat.backend.domain.auth.service.response.JsonWebTokenResponse;
import com.guessmewhat.backend.domain.auth.service.response.RefreshTokenResponse;
import com.guessmewhat.backend.global.common.response.BaseResponse;
import com.guessmewhat.backend.global.common.response.BaseResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse signUp(@Valid @RequestBody SignUpRequest request){
        authService.signUp(request);
        return BaseResponseData.created("sign up successful");
    }

    @PostMapping("/signin")
    public BaseResponseData<JsonWebTokenResponse> signIn(@Valid @RequestBody SignInRequest request){
        return BaseResponseData.ok(
                "sign in successful",
                authService.signIn(request));
    }

    @PostMapping("/refresh")
    public BaseResponseData<RefreshTokenResponse> refresh(RefreshTokenRequest request){
        return BaseResponseData.ok(
                "token reissue successful",
                authService.refresh(request.refreshToken()));
    }

}
