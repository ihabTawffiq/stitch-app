package io.stitch.stitch.service;

import io.stitch.stitch.dto.CreateBannerRequest;
import io.stitch.stitch.entity.Banner;
import io.stitch.stitch.repos.BannerRepository;
import io.stitch.stitch.repos.BrandRepository;
import io.stitch.stitch.repos.TagRepository;
import io.stitch.stitch.util.CloudinaryManager;
import io.stitch.stitch.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class BannerService {

    private final BannerRepository bannerRepository;
    private final CloudinaryManager cloudinaryManager;
    private final PrimarySequenceService primarySequenceService;
    private final TagRepository tagRepository;
    private final BrandRepository brandRepository;

    public BannerService(final BannerRepository bannerRepository, CloudinaryManager cloudinaryManager, PrimarySequenceService primarySequenceService, TagRepository tagRepository, BrandRepository brandRepository) {
        this.bannerRepository = bannerRepository;
        this.cloudinaryManager = cloudinaryManager;
        this.primarySequenceService = primarySequenceService;
        this.tagRepository = tagRepository;
        this.brandRepository = brandRepository;
    }

    public Long createNewBanner(final CreateBannerRequest createBannerRequest) throws IOException {
        Banner banner = new Banner();
        mapRequestToEntity(createBannerRequest, banner);
        return bannerRepository.save(banner).getId();
    }

    public void delete(final Long id) throws IOException {
        Banner banner = bannerRepository.findById(id).orElseThrow(NotFoundException::new);
        cloudinaryManager.deleteImageFromCloud(banner.getImageURL());
        bannerRepository.deleteById(id);
    }

    private void mapRequestToEntity(final CreateBannerRequest createBannerRequest, final Banner banner) {
        banner.setId(primarySequenceService.getNextValue());
        banner.setDescription(createBannerRequest.getDescription());
        banner.setBannerOrder(createBannerRequest.getBannerOrder());
        banner.setImageURL(createBannerRequest.getImageURL());
        if (Objects.nonNull(createBannerRequest.getId())) {
            banner.setTag(tagRepository.findById(createBannerRequest.getId()).orElseThrow(NotFoundException::new));
        }
        if (Objects.nonNull(createBannerRequest.getBrandId())) {
            banner.setBrand(brandRepository.findById(createBannerRequest.getBrandId()).orElseThrow(NotFoundException::new));
        } else {
            banner.setBrand(null);
        }

    }

}
