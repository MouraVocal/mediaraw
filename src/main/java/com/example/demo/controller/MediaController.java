package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.model.Bucket;
import com.example.demo.entity.Media;
import com.example.demo.entity.ProjectProperties;
import com.example.demo.service.MediaService;

@RestController
@RequestMapping("/api")
public class MediaController {

  @Autowired
  private MediaService mediaService;

  @Autowired
  private ProjectProperties projectProperties;

  @GetMapping("/medias/raw")
  String getMedia(@RequestParam(required = true, name = "mediaId") Integer mediaId) {

    return mediaService.getRawUrl(mediaId);
  }

  @GetMapping("/buckets")
  List<Bucket> getAllBuckets() {

    AWSCredentials credentials = new BasicAWSCredentials(projectProperties.getAccessKey(),
        projectProperties.getSecretKey());

    return mediaService.getAllBuckets(credentials);
  }

  @GetMapping("/medias")
  List<Media> getAllMedia() {
    return mediaService.getAllMedia();
  }

  @PostMapping("/medias")
  Media createMedia() {
    return mediaService.createMedia();
  }
}
