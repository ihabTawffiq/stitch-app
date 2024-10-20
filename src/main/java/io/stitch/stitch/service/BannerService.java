package io.stitch.stitch.service;

import io.stitch.stitch.dto.BannerDTO;
import io.stitch.stitch.entity.Banner;
import io.stitch.stitch.entity.Brand;
import io.stitch.stitch.entity.Tag;
import io.stitch.stitch.repos.BannerRepository;
import io.stitch.stitch.util.CloudinaryManager;
import io.stitch.stitch.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BannerService {

    private final BannerRepository bannerRepository;
    private final CloudinaryManager cloudinaryManager;

    public BannerService(final BannerRepository bannerRepository, CloudinaryManager cloudinaryManager) {
        this.bannerRepository = bannerRepository;
        this.cloudinaryManager = cloudinaryManager;
    }

    public void update(final Long id, final BannerDTO bannerDTO) throws IOException {

        Banner banner = bannerRepository.findById(id).orElseThrow(NotFoundException::new);

        if (bannerDTO.getImageURL() == null || bannerDTO.getImageURL().isEmpty()) {
            bannerDTO.setImageURL(banner.getImageURL());

        }
        if (!banner.getImageURL().equals(bannerDTO.getImageURL())) {
            cloudinaryManager.deleteImageFromCloud(banner.getImageURL());

        }
        Banner updatedBanner = new Banner();

        mapToEntity(bannerDTO, updatedBanner);

        updatedBanner.setId(id);

        bannerRepository.save(updatedBanner);
    }

    public void delete(final Long id) throws IOException {

        Banner banner = bannerRepository.findById(id).orElseThrow(NotFoundException::new);

        cloudinaryManager.deleteImageFromCloud(banner.getImageURL());

        bannerRepository.deleteById(id);
    }

    // map void l2n  hat8ir al object and l2it enk mash bta5od al object back
    private void mapToEntity(final BannerDTO bannerDTO, final Banner banner) {
        banner.setId(bannerDTO.getId());
        banner.setDescription(bannerDTO.getDescription());
        banner.setBannerOrder(bannerDTO.getBannerOrder());
        banner.setImageURL(bannerDTO.getImageURL());

        if (bannerDTO.getTagDTO() != null) {
            Tag tag = new Tag();
            tag.setId(bannerDTO.getTagDTO().getId());
            tag.setName(bannerDTO.getTagDTO().getName());
            tag.setDescription(bannerDTO.getTagDTO().getDescription());
            banner.setTag(tag);
        } else {
            banner.setTag(null);
        }

        if (bannerDTO.getBrandDTO() != null) {
            Brand brand = new Brand();
            brand.setId(bannerDTO.getBrandDTO().getId());
            brand.setName(bannerDTO.getBrandDTO().getName());
            brand.setDescription(bannerDTO.getBrandDTO().getDescription());
            brand.setLogoURL(bannerDTO.getBrandDTO().getLogoURL());
            banner.setBrand(brand);
        } else {
            banner.setBrand(null);
        }

    }

}
