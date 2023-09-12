package com.example.demo.commons.utils;

import com.example.models.entity.enums.FileType;

public class FileUtils {

  private FileUtils() {
  }

  public static FileType getFileTypeFromMime(String mimeType) {
    if (mimeType == null)
      return null;
    int pos = mimeType.indexOf('/');
    if (pos == -1)
      return null;
    String type = mimeType.substring(0, pos);
    switch (type) {
      case "video":
        return FileType.video;
      case "image":
        return FileType.image;
      case "audio":
        return FileType.audio;
      case "text":
        return FileType.text;
      default:
        return FileType.undefined;
    }
  }

  public static String getFileName(String key) {
    if (key == null)
      return null;
    int index = key.lastIndexOf("/");
    if (index == -1)
      return key;
    return key.substring(index + 1);
  }
}
