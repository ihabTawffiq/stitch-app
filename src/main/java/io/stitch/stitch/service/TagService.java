package io.stitch.stitch.service;

import io.stitch.stitch.entity.Tag;
import io.stitch.stitch.dto.TagDTO;
import io.stitch.stitch.repos.MachineRepository;
import io.stitch.stitch.repos.TagRepository;
import io.stitch.stitch.util.NotFoundException;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TagService {
    private final PrimarySequenceService primarySequenceService;
    private final TagRepository tagRepository;
    private final MachineRepository machineRepository;

    public TagService(PrimarySequenceService primarySequenceService, final TagRepository tagRepository,
                      final MachineRepository machineRepository) {
        this.primarySequenceService = primarySequenceService;
        this.tagRepository = tagRepository;
        this.machineRepository = machineRepository;
    }

    @Cacheable(value = "longCache", key = "'allTags'")
    public List<TagDTO> findAll() {
        final List<Tag> tags = tagRepository.findAll(Sort.by("id"));
        return tags.stream()
                .map(tag -> mapToDTO(tag, new TagDTO()))
                .toList();
    }
    @Cacheable(value = "longCache", key = "'displayedTags'")
    public List<TagDTO> findAllByDisplay(Boolean display) {
        final List<Tag> tags = tagRepository.findAllByDisplay(display);
        return tags.stream()
                .map(tag -> mapToDTO(tag, new TagDTO()))
                .toList();
    }

    public TagDTO get(final Long id) {
        return tagRepository.findById(id)
                .map(tag -> mapToDTO(tag, new TagDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @CacheEvict(value = "longCache", allEntries = true)
    public Long create(final TagDTO tagDTO) {
        final Tag tag = new Tag();
        tag.setId(primarySequenceService.getNextValue());
        mapToEntity(tagDTO, tag);
        return tagRepository.save(tag).getId();
    }

    @CacheEvict(value = "longCache", allEntries = true)
    public void update(final Long id, final TagDTO tagDTO) {
        final Tag tag = tagRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(tagDTO, tag);
        tagRepository.save(tag);
    }

    @CacheEvict(value = "longCache", allEntries = true)
    public void delete(final Long id) {
        final Tag tag = tagRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        machineRepository.findAllByTags(tag)
                .forEach(machine -> machine.getTags().remove(tag));
        tagRepository.delete(tag);
    }

    private TagDTO mapToDTO(final Tag tag, final TagDTO tagDTO) {
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        tagDTO.setDescription(tag.getDescription());
        tagDTO.setDisplay(tag.getDisplay());
        return tagDTO;
    }

    private Tag mapToEntity(final TagDTO tagDTO, final Tag tag) {
        tag.setName(tagDTO.getName());
        tag.setDescription(tagDTO.getDescription());
        tag.setDisplay(tagDTO.getDisplay());
        return tag;
    }

}
