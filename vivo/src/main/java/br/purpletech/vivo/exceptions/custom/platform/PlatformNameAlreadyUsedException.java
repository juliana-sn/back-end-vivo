package br.purpletech.vivo.exceptions.custom.platform;

public class PlatformNameAlreadyUsedException extends RuntimeException {
    public PlatformNameAlreadyUsedException() {
        super("Este nome de plataforma já está em uso.");
    }
}
