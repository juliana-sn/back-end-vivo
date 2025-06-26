package br.purpletech.vivo.exceptions.custom.auth;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Credenciais inválidas. Verifique seu e-mail e senha.");
    }
}
