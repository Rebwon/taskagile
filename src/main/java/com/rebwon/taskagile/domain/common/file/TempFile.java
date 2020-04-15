package com.rebwon.taskagile.domain.common.file;

import java.io.File;
import java.nio.file.Path;

import lombok.Getter;

@Getter
public class TempFile {
  private String rootTempPath;
  private String fileRelativePath;

  public static TempFile create(String rootTempPath, Path fileAbsolutePath) {
    TempFile tempFile = new TempFile();
    tempFile.rootTempPath = rootTempPath;
    tempFile.fileRelativePath = fileAbsolutePath.toString().replaceFirst(rootTempPath + "/", "");
    return tempFile;
  }

  public File getFile() {
    return new File(rootTempPath + "/" + fileRelativePath);
  }
}
