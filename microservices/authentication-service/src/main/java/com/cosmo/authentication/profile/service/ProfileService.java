package com.cosmo.authentication.profile.service;

import com.cosmo.common.model.ApiResponse;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface ProfileService {
    Mono<ApiResponse<?>> getProfileDetail(Principal connectedUser);
}
