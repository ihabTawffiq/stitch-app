package io.stitch.stitch.controller;

import io.stitch.stitch.dto.BannerDTO;
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

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateBanner(@PathVariable (name = "id") Long id,
                                             @RequestBody @Valid final BannerDTO bannerDTO) throws IOException {
        bannerService.update(id,bannerDTO);

        return ResponseEntity.ok(bannerDTO.getId());
    }
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBanner(@PathVariable (name = "id") Long id) throws IOException {

        bannerService.delete(id);

        return ResponseEntity.noContent().build();//enta bat3ml keda dh eh ma3rfsh
    }
}
