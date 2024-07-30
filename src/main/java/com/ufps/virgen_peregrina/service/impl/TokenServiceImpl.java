package com.ufps.virgen_peregrina.service.impl;

import com.ufps.virgen_peregrina.entity.Token;
import com.ufps.virgen_peregrina.repository.TokenRepository;
import com.ufps.virgen_peregrina.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public Token obtenerToken(String codigo) {
        return tokenRepository.getReferenceById(codigo);
    }

    @Override
    public void guardarToken(Token token) {
        tokenRepository.save(token);
    }
}
