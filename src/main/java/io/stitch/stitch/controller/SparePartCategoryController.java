package io.stitch.stitch.controller;

import io.stitch.stitch.dto.SparePartCategoryDTO;
import io.stitch.stitch.service.SparePartCategoryService;
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
@RequestMapping(value = "/api/spare-parts/category", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SparePartCategoryController {

    private final SparePartCategoryService sparePartCategoryService;

    public SparePartCategoryController(SparePartCategoryService sparePartCategoryService) {
        this.sparePartCategoryService = sparePartCategoryService;
    }


    @GetMapping
    public ResponseEntity<List<SparePartCategoryDTO>> getAllSparePartCategory() {
        return ResponseEntity.ok(sparePartCategoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SparePartCategoryDTO> getSparePartCategory(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(sparePartCategoryService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSparePartCategory(@RequestBody @Valid final SparePartCategoryDTO sparePartCategoryDTO) {
        final Long createdId = sparePartCategoryService.create(sparePartCategoryDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSparePartCategory(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SparePartCategoryDTO sparePartCategoryDTO) {
        sparePartCategoryService.update(id, sparePartCategoryDTO);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/homepage")
    public ResponseEntity<Long> addHomepageSparePartCategory(@RequestParam(name = "id") final Long sparePartCategoryId) {
        sparePartCategoryService.updateHomepageSparePartCategory(sparePartCategoryId);
        return ResponseEntity.ok(sparePartCategoryId);
    }

    @GetMapping("/homepage")
    public ResponseEntity<List<SparePartCategoryDTO>> addHomepageSparePartCategory() {
        return ResponseEntity.ok(sparePartCategoryService.findAllHomepageSparePartCategory());
    }


    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSparePartCategory(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = sparePartCategoryService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        sparePartCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
