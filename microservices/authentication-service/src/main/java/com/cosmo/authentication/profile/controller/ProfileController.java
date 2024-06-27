package com.cosmo.authentication.profile.controller;

import com.cosmo.authentication.profile.model.request.ChangePasswordRequest;
import com.cosmo.authentication.profile.model.request.EditProfileRequest;
import com.cosmo.authentication.profile.service.ProfileService;
import com.cosmo.common.constant.ApiConstant;
import com.cosmo.common.model.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping(ApiConstant.PROFILE)
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    @GetMapping(ApiConstant.VIEW)
    public Mono<ApiResponse<?>> getProfileDetail(Principal connectedUser){
        return profileService.getProfileDetail(connectedUser);
    }
    @PostMapping(ApiConstant.CHANGE_PASSWORD)
    public Mono<ApiResponse<?>> changePassword(@RequestBody @Valid  ChangePasswordRequest changePasswordRequest, Principal connectedUser){
        return profileService.changePassword(changePasswordRequest, connectedUser);
    }
    @PostMapping(ApiConstant.UPDATE)
    public Mono<ApiResponse<?>> editProfile(@RequestBody @Valid EditProfileRequest editProfileRequest, Principal connectedUser){
        return profileService.editProfile(editProfileRequest, connectedUser);
    }
}
