package br.purpletech.vivo.services;

import br.purpletech.vivo.models.Platform;

import java.util.List;
import java.util.Optional;

public interface PlatformService {
    List<Platform> getAllPlatforms();

    Optional<Platform> getById(Long id);

    Platform createPlatform(Platform platformToCreate);

    boolean deletePlatform(Long id);

    Optional<Platform> updatePlatform (Long id, Platform updatedPlatform);
}
