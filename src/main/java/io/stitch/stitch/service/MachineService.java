package io.stitch.stitch.service;

import io.stitch.stitch.domain.Brand;
import io.stitch.stitch.domain.Machine;
import io.stitch.stitch.domain.Model;
import io.stitch.stitch.domain.Tag;
import io.stitch.stitch.model.MachineDTO;
import io.stitch.stitch.repos.BrandRepository;
import io.stitch.stitch.repos.MachineRepository;
import io.stitch.stitch.repos.ModelRepository;
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
    private final ModelRepository modelRepository;
    private final TagRepository tagRepository;

    public MachineService(final MachineRepository machineRepository,
            final BrandRepository brandRepository, final ModelRepository modelRepository,
            final TagRepository tagRepository) {
        this.machineRepository = machineRepository;
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.tagRepository = tagRepository;
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
        machineDTO.setTitle(machine.getTitle());
        machineDTO.setDescription(machine.getDescription());
        machineDTO.setStock(machine.getStock());
        machineDTO.setMainImageUrl(machine.getMainImageUrl());
        machineDTO.setFinalPrice(machine.getFinalPrice());
        machineDTO.setInitialPrice(machine.getInitialPrice());
        machineDTO.setBrand(machine.getBrand() == null ? null : machine.getBrand().getId());
        machineDTO.setModel(machine.getModel() == null ? null : machine.getModel().getId());
        machineDTO.setTags(machine.getTags().stream()
                .map(tag -> tag.getId())
                .toList());
        return machineDTO;
    }

    private Machine mapToEntity(final MachineDTO machineDTO, final Machine machine) {
        machine.setTitle(machineDTO.getTitle());
        machine.setDescription(machineDTO.getDescription());
        machine.setStock(machineDTO.getStock());
        machine.setMainImageUrl(machineDTO.getMainImageUrl());
        machine.setFinalPrice(machineDTO.getFinalPrice());
        machine.setInitialPrice(machineDTO.getInitialPrice());
        final Brand brand = machineDTO.getBrand() == null ? null : brandRepository.findById(machineDTO.getBrand())
                .orElseThrow(() -> new NotFoundException("brand not found"));
        machine.setBrand(brand);
        final Model model = machineDTO.getModel() == null ? null : modelRepository.findById(machineDTO.getModel())
                .orElseThrow(() -> new NotFoundException("model not found"));
        machine.setModel(model);
        final List<Tag> tags = iterableToList(tagRepository.findAllById(
                machineDTO.getTags() == null ? Collections.emptyList() : machineDTO.getTags()));
        if (tags.size() != (machineDTO.getTags() == null ? 0 : machineDTO.getTags().size())) {
            throw new NotFoundException("one of tags not found");
        }
        machine.setTags(new HashSet<>(tags));
        return machine;
    }

    private <T> List<T> iterableToList(final Iterable<T> iterable) {
        final List<T> list = new ArrayList<T>();
        iterable.forEach(item -> list.add(item));
        return list;
    }

}
