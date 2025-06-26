package br.purpletech.vivo.exceptions.custom.user;

public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException() {
        super("Chat não encontrado.");
    }
}
