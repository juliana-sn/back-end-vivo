package br.purpletech.vivo.controllers;


import br.purpletech.vivo.dtos.platform.PlatformDTO;
import br.purpletech.vivo.dtos.platform.PlatformToCreateDTO;
import br.purpletech.vivo.models.Platform;
import br.purpletech.vivo.services.imp.PlatformServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/platforms")
public class PlatformController {
    private final PlatformServiceImp platformServiceImp;


    public PlatformController(PlatformServiceImp platformServiceImp) {
        this.platformServiceImp = platformServiceImp;
    }

    @GetMapping
    public ResponseEntity<List<PlatformDTO>> getAllPlatforms(){
        var platforms = platformServiceImp.getAllPlatforms();
        return ResponseEntity.ok(platforms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PlatformDTO>> getPlatformById(@PathVariable Long id){
        var platform = platformServiceImp.getById(id);
        return ResponseEntity.ok(platform);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<PlatformDTO>> updatePlatform(@PathVariable Long id, @RequestBody @Valid PlatformToCreateDTO updatedPlatform){
        var platform = platformServiceImp.updatePlatform(id, updatedPlatform);
        return ResponseEntity.ok(platform);
    }

    @PostMapping
    public ResponseEntity<PlatformDTO> createPlatform(@RequestBody @Valid PlatformToCreateDTO platformToCreate){
        var platform = platformServiceImp.createPlatform(platformToCreate);
        return ResponseEntity.ok(platform);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlatform(@PathVariable Long id){
        platformServiceImp.deletePlatform(id);
        return ResponseEntity.noContent().build();
    }
}
