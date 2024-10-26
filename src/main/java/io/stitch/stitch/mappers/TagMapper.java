package io.stitch.stitch.mappers;

import io.stitch.stitch.dto.TagDTO;
import io.stitch.stitch.entity.Tag;

public class TagMapper {
    public static TagDTO mapToAppDTO(final Tag tag, final TagDTO tagDTO) {
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        tagDTO.setDescription(tag.getDescription());
        return tagDTO;
    }

    public static Tag mapToEntity(final TagDTO tagDTO, final Tag tag) {
        tag.setId(tag.getId());
        tag.setName(tagDTO.getName());
        tag.setDescription(tagDTO.getDescription());
        return tag;
    }
}
