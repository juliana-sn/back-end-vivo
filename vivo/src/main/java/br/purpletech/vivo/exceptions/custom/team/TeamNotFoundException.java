package br.purpletech.vivo.exceptions.custom.team;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException() {
        super("Equipe não encontrada.");
    }
}

