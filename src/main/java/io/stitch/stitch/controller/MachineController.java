package io.stitch.stitch.controller;

import io.stitch.stitch.dto.MachineDTO;
import io.stitch.stitch.dto.app.AppMachineDTO;
import io.stitch.stitch.service.MachineService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/machines", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MachineController {

    private final MachineService machineService;

    public MachineController(final MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping
    public ResponseEntity<List<AppMachineDTO>> getAllMachines(@RequestParam(defaultValue = "0") final Integer offset,
                                                              @RequestParam(defaultValue = "3") final Integer limit) {
        return ResponseEntity.ok(machineService.findAll(offset,limit));
    }

    @GetMapping("/app/by-tag-id")
    public ResponseEntity<List<AppMachineDTO>> getMachineByTag(@RequestParam(name = "tagId") Long[] ids,
                                                               @RequestParam(defaultValue = "0") final Integer offset,
                                                               @RequestParam(defaultValue = "3") final Integer limit) {
        return ResponseEntity.ok(machineService.getMachinesByTag(ids,offset,limit));
    }


    @GetMapping("/app/by-category-id")
    public ResponseEntity<List<AppMachineDTO>> getMachineByCategory(@RequestParam(name = "categoryId") Long id,
                                                                    @RequestParam(defaultValue = "0") final Integer offset,
                                                                    @RequestParam(defaultValue = "3") final Integer limit) {
        return ResponseEntity.ok(machineService.getMachinesByCategory(id,offset,limit));
    }

    @GetMapping("/app/by-brand-id")
    public ResponseEntity<List<AppMachineDTO>> getMachineByBrand(@RequestParam(name = "brandId") Long id,
                                                                 @RequestParam(defaultValue = "0") final Integer offset,
                                                                 @RequestParam(defaultValue = "3") final Integer limit) {
        return ResponseEntity.ok(machineService.getMachinesByBrand(id,offset,limit));
    }

    @GetMapping("/app/by-model")
    public ResponseEntity<List<AppMachineDTO>> getMachineByBrand(@RequestParam(name = "model") String model) {
        return ResponseEntity.ok(machineService.getMachinesByModel(model));
    }


    @GetMapping("/{id}")
    public ResponseEntity<AppMachineDTO> getMachine(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(machineService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMachine(@RequestBody @Valid final MachineDTO machineDTO) {
        final Long createdId = machineService.create(machineDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateMachine(@PathVariable(name = "id") final Long id, @RequestBody @Valid final MachineDTO machineDTO) {
        machineService.update(id, machineDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMachine(@PathVariable(name = "id") final Long id) {
        machineService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/app/filter")
    public ResponseEntity<List<AppMachineDTO>> filterMachine(@RequestParam(required = false) List<Long> tagIds,@RequestParam(required = false) List<Long> categoryIds,@RequestParam(required = false) List<Long> brandIds) {
       return ResponseEntity.ok( machineService.filterMachines(tagIds,brandIds,categoryIds));
    }

}
