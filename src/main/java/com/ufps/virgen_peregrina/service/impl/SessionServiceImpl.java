package com.ufps.virgen_peregrina.service.impl;

import com.ufps.virgen_peregrina.service.SesionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SesionService {
    @Override
    public String getUsernameFromSession() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }
}
