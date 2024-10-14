package io.stitch.stitch.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
public class CloudinaryManager {
    private final Cloudinary cloudinary;

    public CloudinaryManager(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }
    public String uploadImageOnCloud(MultipartFile imageFile) throws IOException {
        String imageURL = cloudinary.uploader()
                .upload(imageFile.getBytes(), ObjectUtils.asMap("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
        if (!imageURL.isBlank()) {
            return imageURL;
        }
        return "failure";
    }

}
