package io.stitch.stitch.service;

import io.stitch.stitch.dto.requets.SpearPartRequest;
import io.stitch.stitch.dto.response.SpearPartResponse;
import io.stitch.stitch.entity.SpearPart;
import io.stitch.stitch.repos.SpearPartRepository;
import io.stitch.stitch.util.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SpearPartService {


    private final PrimarySequenceService primarySequenceService;
    private final SpearPartRepository spearPartRepository;

    public SpearPartService(PrimarySequenceService primarySequenceService, SpearPartRepository spearPartRepository) {
        this.primarySequenceService = primarySequenceService;
        this.spearPartRepository = spearPartRepository;
    }

    public Long create(final SpearPartRequest spearPartRequest) {
        final SpearPart spearPart = new SpearPart();
        mapRequestToEntity(spearPartRequest, spearPart);
        return spearPartRepository.save(spearPart).getId();
    }

    public Long update(final Long id, final SpearPartRequest spearPartRequest) {
        final SpearPart spearPart = spearPartRepository.findById(id).orElseThrow(NotFoundException::new);
        mapRequestToEntity(spearPartRequest, spearPart);
        return spearPartRepository.save(spearPart).getId();
    }

    public List<SpearPartResponse> getAllSpearParts(final Integer offset, final Integer limit) {

        final List<SpearPartResponse> spearPartResponseList = new ArrayList<>();
        List<SpearPart> spearParts = new ArrayList<>();
        if (limit == -1) {
            spearParts = spearPartRepository.findAll(Sort.by("id"));
        } else {
            spearParts = spearPartRepository.findAll(PageRequest.of(offset, limit)).getContent();
        }
        spearParts.forEach(spearPart -> {
            final SpearPartResponse spearPartResponse = new SpearPartResponse();
            mapEntityToResponse(spearPart, spearPartResponse);
            spearPartResponseList.add(spearPartResponse);
        });
        return spearPartResponseList;
    }

    public SpearPartResponse getSpearPartById(final Long id) {
        final SpearPartResponse spearPartResponse = new SpearPartResponse();
        SpearPart spearPart = spearPartRepository.findById(id).orElseThrow(NotFoundException::new);
        mapEntityToResponse(spearPart, spearPartResponse);
        return spearPartResponse;
    }
    public void deleteSpearPart(final Long id) {
        spearPartRepository.deleteById(id);
    }

    private void mapRequestToEntity(final SpearPartRequest spearPartRequest, final SpearPart spearPart) {
        spearPart.setId(primarySequenceService.getNextValue());
        spearPart.setName(spearPartRequest.getName());
        spearPart.setPrice(spearPartRequest.getPrice());
        spearPart.setImageURL(spearPartRequest.getImageURL());
    }

    private void mapEntityToResponse(final SpearPart spearPart, final SpearPartResponse spearPartResponse) {
        spearPartResponse.setId(spearPart.getId());
        spearPartResponse.setName(spearPart.getName());
        spearPartResponse.setPrice(spearPart.getPrice());
        spearPartResponse.setImageURL(spearPart.getImageURL());
    }

}