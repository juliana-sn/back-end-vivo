package br.purpletech.vivo.services;

import br.purpletech.vivo.dtos.platform.PlatformDTO;
import br.purpletech.vivo.dtos.platform.PlatformToCreateDTO;
import br.purpletech.vivo.models.Platform;

import java.util.List;
import java.util.Optional;

public interface PlatformService {
    List<PlatformDTO> getAllPlatforms();

    PlatformDTO getById(Long id);

    PlatformDTO createPlatform(PlatformToCreateDTO platformToCreate);

    void deletePlatform(Long id);

    PlatformDTO updatePlatform (Long id, PlatformToCreateDTO updatedPlatform);
}
