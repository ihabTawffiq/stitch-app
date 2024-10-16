package io.stitch.stitch.mappers;

import io.stitch.stitch.dto.CategoryDTO;
import io.stitch.stitch.entity.Category;

public class CategoryMapper {

    public static CategoryDTO mapToAppDTO(final Category category, final CategoryDTO categoryDTO) {
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    public static Category mapToEntity(final CategoryDTO categoryDTO, final Category category) {
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }
}
