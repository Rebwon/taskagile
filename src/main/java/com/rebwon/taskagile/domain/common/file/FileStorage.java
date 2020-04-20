package com.rebwon.taskagile.domain.common.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
  TempFile saveAsTempFile(String folder, MultipartFile file);
  void saveTempFile(TempFile tempFile);
  String saveUploaded(String folder, MultipartFile file);
}
