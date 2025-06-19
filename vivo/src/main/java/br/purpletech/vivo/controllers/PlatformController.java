package br.purpletech.vivo.controllers;


import br.purpletech.vivo.models.Platform;
import br.purpletech.vivo.services.imp.PlatformServiceImp;
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
    public ResponseEntity<List<Platform>> getAllPlatforms(){
        var platforms = platformServiceImp.getAllPlatforms();
        return ResponseEntity.ok(platforms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Platform>> getPlatformById(@PathVariable Long id){
        var platform = platformServiceImp.getById(id);
        return ResponseEntity.ok(platform);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Platform>> updatePlatform(@PathVariable Long id, @RequestBody Platform updatedPlatform){
        var platform = platformServiceImp.updatePlatform(id, updatedPlatform);
        return ResponseEntity.ok(platform);
    }

    @PostMapping
    public ResponseEntity<Platform> createPlatform(@RequestBody Platform platformToCreate){
        var platform = platformServiceImp.createPlatform(platformToCreate);
        return ResponseEntity.ok(platform);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlatform(@PathVariable Long id){
        platformServiceImp.deletePlatform(id);
        return ResponseEntity.noContent().build();
    }
}
