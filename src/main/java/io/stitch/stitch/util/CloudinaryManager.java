package io.stitch.stitch.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.stitch.stitch.entity.Image;
import io.stitch.stitch.repos.ImageRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class CloudinaryManager {
    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;

    public CloudinaryManager(Cloudinary cloudinary, ImageRepository imageRepository) {
        this.cloudinary = cloudinary;
        this.imageRepository = imageRepository;
    }

    public String uploadImageOnCloud(MultipartFile imageFile) throws IOException {
        var map = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.asMap("public_id", UUID.randomUUID().toString()));

        if (Objects.nonNull(map)) {
            String imageURL = map.get("url").toString();
            String publicId = map.get("public_id").toString();
            Image image = new Image();
            image.setUrl(imageURL);
            image.setId(publicId);
            imageRepository.save(image);

            return imageURL;
        }
        return "failure";
    }

    public void deleteImageFromCloud(String imageURl) throws IOException {
        Optional<Image> imageOptional = imageRepository.findByUrl(imageURl);
        if (imageOptional.isPresent()) {
            Image image = imageOptional.get();
            cloudinary.uploader().destroy(image.getId(), ObjectUtils.asMap("public_id", image.getUrl()));
            imageRepository.delete(image);
        }
    }

}
