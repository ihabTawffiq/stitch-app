package io.stitch.stitch.controller;

import io.stitch.stitch.dto.TagDTO;
import io.stitch.stitch.service.TagService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/tags", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TagController {

    private final TagService tagService;

    public TagController(final TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagDTO>> getAllTags() {
        return ResponseEntity.ok(tagService.findAll());
    }

    @GetMapping("/display")
    public ResponseEntity<List<TagDTO>> getAllTagsForDisplay(@RequestParam(name = "display") Boolean display) {
        return ResponseEntity.ok(tagService.findAllByDisplay(display));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTag(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(tagService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTag(@RequestBody @Valid final TagDTO tagDTO) {
        final Long createdId = tagService.create(tagDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateTag(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final TagDTO tagDTO) {
        tagService.update(id, tagDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTag(@PathVariable(name = "id") final Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
