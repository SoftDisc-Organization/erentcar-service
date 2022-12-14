package com.acme.webserviceserentcar.security.domain.service.communication;

import com.acme.webserviceserentcar.security.resource.UserResource;
import com.acme.webserviceserentcar.shared.domain.service.communication.BaseResponse;

public class RegisterResponse extends BaseResponse<UserResource> {
    public RegisterResponse(String message) {
        super(message);
    }
    public RegisterResponse(UserResource resource) {
        super(resource);
    }
}
