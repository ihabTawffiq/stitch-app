package io.stitch.stitch.service;

import io.stitch.stitch.domain.Category;
import io.stitch.stitch.domain.Machine;
import io.stitch.stitch.model.CategoryDTO;
import io.stitch.stitch.repos.CategoryRepository;
import io.stitch.stitch.repos.MachineRepository;
import io.stitch.stitch.util.NotFoundException;
import io.stitch.stitch.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final MachineRepository machineRepository;
    private final PrimarySequenceService primarySequenceService;

    public CategoryService(final CategoryRepository categoryRepository,
                           final MachineRepository machineRepository, PrimarySequenceService primarySequenceService) {
        this.categoryRepository = categoryRepository;
        this.machineRepository = machineRepository;
        this.primarySequenceService = primarySequenceService;
    }

    public List<CategoryDTO> findAll() {
        final List<Category> categories = categoryRepository.findAll(Sort.by("id"));
        return categories.stream()
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .toList();
    }

    public CategoryDTO get(final Long id) {
        return categoryRepository.findById(id)
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CategoryDTO categoryDTO) {
        final Category category = new Category();
        category.setId(primarySequenceService.getNextValue());
        mapToEntity(categoryDTO, category);
        return categoryRepository.save(category).getId();
    }

    public void update(final Long id, final CategoryDTO categoryDTO) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categoryDTO, category);
        categoryRepository.save(category);
    }

    public void delete(final Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO mapToDTO(final Category category, final CategoryDTO categoryDTO) {
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }

    private Category mapToEntity(final CategoryDTO categoryDTO, final Category category) {
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Category category = categoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Machine categoryMachine = machineRepository.findFirstByCategory(category);
        if (categoryMachine != null) {
            referencedWarning.setKey("category.machine.category.referenced");
            referencedWarning.addParam(categoryMachine.getId());
            return referencedWarning;
        }
        return null;
    }

}
