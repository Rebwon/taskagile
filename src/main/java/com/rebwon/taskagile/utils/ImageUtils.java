package com.rebwon.taskagile.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.Assert;

public class ImageUtils {
  private ImageUtils () {
  }

  public static String getThumbnailVersion(String imagePath) {
    Assert.hasText(imagePath, "Parameter `imagePath` must not be blank");

    String ext = FilenameUtils.getExtension(imagePath);
    Assert.hasText(ext, "Image `" + imagePath + "` must have extension");

    return FilenameUtils.removeExtension(imagePath) + ".thumbnail." + ext;
  }

  public static boolean isImage(String contentType) {
    if (contentType == null) {
      return false;
    }
    return contentType.startsWith("image/");
  }
}
