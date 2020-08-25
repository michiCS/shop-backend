/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.services;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author wagne
 */

@Service
public class ImageKitService {

    public String uploadImage(String base64, String username, String filename) {
        String imagePath = "";
        FileCreateRequest fileCreateRequest = new FileCreateRequest(base64, username + "_" + filename);
        Result res = ImageKit.getInstance().upload(fileCreateRequest);
        if (!Objects.isNull(res.getUrl())) {
            imagePath = res.getUrl();
        } else {
            imagePath = "https://ik.imagekit.io/uizysu7r0t/no-image-available-icon-flat-vector-no-image-available-icon-flat-vector-illustration-132482953_bBkQPgLnPiV.jpg";
        }
        return imagePath;
    }
}
