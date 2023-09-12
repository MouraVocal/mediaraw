package com.example.demo.commons.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.example.demo.commons.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class S3Client {

  private static final Logger LOG = LoggerFactory.getLogger(S3Client.class);
  private static final int BUFFER_READ = 1024;

  public File getObject(AWSCredentials credentials, String bucketName, String key) throws IOException {
    S3Object s3Object = getS3Object(credentials, bucketName, key);
    if (s3Object == null)
      return null;

    OutputStream outputStream = null;
    File file = null;
    try {
      String fileName = FileUtils.getFileName(s3Object.getKey());
      file = new File(fileName);
      outputStream = new FileOutputStream(file);

      int read = 0;
      byte[] bytes = new byte[BUFFER_READ];
      while ((read = s3Object.getObjectContent().read(bytes)) != -1) {
        outputStream.write(bytes, 0, read);
      }

      try {
        outputStream.close();
      } catch (IOException e) {
        LOG.error("Could not close outputStream", e);
      }

      return file;

    } catch (Exception e) {

      if (outputStream != null) {
        try {
          outputStream.close();
        } catch (IOException e2) {
          LOG.error("Could not close outputStream downloading key '" + key + "'", e);
        }
      }

      if (file != null) {
        file.delete();
      }

      throw new IOException("Could not get object in S3.", e);

    }
  }

  private S3Object getS3Object(AWSCredentials credentials, String bucketName, String key) throws IOException {
    if (credentials == null || key == null || bucketName == null) {
      throw new IOException("Paramenters cannot be null");
    }

    LOG.info("GET Object in S3. [key=" + key + "],[bucket=" + bucketName + "]");

    try {

      AmazonS3 s3 = new AmazonS3Client(credentials);
      GetObjectRequest request = new GetObjectRequest(bucketName, key);
      return s3.getObject(request);

    } catch (AmazonServiceException ase) {
      if (ase.getStatusCode() == HttpStatus.NOT_FOUND.value()) {
        // File does not exists
        return null;
      }
      throw new IOException("Could not get object in S3", ase);
    } catch (AmazonClientException ace) {
      throw new IOException("Could not get object in S3", ace);
    }
  }
}
