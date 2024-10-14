package io.stitch.stitch.service;

import io.stitch.stitch.domain.Tag;
import io.stitch.stitch.model.TagDTO;
import io.stitch.stitch.repos.MachineRepository;
import io.stitch.stitch.repos.TagRepository;
import io.stitch.stitch.util.NotFoundException;
import java.util.List;
import java.util.Objects;

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

    public List<TagDTO> findAll() {
        final List<Tag> tags = tagRepository.findAll(Sort.by("id"));
        return tags.stream()
                .map(tag -> mapToDTO(tag, new TagDTO()))
                .toList();
    }

    public TagDTO get(final Long id) {
        return tagRepository.findById(id)
                .map(tag -> mapToDTO(tag, new TagDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TagDTO tagDTO) {
        final Tag tag = new Tag();
        tag.setId(primarySequenceService.getNextValue());
        mapToEntity(tagDTO, tag);
        return tagRepository.save(tag).getId();
    }

    public void update(final Long id, final TagDTO tagDTO) {
        final Tag tag = tagRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(tagDTO, tag);
        tagRepository.save(tag);
    }

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
        return tagDTO;
    }

    private Tag mapToEntity(final TagDTO tagDTO, final Tag tag) {
        tag.setName(tagDTO.getName());
        tag.setDescription(tagDTO.getDescription());
        return tag;
    }

}
