package appPackage.model.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CloudinaryConfig {

    private Cloudinary cloudinary;

    @Autowired
    public CloudinaryConfig(@Value("${cloudinary.cloudname}")String cloudname,
                            @Value("${cloudinary.apikey}") String apikey,
                            @Value("${cloudinary.apisecret}") String apisecret) {
        cloudinary = Singleton.getCloudinary();
        cloudinary.config.cloudName = cloudname;
        cloudinary.config.apiKey = apikey;
        cloudinary.config.apiSecret = apisecret;
    }

    public Map upload(Object file, Map options){
        try {
            return cloudinary.uploader().upload(file,options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
