package io.stitch.stitch.controller;

import io.stitch.stitch.dto.requets.SpearPartRequest;
import io.stitch.stitch.dto.response.SpearPartResponse;
import io.stitch.stitch.service.SpearPartService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/spear-parts", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SpearPartController {
    private final SpearPartService spearPartService;

    public SpearPartController(SpearPartService spearPartService) {
        this.spearPartService = spearPartService;
    }


    @GetMapping("/get-all")
    public ResponseEntity<List<SpearPartResponse>> getAllSpearParts(@RequestParam(defaultValue = "0") final Integer offset, @RequestParam(defaultValue = "3") final Integer limit) {
        return ResponseEntity.ok(spearPartService.getAllSpearParts(offset, limit));
    }

    @PostMapping()
    public ResponseEntity<Long> createSpearPart(@RequestBody @Valid final SpearPartRequest spearPartRequest) {
        return ResponseEntity.ok(spearPartService.create(spearPartRequest));
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<SpearPartResponse> getSpearPartById(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(spearPartService.getSpearPartById(id));
    }

    @DeleteMapping("/delete")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMachine(@RequestParam(name = "id") Long id) {
        spearPartService.deleteSpearPart(id);
        return ResponseEntity.noContent().build();
    }
}
