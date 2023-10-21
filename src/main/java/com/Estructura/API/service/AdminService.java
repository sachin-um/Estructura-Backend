package com.Estructura.API.service;

import com.Estructura.API.model.Admin;
import com.Estructura.API.requests.auth.RegisterRequest;
import com.Estructura.API.responses.auth.RegisterResponse;

public interface AdminService {
    Admin saveAdmin(Admin admin);

    RegisterResponse crateAnAdmin(RegisterRequest registerRequest);
}
