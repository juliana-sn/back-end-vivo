package br.purpletech.vivo.services.imp;


import br.purpletech.vivo.dtos.platform.PlatformDTO;
import br.purpletech.vivo.dtos.platform.PlatformToCreateDTO;
import br.purpletech.vivo.models.Platform;
import br.purpletech.vivo.repositories.PlatformRepository;
import br.purpletech.vivo.services.PlatformService;
import br.purpletech.vivo.utils.EntityDtoConverter;
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
    public Optional<PlatformDTO> getById(Long id) {
        return platformRepository.findById(id).map(EntityDtoConverter::toPlatformDTO);
    }

    @Transactional
    @Override
    public PlatformDTO createPlatform(PlatformToCreateDTO platformToCreate) {
        Platform platform = EntityDtoConverter.toPlatform(platformToCreate);
        Platform saved = platformRepository.save(platform);
        return EntityDtoConverter.toPlatformDTO(saved);
    }

    @Transactional
    @Override
    public boolean deletePlatform(Long id) {
        return platformRepository.findById(id).map(platform -> {
            platformRepository.delete(platform);
            return true;
        }).orElse(false);
    }

    @Transactional
    @Override
    public Optional<PlatformDTO> updatePlatform(Long id, PlatformToCreateDTO updatedPlatform) {
        return platformRepository.findById(id).map(platform -> {
            platform.setName(updatedPlatform.name());
            platform.setType_access(updatedPlatform.type_access());
            platform.setUrl(updatedPlatform.url());
            Platform platformSaved = platformRepository.save(platform);
            return EntityDtoConverter.toPlatformDTO(platformSaved);
        });
    }
}
