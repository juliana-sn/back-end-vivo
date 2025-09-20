package br.purpletech.vivo.exceptions.custom.auth;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException() {
        super("O token de autenticação expirou.");
    }
}

