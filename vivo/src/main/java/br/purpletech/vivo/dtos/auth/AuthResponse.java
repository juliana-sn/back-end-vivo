package br.purpletech.vivo.dtos.auth;

public record AuthResponse(String token, Long userId, String role) {
}
