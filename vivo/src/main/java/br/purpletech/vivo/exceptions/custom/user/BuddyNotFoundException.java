package br.purpletech.vivo.exceptions.custom.user;

public class BuddyNotFoundException extends RuntimeException {
    public BuddyNotFoundException() {
        super("Buddy não encontrado.");
    }
}
