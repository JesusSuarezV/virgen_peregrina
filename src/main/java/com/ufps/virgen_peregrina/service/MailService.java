package com.ufps.virgen_peregrina.service;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;


public interface MailService {
    public void enviarCorreo(String destinatario, String asunto, String cuerpo) throws MessagingException;
}