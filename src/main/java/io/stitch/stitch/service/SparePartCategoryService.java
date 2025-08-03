package io.stitch.stitch.service;

import io.stitch.stitch.dto.SparePartCategoryDTO;
import io.stitch.stitch.entity.SparePartCategory;
import io.stitch.stitch.entity.SparePartMainCategory;
import io.stitch.stitch.entity.SpearPart;
import io.stitch.stitch.repos.SparePartCategoryRepository;
import io.stitch.stitch.repos.SparePartMainCategoryRepository;
import io.stitch.stitch.repos.SpearPartRepository;
import io.stitch.stitch.util.NotFoundException;
import io.stitch.stitch.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SparePartCategoryService {

    private final PrimarySequenceService primarySequenceService;
    private final SparePartCategoryRepository sparePartCategoryRepository;
    private final SpearPartRepository spearPartRepository;
    private final SparePartMainCategoryRepository sparePartMainCategoryRepository;

    public SparePartCategoryService(PrimarySequenceService primarySequenceService, SparePartCategoryRepository sparePartCategoryRepository, SpearPartRepository spearPartRepository, SparePartMainCategoryRepository sparePartMainCategoryRepository) {
        this.primarySequenceService = primarySequenceService;
        this.sparePartCategoryRepository = sparePartCategoryRepository;
        this.spearPartRepository = spearPartRepository;
        this.sparePartMainCategoryRepository = sparePartMainCategoryRepository;
    }


    public List<SparePartCategoryDTO> findAll() {
        final List<SparePartCategory> sparePartCategories = sparePartCategoryRepository.findAll(Sort.by("id"));
        return sparePartCategories.stream().map(sparePartCategory -> mapToDTO(sparePartCategory, new SparePartCategoryDTO())).toList();
    }

    public List<SparePartCategoryDTO> findAllHomepageSparePartCategory() {
        final List<SparePartCategory> sparePartCategories = sparePartCategoryRepository.findAllByIsHomepageCategory(Boolean.TRUE,Sort.by("id"));
        return sparePartCategories.stream().map(sparePartCategory -> mapToDTO(sparePartCategory, new SparePartCategoryDTO())).toList();
    }

    public SparePartCategoryDTO get(final Long id) {
        return sparePartCategoryRepository.findById(id).map(sparePartCategory -> mapToDTO(sparePartCategory, new SparePartCategoryDTO())).orElseThrow(NotFoundException::new);
    }

    public Long create(final SparePartCategoryDTO sparePartCategoryDTO) {
        final SparePartCategory sparePartCategory = new SparePartCategory();
        sparePartCategory.setId(primarySequenceService.getNextValue());
        sparePartCategory.setIsHomepageCategory(Boolean.TRUE);
        mapToEntity(sparePartCategoryDTO, sparePartCategory);
        return sparePartCategoryRepository.save(sparePartCategory).getId();
    }

    public void update(final Long id, final SparePartCategoryDTO sparePartCategoryDTO) {
        final SparePartCategory sparePartCategory = sparePartCategoryRepository.findById(id).orElseThrow(NotFoundException::new);
        mapToEntity(sparePartCategoryDTO, sparePartCategory);
        sparePartCategoryRepository.save(sparePartCategory);
    }

    public void updateHomepageSparePartCategory(final Long brandId) {
        final SparePartCategory sparePartCategory = sparePartCategoryRepository.findById(brandId).orElseThrow(NotFoundException::new);
        sparePartCategory.setIsHomepageCategory(!sparePartCategory.getIsHomepageCategory());
        sparePartCategoryRepository.save(sparePartCategory);
    }


    public void delete(final Long id) {
        sparePartCategoryRepository.deleteById(id);
    }

    private SparePartCategoryDTO mapToDTO(final SparePartCategory sparePartCategory, final SparePartCategoryDTO sparePartCategoryDTO) {
        sparePartCategoryDTO.setId(sparePartCategory.getId());
        sparePartCategoryDTO.setName(sparePartCategory.getName());
        sparePartCategoryDTO.setDescription(sparePartCategory.getDescription());
        sparePartCategoryDTO.setLogoURL(sparePartCategory.getLogoURL());
        sparePartCategoryDTO.setIsHomepageCategory(sparePartCategory.getIsHomepageCategory());
        sparePartCategoryDTO.setMainCategoryId(sparePartCategory.getSparePartMainCategory().getId());
        return sparePartCategoryDTO;
    }

    private SparePartCategory mapToEntity(final SparePartCategoryDTO sparePartCategoryDTO, final SparePartCategory sparePartCategory) {
        sparePartCategory.setName(sparePartCategoryDTO.getName());
        sparePartCategory.setDescription(sparePartCategoryDTO.getDescription());
        sparePartCategory.setLogoURL(sparePartCategoryDTO.getLogoURL());
        Optional<SparePartMainCategory> optionalSparePartMainCategory = sparePartMainCategoryRepository.findById(sparePartCategoryDTO.getMainCategoryId());
        if (!optionalSparePartMainCategory.isPresent()) {
            throw new NotFoundException("Main Category not found");
        }
        sparePartCategory.setSparePartMainCategory(optionalSparePartMainCategory.get());
        return sparePartCategory;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final SparePartCategory sparePartCategory = sparePartCategoryRepository.findById(id).orElseThrow(NotFoundException::new);
        final SpearPart spearPart = spearPartRepository.findFirstBySparePartCategory(sparePartCategory);
        if (spearPart != null) {
            referencedWarning.setKey("spare.part.category.referenced");
            referencedWarning.addParam(spearPart.getId());
            return referencedWarning;
        }
        return null;
    }

}