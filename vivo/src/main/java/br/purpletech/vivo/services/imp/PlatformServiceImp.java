package br.purpletech.vivo.services.imp;


import br.purpletech.vivo.dtos.platform.PlatformDTO;
import br.purpletech.vivo.dtos.platform.PlatformToCreateDTO;
import br.purpletech.vivo.models.Platform;
import br.purpletech.vivo.repositories.PlatformRepository;
import br.purpletech.vivo.services.PlatformService;
import br.purpletech.vivo.utils.EntityDtoConverter;
import br.purpletech.vivo.exceptions.custom.platform.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlatformServiceImp implements PlatformService {

    private final PlatformRepository platformRepository;

    public PlatformServiceImp(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    @Override
    public List<PlatformDTO> getAllPlatforms() {
        return platformRepository.findAll().stream()
                .map(EntityDtoConverter::toPlatformDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlatformDTO getById(Long id) {
        Platform platform = platformRepository.findById(id)
                .orElseThrow(PlatformNotFoundException::new);

        return EntityDtoConverter.toPlatformDTO(platform);
    }

    @Transactional
    @Override
    public PlatformDTO createPlatform(PlatformToCreateDTO platformToCreate) {
        if (platformRepository.existsByName(platformToCreate.name())) {
            throw new PlatformNameAlreadyUsedException();
        }

        Platform platform = EntityDtoConverter.toPlatform(platformToCreate);
        Platform saved = platformRepository.save(platform);
        return EntityDtoConverter.toPlatformDTO(saved);
    }

    @Transactional
    @Override
    public void deletePlatform(Long id) {
        Platform platform = platformRepository.findById(id)
                .orElseThrow(PlatformNotFoundException::new);

        platformRepository.delete(platform);
    }


    @Transactional
    @Override
    public PlatformDTO updatePlatform(Long id, PlatformToCreateDTO updatedPlatform) {
        Platform platform = platformRepository.findById(id)
                .orElseThrow(PlatformNotFoundException::new);

        if (!platform.getName().equalsIgnoreCase(updatedPlatform.name()) &&
                platformRepository.existsByName(updatedPlatform.name())) {
            throw new PlatformNameAlreadyUsedException();
        }

        platform.setName(updatedPlatform.name());
        platform.setType_access(updatedPlatform.type_access());
        platform.setUrl(updatedPlatform.url());

        Platform platformSaved = platformRepository.save(platform);
        return EntityDtoConverter.toPlatformDTO(platformSaved);
    }

}
