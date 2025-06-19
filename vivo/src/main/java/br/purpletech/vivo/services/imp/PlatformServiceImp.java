package br.purpletech.vivo.services.imp;


import br.purpletech.vivo.models.Platform;
import br.purpletech.vivo.repositories.PlatformRepository;
import br.purpletech.vivo.services.PlatformService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatformServiceImp implements PlatformService {

    private final PlatformRepository platformRepository;

    public PlatformServiceImp(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    @Override
    public List<Platform> getAllPlatforms() {
        return platformRepository.findAll();
    }

    @Override
    public Optional<Platform> getById(Long id) {
        return platformRepository.findById(id);
    }

    @Override
    public Platform createPlatform(Platform platformToCreate) {
        return platformRepository.save(platformToCreate);
    }

    @Override
    public boolean deletePlatform(Long id) {
        return platformRepository.findById(id).map(platform -> {
            platformRepository.delete(platform);
            return true;
        }).orElse(false);
    }

    @Override
    public Optional<Platform> updatePlatform(Long id, Platform updatedPlatform) {
        return platformRepository.findById(id).map(platform -> {
            platform.setName(updatedPlatform.getName());
            platform.setType_access(updatedPlatform.getType_access());
            return platformRepository.save(platform);
        });
    }
}
