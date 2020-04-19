package com.rebwon.taskagile.domain.model.attachment;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.rebwon.taskagile.domain.common.file.FileStorage;
import com.rebwon.taskagile.domain.common.file.FileStorageResolver;
import com.rebwon.taskagile.domain.common.file.TempFile;
import com.rebwon.taskagile.domain.model.card.CardId;
import com.rebwon.taskagile.domain.model.user.UserId;
import com.rebwon.taskagile.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AttachmentManagement {
  private final FileStorageResolver fileStorageResolver;
  private final ThumbnailCreator thumbnailCreator;
  private final AttachmentRepository attachmentRepository;

  public Attachment save(CardId cardId, MultipartFile file, UserId userId) {
    FileStorage fileStorage = fileStorageResolver.resolve();

    String filePath;
    String folder = "attachments";
    boolean thumbnailCreated = false;
    if (ImageUtils.isImage(file.getContentType())) {
      filePath = saveImage(fileStorage, folder, file);
      thumbnailCreated = true;
    } else {
      filePath = fileStorage.saveUploaded(folder, file);
    }

    Attachment attachment = Attachment.create(cardId, userId, file.getOriginalFilename(), filePath, thumbnailCreated);
    attachmentRepository.save(attachment);
    return attachment;
  }

  private String saveImage(FileStorage fileStorage, String folder, MultipartFile file) {
    // Save the file as a local temp file
    TempFile tempImageFile = fileStorage.saveAsTempFile(folder, file);

    // Save the temp image file to its target location
    fileStorage.saveTempFile(tempImageFile);

    // Create a thumbnail of the image file
    thumbnailCreator.create(fileStorage, tempImageFile);

    try {
      Files.delete(tempImageFile.getFile().toPath());
    } catch (IOException e) {
      log.error("Failed to delete temp file `" + tempImageFile.getFile().getAbsolutePath() + "`", e);
    }
    return tempImageFile.getFileRelativePath();
  }
}
