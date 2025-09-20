package br.purpletech.vivo.exceptions.custom.user;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException() {
        super("Este e-mail já está em uso.");
    }
}

