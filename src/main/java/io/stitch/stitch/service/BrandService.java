package io.stitch.stitch.service;

import io.stitch.stitch.dto.BrandDTO;
import io.stitch.stitch.entity.Brand;
import io.stitch.stitch.entity.Machine;
import io.stitch.stitch.repos.BrandRepository;
import io.stitch.stitch.repos.MachineRepository;
import io.stitch.stitch.util.NotFoundException;
import io.stitch.stitch.util.ReferencedWarning;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final MachineRepository machineRepository;
    private final PrimarySequenceService primarySequenceService;

    public BrandService(final BrandRepository brandRepository, final MachineRepository machineRepository, PrimarySequenceService primarySequenceService) {
        this.brandRepository = brandRepository;
        this.machineRepository = machineRepository;
        this.primarySequenceService = primarySequenceService;
    }

    @Cacheable(value = "longCache", key = "'allBrands'")
    public List<BrandDTO> findAll() {
        final List<Brand> brands = brandRepository.findAll(Sort.by("id"));
        return brands.stream().map(brand -> mapToDTO(brand, new BrandDTO())).toList();
    }

    public BrandDTO get(final Long id) {
        return brandRepository.findById(id).map(brand -> mapToDTO(brand, new BrandDTO())).orElseThrow(NotFoundException::new);
    }

    @CacheEvict(value = "longCache", allEntries = true)
    public Long create(final BrandDTO brandDTO) {
        final Brand brand = new Brand();
        brand.setId(primarySequenceService.getNextValue());
        mapToEntity(brandDTO, brand);
        return brandRepository.save(brand).getId();
    }

    @CacheEvict(value = "longCache", allEntries = true)
    public void update(final Long id, final BrandDTO brandDTO) {
        final Brand brand = brandRepository.findById(id).orElseThrow(NotFoundException::new);
        mapToEntity(brandDTO, brand);
        brandRepository.save(brand);
    }

    @CacheEvict(value = "longCache", allEntries = true)
    public void delete(final Long id) {
        brandRepository.deleteById(id);
    }

    private BrandDTO mapToDTO(final Brand brand, final BrandDTO brandDTO) {
        brandDTO.setId(brand.getId());
        brandDTO.setName(brand.getName());
        brandDTO.setDescription(brand.getDescription());
        brandDTO.setLogoURL(brand.getLogoURL());
        return brandDTO;
    }

    private Brand mapToEntity(final BrandDTO brandDTO, final Brand brand) {
        brand.setName(brandDTO.getName());
        brand.setDescription(brandDTO.getDescription());
        brand.setLogoURL(brandDTO.getLogoURL());
        return brand;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Brand brand = brandRepository.findById(id).orElseThrow(NotFoundException::new);
        final Machine brandMachine = machineRepository.findFirstByBrand(brand);
        if (brandMachine != null) {
            referencedWarning.setKey("brand.machine.brand.referenced");
            referencedWarning.addParam(brandMachine.getId());
            return referencedWarning;
        }
        return null;
    }

}