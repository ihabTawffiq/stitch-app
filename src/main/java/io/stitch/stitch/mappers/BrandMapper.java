package io.stitch.stitch.mappers;

import io.stitch.stitch.dto.BrandDTO;
import io.stitch.stitch.entity.Brand;

public class BrandMapper {
    public static BrandDTO mapToAppDTO(final Brand brand, final BrandDTO brandDTO) {
        brandDTO.setId(brand.getId());
        brandDTO.setName(brand.getName());
        brandDTO.setDescription(brand.getDescription());
        brandDTO.setLogoURL(brand.getLogoURL());
        return brandDTO;
    }

    public static Brand mapToEntity(final BrandDTO brandDTO, final Brand brand) {
        brand.setName(brandDTO.getName());
        brand.setDescription(brandDTO.getDescription());
        brand.setLogoURL(brandDTO.getLogoURL());
        return brand;
    }
}
