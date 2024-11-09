package io.stitch.stitch.controller;

import io.stitch.stitch.dto.BannerDTO;
import io.stitch.stitch.dto.CreateBannerRequest;
import io.stitch.stitch.service.BannerService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/banners", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BannerController {
    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @GetMapping
    public ResponseEntity<List<BannerDTO>> getAllBanners() {
        return ResponseEntity.ok(bannerService.findAll());
    }

    @PostMapping()
    public ResponseEntity<Long> createBanner(@RequestBody @Valid final CreateBannerRequest createBannerRequest) throws IOException {
        return ResponseEntity.ok(bannerService.createNewBanner(createBannerRequest));
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBanner(@PathVariable(name = "id") Long id) throws IOException {

        bannerService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
