package io.stitch.stitch.config;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    private String cloudName = "daotubczw";
    private String apiKey = "399688121232161";
    private String apiSecret = "ck4EXpjQZEeXW9wCvQfsV0nVvQc";
    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name",cloudName,
                "api_key",apiKey,
                "api_secret",apiSecret
        ));
    }
}
