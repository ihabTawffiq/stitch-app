package io.stitch.stitch.service;

import io.stitch.stitch.dto.SparePartCategoryDTO;
import io.stitch.stitch.dto.SparePartMainCategoryDTO;
import io.stitch.stitch.entity.SparePartCategory;
import io.stitch.stitch.entity.SparePartMainCategory;
import io.stitch.stitch.repos.SparePartMainCategoryRepository;
import io.stitch.stitch.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SparePartMainCategoryService {

    private final PrimarySequenceService primarySequenceService;
    private final SparePartMainCategoryRepository sparePartMainCategoryRepository;

    public SparePartMainCategoryService(PrimarySequenceService primarySequenceService, SparePartMainCategoryRepository sparePartMainCategoryRepository) {
        this.primarySequenceService = primarySequenceService;
        this.sparePartMainCategoryRepository = sparePartMainCategoryRepository;
    }


    public List<SparePartMainCategoryDTO> findAll() {
        final List<SparePartMainCategory> sparePartMainCategories = sparePartMainCategoryRepository.findAll(Sort.by("id"));
        return sparePartMainCategories.stream().map(sparePartMainCategory -> mapToDTO(sparePartMainCategory, new SparePartMainCategoryDTO())).toList();
    }

    public List<SparePartMainCategoryDTO> findAllHomepageSparePartCategory() {
        final List<SparePartMainCategory> sparePartMainCategories = sparePartMainCategoryRepository.findAllByIsHomepageCategory(Boolean.TRUE, Sort.by("id"));
        return sparePartMainCategories.stream().map(sparePartCategory -> mapToDTO(sparePartCategory, new SparePartMainCategoryDTO())).toList();
    }

    public SparePartMainCategoryDTO get(final Long id) {
        return sparePartMainCategoryRepository.findById(id).map(sparePartMainCategory -> mapToDTO(sparePartMainCategory, new SparePartMainCategoryDTO())).orElseThrow(NotFoundException::new);
    }

    public Long create(final SparePartMainCategoryDTO sparePartMainCategoryDTO) {
        final SparePartMainCategory sparePartMainCategory = new SparePartMainCategory();
        sparePartMainCategory.setId(primarySequenceService.getNextValue());
        sparePartMainCategory.setIsHomepageCategory(Boolean.FALSE);
        mapToEntity(sparePartMainCategoryDTO, sparePartMainCategory);
        return sparePartMainCategoryRepository.save(sparePartMainCategory).getId();
    }

    public void update(final Long id, final SparePartMainCategoryDTO sparePartMainCategoryDTO) {
        final SparePartMainCategory sparePartMainCategory = sparePartMainCategoryRepository.findById(id).orElseThrow(NotFoundException::new);
        mapToEntity(sparePartMainCategoryDTO, sparePartMainCategory);
        sparePartMainCategoryRepository.save(sparePartMainCategory);
    }

    public void updateHomepageSparePartCategory(final Long brandId) {
        final SparePartMainCategory sparePartMainCategory = sparePartMainCategoryRepository.findById(brandId).orElseThrow(NotFoundException::new);
        sparePartMainCategory.setIsHomepageCategory(!sparePartMainCategory.getIsHomepageCategory());
        sparePartMainCategoryRepository.save(sparePartMainCategory);
    }


    private SparePartMainCategoryDTO mapToDTO(final SparePartMainCategory sparePartMainCategory, final SparePartMainCategoryDTO sparePartMainCategoryDTO) {
        sparePartMainCategoryDTO.setId(sparePartMainCategory.getId());
        sparePartMainCategoryDTO.setName(sparePartMainCategory.getName());
        sparePartMainCategoryDTO.setDescription(sparePartMainCategory.getDescription());
        sparePartMainCategoryDTO.setLogoURL(sparePartMainCategory.getLogoURL());
        sparePartMainCategoryDTO.setIsHomepageCategory(sparePartMainCategory.getIsHomepageCategory());
        sparePartMainCategoryDTO.setSparePartCategories(sparePartMainCategory.getSparePartCategory().stream().map(sparePartCategory -> categoryMapToDTO(sparePartCategory, new SparePartCategoryDTO())).collect(Collectors.toSet()));
        return sparePartMainCategoryDTO;
    }

    private SparePartMainCategory mapToEntity(final SparePartMainCategoryDTO sparePartMainCategoryDTO, final SparePartMainCategory sparePartMainCategory) {
        sparePartMainCategory.setName(sparePartMainCategoryDTO.getName());
        sparePartMainCategory.setDescription(sparePartMainCategoryDTO.getDescription());
        sparePartMainCategory.setLogoURL(sparePartMainCategoryDTO.getLogoURL());
        sparePartMainCategory.setSparePartCategory(sparePartMainCategoryDTO.getSparePartCategories().stream().map(sparePartCategoryDTO -> categoryDTOMapToEntity(sparePartCategoryDTO, new SparePartCategory())).collect(Collectors.toSet()));
        return sparePartMainCategory;
    }

    private SparePartCategoryDTO categoryMapToDTO(final SparePartCategory sparePartCategory, final SparePartCategoryDTO sparePartCategoryDTO) {
        sparePartCategoryDTO.setId(sparePartCategory.getId());
        sparePartCategoryDTO.setName(sparePartCategory.getName());
        sparePartCategoryDTO.setDescription(sparePartCategory.getDescription());
        sparePartCategoryDTO.setLogoURL(sparePartCategory.getLogoURL());
        sparePartCategoryDTO.setIsHomepageCategory(sparePartCategory.getIsHomepageCategory());
        return sparePartCategoryDTO;
    }

    private SparePartCategory categoryDTOMapToEntity(final SparePartCategoryDTO sparePartCategoryDTO, final SparePartCategory sparePartCategory) {
        sparePartCategory.setName(sparePartCategoryDTO.getName());
        sparePartCategory.setDescription(sparePartCategoryDTO.getDescription());
        sparePartCategory.setLogoURL(sparePartCategoryDTO.getLogoURL());
        return sparePartCategory;
    }


}