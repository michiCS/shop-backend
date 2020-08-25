package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.utils.Utils;



@SpringBootApplication
public class BackendApplication {


    public static void main(String[] args) throws IOException {        
        ImageKit imageKit = ImageKit.getInstance();
        Configuration config = Utils.getSystemConfig(BackendApplication.class);
        imageKit.setConfig(config);
        SpringApplication.run(BackendApplication.class, args);
    }

}
