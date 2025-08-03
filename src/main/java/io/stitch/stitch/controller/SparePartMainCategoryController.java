package io.stitch.stitch.controller;

import io.stitch.stitch.dto.SparePartMainCategoryDTO;
import io.stitch.stitch.service.SparePartMainCategoryService;
import io.stitch.stitch.util.ReferencedException;
import io.stitch.stitch.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/spare-parts/main/category", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SparePartMainCategoryController {

    private final SparePartMainCategoryService sparePartMainCategoryService;

    public SparePartMainCategoryController(SparePartMainCategoryService sparePartMainCategoryService) {
        this.sparePartMainCategoryService = sparePartMainCategoryService;
    }


    @GetMapping
    public ResponseEntity<List<SparePartMainCategoryDTO>> getAllSparePartCategory() {
        return ResponseEntity.ok(sparePartMainCategoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SparePartMainCategoryDTO> getSparePartCategory(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(sparePartMainCategoryService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSparePartCategory(@RequestBody @Valid final SparePartMainCategoryDTO SparePartMainCategoryDTO) {
        final Long createdId = sparePartMainCategoryService.create(SparePartMainCategoryDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSparePartCategory(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SparePartMainCategoryDTO SparePartMainCategoryDTO) {
        sparePartMainCategoryService.update(id, SparePartMainCategoryDTO);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/homepage")
    public ResponseEntity<Long> addHomepageSparePartCategory(@RequestParam(name = "sparePartMainCategoryId") final Long sparePartMainCategoryId) {
        sparePartMainCategoryService.updateHomepageSparePartCategory(sparePartMainCategoryId);
        return ResponseEntity.ok(sparePartMainCategoryId);
    }

    @GetMapping("/homepage")
    public ResponseEntity<List<SparePartMainCategoryDTO>> findAllHomepageSparePartCategory() {
        return ResponseEntity.ok(sparePartMainCategoryService.findAllHomepageSparePartCategory());
    }


}
