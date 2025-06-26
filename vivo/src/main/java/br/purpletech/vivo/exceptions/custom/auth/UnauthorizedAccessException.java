package br.purpletech.vivo.exceptions.custom.auth;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException() {
        super("Acesso não autorizado.");
    }
}
