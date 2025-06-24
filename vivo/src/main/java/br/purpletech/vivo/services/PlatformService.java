package br.purpletech.vivo.services;

import br.purpletech.vivo.dtos.platform.PlatformDTO;
import br.purpletech.vivo.dtos.platform.PlatformToCreateDTO;
import br.purpletech.vivo.models.Platform;

import java.util.List;
import java.util.Optional;

public interface PlatformService {
    List<PlatformDTO> getAllPlatforms();

    Optional<PlatformDTO> getById(Long id);

    PlatformDTO createPlatform(PlatformToCreateDTO platformToCreate);

    boolean deletePlatform(Long id);

    Optional<PlatformDTO> updatePlatform (Long id, PlatformToCreateDTO updatedPlatform);
}
