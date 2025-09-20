package br.purpletech.vivo.exceptions.custom.user;

public class ManagerNotFoundException extends RuntimeException {
    public ManagerNotFoundException() {
        super("Gestor não encontrado.");
    }
}
