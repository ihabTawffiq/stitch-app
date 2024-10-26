package io.stitch.stitch.service;

import io.stitch.stitch.dto.BrandDTO;
import io.stitch.stitch.dto.CategoryDTO;
import io.stitch.stitch.dto.MachineDTO;
import io.stitch.stitch.dto.TagDTO;
import io.stitch.stitch.dto.app.AppMachineDTO;
import io.stitch.stitch.entity.Brand;
import io.stitch.stitch.entity.Category;
import io.stitch.stitch.entity.Machine;
import io.stitch.stitch.entity.Tag;
import io.stitch.stitch.mappers.BrandMapper;
import io.stitch.stitch.mappers.CategoryMapper;
import io.stitch.stitch.mappers.TagMapper;
import io.stitch.stitch.repos.BrandRepository;
import io.stitch.stitch.repos.CategoryRepository;
import io.stitch.stitch.repos.MachineRepository;
import io.stitch.stitch.repos.TagRepository;
import io.stitch.stitch.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MachineService {

    private final MachineRepository machineRepository;
    private final BrandRepository brandRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final PrimarySequenceService primarySequenceService;

    public MachineService(final MachineRepository machineRepository, final BrandRepository brandRepository, final TagRepository tagRepository, final CategoryRepository categoryRepository, PrimarySequenceService primarySequenceService) {
        this.machineRepository = machineRepository;
        this.brandRepository = brandRepository;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
        this.primarySequenceService = primarySequenceService;
    }

    static Map<String, Object> convertToResponse(final Page<Machine> pagePersons) {
        Map<String, Object> response = new HashMap<>();
        response.put("rice-productions", pagePersons.getContent());
        response.put("current-page", pagePersons.getNumber());
        response.put("total-items", pagePersons.getTotalElements());
        response.put("total-pages", pagePersons.getTotalPages());
        return response;
    }

    public List<AppMachineDTO> findAll(final Integer offset, final Integer limit) {
        List<Machine> machineList;
        if (offset == -1) {
            machineList = machineRepository.findAll();
        } else {
            machineList = machineRepository.findAll(PageRequest.of(offset, limit)).getContent();
        }
        return machineList.stream().map(machine -> mapToAppDTO(machine, new AppMachineDTO())).toList();
    }

    public MachineDTO get(final Long id) {
        return machineRepository.findById(id).map(machine -> mapToDTO(machine, new MachineDTO())).orElseThrow(NotFoundException::new);
    }

    public Long create(final MachineDTO machineDTO) {
        final Machine machine = new Machine();
        machine.setId(primarySequenceService.getNextValue());
        mapToEntity(machineDTO, machine);
        return machineRepository.save(machine).getId();
    }

    public void update(final Long id, final MachineDTO machineDTO) {
        final Machine machine = machineRepository.findById(id).orElseThrow(NotFoundException::new);
        mapToEntity(machineDTO, machine);
        machineRepository.save(machine);
    }

    public void delete(final Long id) {
        machineRepository.deleteById(id);
    }

    public List<AppMachineDTO> getMachinesByTag(final Long[] tagIds, final Integer offset, final Integer limit) {
        List<Machine> machineList;
        List<Tag> tagList = tagRepository.findAllByIdIn(Arrays.stream(tagIds).toList());
        List<AppMachineDTO> machineDTOList = new ArrayList<>();
        if (offset == -1) {
            machineList = machineRepository.findAllByTagsIn(tagList);

        } else {
            machineList = machineRepository.findAllByTagsIn(tagList, PageRequest.of(offset, limit)).getContent();
        }
        for (Machine machine : machineList) {
            machineDTOList.add(mapToAppDTO(machine, new AppMachineDTO()));
        }
        return machineDTOList;
    }

    public List<AppMachineDTO> getMachinesByCategory(final Long categoryId, final Integer offset, final Integer limit) {

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();

            List<Machine> machineList;
            if (offset == -1) {
                machineList = machineRepository.findAllByCategory(category);
            } else {
                machineList = machineRepository.findAllByCategory(category, PageRequest.of(offset, limit)).getContent();
            }
            List<AppMachineDTO> machineDTOList = new ArrayList<>();
            for (Machine machine : machineList) {
                machineDTOList.add(mapToAppDTO(machine, new AppMachineDTO()));
            }
            return machineDTOList;
        }
        return null;
    }

    public List<AppMachineDTO> getMachinesByBrand(final Long brandId, final Integer offset, final Integer limit) {
        Optional<Brand> brandOptional = brandRepository.findById(brandId);
        if (brandOptional.isPresent()) {
            Brand brand = brandOptional.get();
            List<Machine> machineList;
            if (offset == -1) {
                machineList = machineRepository.findAllByBrand(brand);
            } else {
                machineList = machineRepository.findAllByBrand(brand, PageRequest.of(offset, limit)).getContent();
            }
            List<AppMachineDTO> machineDTOList = new ArrayList<>();
            for (Machine machine : machineList) {
                machineDTOList.add(mapToAppDTO(machine, new AppMachineDTO()));
            }
            return machineDTOList;
        }
        return null;

    }

    public List<AppMachineDTO> filterMachines(List<Long> tagIds, List<Long> brandIds, List<Long> categoryIds) {
        List<Tag> tagList;
        List<Brand> brandList;
        List<Category> categoryList;
        if (Objects.nonNull(tagIds) && !tagIds.isEmpty()) {
            tagList = tagRepository.findAllById(tagIds);
        } else {
            tagList = tagRepository.findAll();
        }

        if (Objects.nonNull(brandIds) && !brandIds.isEmpty()) {
            brandList = brandRepository.findAllById(brandIds);
        } else {
            brandList = brandRepository.findAll();
        }

        if (Objects.nonNull(categoryIds) && !categoryIds.isEmpty()) {
            categoryList = categoryRepository.findAllById(categoryIds);
        } else {
            categoryList = categoryRepository.findAll();
        }
        
        List<Machine> machineList = machineRepository.findAllByBrandInAndCategoryInAndTagsIn(brandList, categoryList, tagList);
        List<AppMachineDTO> machineDTOList = new ArrayList<>();
        for (Machine machine : machineList) {
            machineDTOList.add(mapToAppDTO(machine, new AppMachineDTO()));
        }
        return machineDTOList;
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
        machineDTO.setTags(machine.getTags().stream().map(Tag::getId).toList());
        machineDTO.setCategory(machine.getCategory() == null ? null : machine.getCategory().getId());
        return machineDTO;
    }

    private void mapToEntity(final MachineDTO machineDTO, final Machine machine) {
        machine.setModel(machineDTO.getModel());
        machine.setDescription(machineDTO.getDescription());
        machine.setStock(machineDTO.getStock());
        machine.setMainImageUrl(machineDTO.getMainImageUrl());
        machine.setFinalPrice(machineDTO.getFinalPrice());
        machine.setInitialPrice(machineDTO.getInitialPrice());
        final Brand brand = machineDTO.getBrand() == null ? null : brandRepository.findById(machineDTO.getBrand()).orElseThrow(() -> new NotFoundException("brand not found"));
        machine.setBrand(brand);
        final List<Tag> tags = iterableToList(tagRepository.findAllById(machineDTO.getTags() == null ? Collections.emptyList() : machineDTO.getTags()));
        if (tags.size() != (machineDTO.getTags() == null ? 0 : machineDTO.getTags().size())) {
            throw new NotFoundException("one of tags not found");
        }
        machine.setTags(new HashSet<>(tags));
        final Category category = machineDTO.getCategory() == null ? null : categoryRepository.findById(machineDTO.getCategory()).orElseThrow(() -> new NotFoundException("category not found"));
        machine.setCategory(category);
    }

    private AppMachineDTO mapToAppDTO(final Machine machine, final AppMachineDTO machineDTO) {
        machineDTO.setId(machine.getId());
        machineDTO.setModel(machine.getModel());
        machineDTO.setDescription(machine.getDescription());
        machineDTO.setStock(machine.getStock());
        machineDTO.setMainImageUrl(machine.getMainImageUrl());
        machineDTO.setFinalPrice(machine.getFinalPrice());
        machineDTO.setInitialPrice(machine.getInitialPrice());
        machineDTO.setBrand(machine.getBrand() == null ? null : BrandMapper.mapToAppDTO(machine.getBrand(), new BrandDTO()));
        machineDTO.setTags(machine.getTags().stream().map(tag -> TagMapper.mapToAppDTO(tag, new TagDTO())).toList());
        machineDTO.setCategory(machine.getCategory() == null ? null : CategoryMapper.mapToAppDTO(machine.getCategory(), new CategoryDTO()));
        return machineDTO;
    }

    private <T> List<T> iterableToList(final Iterable<T> iterable) {
        final List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }


}
