package io.stitch.stitch.rest;

import io.stitch.stitch.model.MachineDTO;
import io.stitch.stitch.service.MachineService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/machines", produces = MediaType.APPLICATION_JSON_VALUE)
public class MachineResource {

    private final MachineService machineService;

    public MachineResource(final MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping
    public ResponseEntity<List<MachineDTO>> getAllMachines() {
        return ResponseEntity.ok(machineService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MachineDTO> getMachine(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(machineService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMachine(@RequestBody @Valid final MachineDTO machineDTO) {
        final Long createdId = machineService.create(machineDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateMachine(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final MachineDTO machineDTO) {
        machineService.update(id, machineDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMachine(@PathVariable(name = "id") final Long id) {
        machineService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
