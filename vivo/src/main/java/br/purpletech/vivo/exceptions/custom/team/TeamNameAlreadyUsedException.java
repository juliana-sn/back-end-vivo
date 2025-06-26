package br.purpletech.vivo.exceptions.custom.team;

public class TeamNameAlreadyUsedException extends RuntimeException {
    public TeamNameAlreadyUsedException() {
        super("Este nome de equipe já está em uso.");
    }
}
