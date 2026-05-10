package tn.jallouli.elite.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConf {
    @Value("${cloudinary.cloud_name}")
    private String cloudName;
    @Value("${cloudinary.api_key}")
    private String apiKey;
    @Value("${cloudinary.api_secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary(){
        Map<String,String> conf = new HashMap<>();
        conf.put("cloud_name",this.cloudName);
        conf.put("api_key",this.apiKey);
        conf.put("api_secret",this.apiSecret);
        return new Cloudinary(conf);
    }
}