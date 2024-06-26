package com.cosmo.authentication.profile.controller;

import com.cosmo.authentication.profile.service.ProfileService;
import com.cosmo.common.constant.ApiConstant;
import com.cosmo.common.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping(ApiConstant.VIEW_PROFILE)
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    @GetMapping()
    public Mono<ApiResponse<?>> getProfileDetail(Principal connectedUser){
        return profileService.getProfileDetail(connectedUser);
    }
}
