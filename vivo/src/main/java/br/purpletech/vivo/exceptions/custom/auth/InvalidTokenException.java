package br.purpletech.vivo.exceptions.custom.auth;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Token inválido ou malformado.");
    }
}
