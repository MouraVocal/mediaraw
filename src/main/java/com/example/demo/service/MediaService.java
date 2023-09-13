package com.example.demo.service;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.example.demo.entity.Media;
import com.example.demo.entity.ProjectProperties;
import com.example.demo.repository.MediaRepository;

@Service
public class MediaService {

  private static final Logger LOG = LoggerFactory.getLogger(MediaService.class);

  @Autowired
  private MediaRepository mediaRepository;

  @Autowired
  private ProjectProperties projectProperties;

  public List<Media> getAllMedia() {
    return mediaRepository.findAll();
  }

  public String getRawUrl(Integer cid, Integer projectExternalId, String mediaId, String filename, String qualifier) {

    return buildS3Url(cid, projectExternalId, mediaId, filename, qualifier);
  }

  private String buildS3Url(Integer cid, Integer projectExternalId, String mediaId, String filename, String qualifier) {
    return "http://" + projectProperties.getBucketName() + ".s3.amazonaws.com"
        + buildUrlPath(cid, projectExternalId, mediaId, filename, qualifier);
  }

  public String buildUrlPath(Integer clientId, Integer projectExternalId, String mediaId,
      String filename, String qualifier) {
    // create a "yyyy-mm-dd" string
    String date = String
        .format("%1$tY-%1$tm-%1$td", Calendar.getInstance());
    String externalId = projectExternalId != null ? projectExternalId.toString() : "undefined";

    return "/account/"
        + clientId + "/" + externalId + "/" + date + "/"
        + qualifier.toLowerCase() + "/" + mediaId + "/"
        + filename;
  }

  public Media createMedia() {
    Media media = new Media();
    media.setName("test");
    media.setPid("test");
    media.setCid("test");
    media.setRawUrl("test");
    mediaRepository.save(media);
    return media;
  }

  public List<Bucket> getAllBuckets(AWSCredentials credentials) {
    AmazonS3 s3 = new AmazonS3Client(credentials);
    List<Bucket> buckets = s3.listBuckets();
    LOG.info("Your {S3} buckets are:");
    for (Bucket b : buckets) {
      LOG.info(b.getName());
    }

    return buckets;
  }
}
