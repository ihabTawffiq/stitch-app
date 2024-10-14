package io.stitch.stitch.service;

import io.stitch.stitch.domain.Brand;
import io.stitch.stitch.domain.Category;
import io.stitch.stitch.domain.Machine;
import io.stitch.stitch.domain.Tag;
import io.stitch.stitch.model.MachineDTO;
import io.stitch.stitch.repos.BrandRepository;
import io.stitch.stitch.repos.CategoryRepository;
import io.stitch.stitch.repos.MachineRepository;
import io.stitch.stitch.repos.TagRepository;
import io.stitch.stitch.util.NotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MachineService {

    private final MachineRepository machineRepository;
    private final BrandRepository brandRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final PrimarySequenceService primarySequenceService;

    public MachineService(final MachineRepository machineRepository,
                          final BrandRepository brandRepository, final TagRepository tagRepository,
                          final CategoryRepository categoryRepository, PrimarySequenceService primarySequenceService) {
        this.machineRepository = machineRepository;
        this.brandRepository = brandRepository;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
        this.primarySequenceService = primarySequenceService;
    }

    public List<MachineDTO> findAll() {
        final List<Machine> machines = machineRepository.findAll(Sort.by("id"));
        return machines.stream()
                .map(machine -> mapToDTO(machine, new MachineDTO()))
                .toList();
    }

    public MachineDTO get(final Long id) {
        return machineRepository.findById(id)
                .map(machine -> mapToDTO(machine, new MachineDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MachineDTO machineDTO) {
        final Machine machine = new Machine();
        machine.setId(primarySequenceService.getNextValue());
        mapToEntity(machineDTO, machine);
        return machineRepository.save(machine).getId();
    }

    public void update(final Long id, final MachineDTO machineDTO) {
        final Machine machine = machineRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(machineDTO, machine);
        machineRepository.save(machine);
    }

    public void delete(final Long id) {
        machineRepository.deleteById(id);
    }

    private MachineDTO mapToDTO(final Machine machine, final MachineDTO machineDTO) {
        machineDTO.setId(machine.getId());
        machineDTO.setModel(machine.getModel());
        machineDTO.setDescription(machine.getDescription());
        machineDTO.setStock(machine.getStock());
        machineDTO.setMainImageUrl(machine.getMainImageUrl());
        machineDTO.setFinalPrice(machine.getFinalPrice());
        machineDTO.setInitialPrice(machine.getInitialPrice());
        machineDTO.setBrand(machine.getBrand() == null ? null : machine.getBrand().getId());
        machineDTO.setTags(machine.getTags().stream()
                .map(tag -> tag.getId())
                .toList());
        machineDTO.setCategory(machine.getCategory() == null ? null : machine.getCategory().getId());
        return machineDTO;
    }

    private Machine mapToEntity(final MachineDTO machineDTO, final Machine machine) {
        machine.setModel(machineDTO.getModel());
        machine.setDescription(machineDTO.getDescription());
        machine.setStock(machineDTO.getStock());
        machine.setMainImageUrl(machineDTO.getMainImageUrl());
        machine.setFinalPrice(machineDTO.getFinalPrice());
        machine.setInitialPrice(machineDTO.getInitialPrice());
        final Brand brand = machineDTO.getBrand() == null ? null : brandRepository.findById(machineDTO.getBrand())
                .orElseThrow(() -> new NotFoundException("brand not found"));
        machine.setBrand(brand);
        final List<Tag> tags = iterableToList(tagRepository.findAllById(
                machineDTO.getTags() == null ? Collections.emptyList() : machineDTO.getTags()));
        if (tags.size() != (machineDTO.getTags() == null ? 0 : machineDTO.getTags().size())) {
            throw new NotFoundException("one of tags not found");
        }
        machine.setTags(new HashSet<>(tags));
        final Category category = machineDTO.getCategory() == null ? null : categoryRepository.findById(machineDTO.getCategory())
                .orElseThrow(() -> new NotFoundException("category not found"));
        machine.setCategory(category);
        return machine;
    }

    private <T> List<T> iterableToList(final Iterable<T> iterable) {
        final List<T> list = new ArrayList<T>();
        iterable.forEach(item -> list.add(item));
        return list;
    }

}
