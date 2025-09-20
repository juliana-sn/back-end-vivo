package br.purpletech.vivo.controllers;


import br.purpletech.vivo.dtos.auth.AuthRequest;
import br.purpletech.vivo.dtos.auth.AuthResponse;
import br.purpletech.vivo.dtos.user.UserToCreateDTO;
import br.purpletech.vivo.services.imp.AuthServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImp authService;

    public AuthController(AuthServiceImp authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserToCreateDTO dto) {
        var message = authService.register(dto);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        var token = authService.login(request);
        return ResponseEntity.ok(token);
    }
}
