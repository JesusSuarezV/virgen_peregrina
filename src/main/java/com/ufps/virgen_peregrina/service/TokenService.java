package com.ufps.virgen_peregrina.service;

import com.ufps.virgen_peregrina.entity.Token;

public interface TokenService {

    public Token obtenerToken(String codigo);

    public void guardarToken(Token token);

}
