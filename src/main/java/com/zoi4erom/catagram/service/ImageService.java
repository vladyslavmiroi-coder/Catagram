package com.zoi4erom.catagram.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

        private final Cloudinary cloudinary;

        public String uploadImage(MultipartFile image){
                try {
                        Map<?, ?> uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
                        return (String) uploadResult.get("url");
                } catch (IOException e) {
                        throw new IllegalStateException("Failed to upload image", e);
                }
        }

        public void deleteImage(String avatarUrl) {
                if (avatarUrl == null || avatarUrl.isBlank()) return;

                String publicId = extractPublicId(avatarUrl);

                try {
                        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                } catch (IOException e) {
                        throw new IllegalStateException("Failed to delete image", e);
                }
        }

        private String extractPublicId(String url) {
                String filename = url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf('.'));
                return filename.split("\\.")[0];
        }
}