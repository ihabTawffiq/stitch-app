package io.stitch.stitch.service;

import io.stitch.stitch.dto.BannerDTO;
import io.stitch.stitch.dto.BrandDTO;
import io.stitch.stitch.dto.CreateBannerRequest;
import io.stitch.stitch.dto.TagDTO;
import io.stitch.stitch.entity.Banner;
import io.stitch.stitch.entity.Brand;
import io.stitch.stitch.mappers.BrandMapper;
import io.stitch.stitch.mappers.TagMapper;
import io.stitch.stitch.repos.BannerRepository;
import io.stitch.stitch.repos.BrandRepository;
import io.stitch.stitch.repos.TagRepository;
import io.stitch.stitch.util.CloudinaryManager;
import io.stitch.stitch.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
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

    public List<BannerDTO> findAll() {
        final List<Banner> banners = bannerRepository.findAll(Sort.by("id"));
        return banners.stream().map(banner -> (BannerDTO) mapToDTO(banner, new BannerDTO())).toList();
    }

    private Object mapToDTO(Banner banner, BannerDTO bannerDTO) {
        bannerDTO.setImageURL(banner.getImageURL());
        bannerDTO.setDescription(banner.getDescription());
        bannerDTO.setBannerOrder(banner.getBannerOrder());
        if (Objects.nonNull(banner.getTag())){
            TagDTO tagDTO = new TagDTO();
            TagMapper.mapToAppDTO(banner.getTag(),tagDTO);
            bannerDTO.setTagDTO(tagDTO);
        }
        if (Objects.nonNull(banner.getBrand())){
            BrandDTO brandDTO = new BrandDTO();
            BrandMapper.mapToAppDTO(banner.getBrand(),brandDTO);
            bannerDTO.setBrandDTO(brandDTO);
        }
        return bannerDTO;
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
