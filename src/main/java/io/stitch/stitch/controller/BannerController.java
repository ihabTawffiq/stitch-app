package io.stitch.stitch.controller;

import io.stitch.stitch.dto.CreateBannerRequest;
import io.stitch.stitch.service.BannerService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/banners", produces = MediaType.APPLICATION_JSON_VALUE)
public class BannerController {
    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @PostMapping()
    public ResponseEntity<Long> createBanner(@RequestBody @Valid final CreateBannerRequest createBannerRequest) throws IOException {
        return ResponseEntity.ok(bannerService.createNewBanner(createBannerRequest));
    }
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBanner(@PathVariable (name = "id") Long id) throws IOException {

        bannerService.delete(id);

        return ResponseEntity.noContent().build();//enta bat3ml keda dh eh ma3rfsh
    }
}
