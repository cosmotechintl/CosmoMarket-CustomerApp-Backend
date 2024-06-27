package com.cosmo.authentication.profile.service;

import com.cosmo.authentication.profile.model.request.ChangeEmailRequest;
import com.cosmo.authentication.profile.model.request.ChangePasswordRequest;
import com.cosmo.authentication.profile.model.request.EditProfileRequest;
import com.cosmo.common.model.ApiResponse;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface ProfileService {
    Mono<ApiResponse<?>> getProfileDetail(Principal connectedUser);
    Mono<ApiResponse<?>> changePassword(ChangePasswordRequest changePasswordRequest, Principal connectedUser);
    Mono<ApiResponse<?>> editProfile(EditProfileRequest editProfileRequest, Principal connectedUser);
    Mono<ApiResponse<?>> changeEmail(ChangeEmailRequest changeEmailRequest, Principal connectedUser);
}
