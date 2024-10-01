package com.bn.biteNest.recipe.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> testUpload(@RequestPart("imageFiles") List<MultipartFile> imageFiles) {
        return ResponseEntity.ok("Received " + imageFiles.size() + " files.");
    }
}

