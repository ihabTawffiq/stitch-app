package io.stitch.stitch.rest;

import io.stitch.stitch.util.CloudinaryManager;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/images")
public class UploadImage {
    private final CloudinaryManager cloudinaryManager;

    public UploadImage(CloudinaryManager cloudinaryManager) {
        this.cloudinaryManager = cloudinaryManager;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@Valid @RequestBody MultipartFile imageFile) throws IOException {
        String response = cloudinaryManager.uploadImageOnCloud(imageFile);
        return ResponseEntity.ok(response);
    }
}
