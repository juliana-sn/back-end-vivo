package br.purpletech.vivo.exceptions.custom.platform;

public class PlatformNotFoundException extends RuntimeException {
    public PlatformNotFoundException() {
        super("Plataforma não encontrada.");
    }
}
