package com.rebwon.taskagile.web.apis;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.rebwon.taskagile.domain.application.CardService;
import com.rebwon.taskagile.domain.application.commands.AddCardAttachmentCommand;
import com.rebwon.taskagile.domain.application.commands.AddCardCommand;
import com.rebwon.taskagile.domain.application.commands.AddCardCommentCommand;
import com.rebwon.taskagile.domain.application.commands.ChangeCardDescriptionCommand;
import com.rebwon.taskagile.domain.application.commands.ChangeCardPositionsCommand;
import com.rebwon.taskagile.domain.application.commands.ChangeCardTitleCommand;
import com.rebwon.taskagile.domain.common.file.FileUrlCreator;
import com.rebwon.taskagile.domain.common.security.CurrentUser;
import com.rebwon.taskagile.domain.model.activity.Activity;
import com.rebwon.taskagile.domain.model.attachment.Attachment;
import com.rebwon.taskagile.domain.model.card.Card;
import com.rebwon.taskagile.domain.model.card.CardId;
import com.rebwon.taskagile.domain.model.user.SimpleUser;
import com.rebwon.taskagile.web.payload.AddCardCommentPayload;
import com.rebwon.taskagile.web.payload.AddCardPayload;
import com.rebwon.taskagile.web.payload.ChangeCardDescriptionPayload;
import com.rebwon.taskagile.web.payload.ChangeCardPositionsPayload;
import com.rebwon.taskagile.web.payload.ChangeCardTitlePayload;
import com.rebwon.taskagile.web.results.AddCardResult;
import com.rebwon.taskagile.web.results.ApiResult;
import com.rebwon.taskagile.web.results.AttachmentResult;
import com.rebwon.taskagile.web.results.AttachmentResults;
import com.rebwon.taskagile.web.results.CardActivitiesResult;
import com.rebwon.taskagile.web.results.CardResult;
import com.rebwon.taskagile.web.results.CommentActivityResult;
import com.rebwon.taskagile.web.results.Result;
import com.rebwon.taskagile.web.updater.CardUpdater;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CardApiController extends AbstractBaseController {
  private final CardService cardService;
  private final CardUpdater cardUpdater;
  private final FileUrlCreator fileUrlCreator;

  @PostMapping("/api/cards")
  public ResponseEntity<ApiResult> addCard(@RequestBody AddCardPayload payload,
    HttpServletRequest request) {
    AddCardCommand command = payload.toCommand();
    addTriggeredBy(command, request);

    Card card = cardService.addCard(payload.toCommand());
    cardUpdater.onCardAdded(payload.getBoardId(), card);
    return AddCardResult.build(card);
  }

  @GetMapping("/api/cards/{cardId}")
  public ResponseEntity<ApiResult> getCard(@PathVariable long cardId) {
    Card card = cardService.findById(new CardId(cardId));
    return CardResult.build(card);
  }

  @PostMapping("/api/cards/positions")
  public ResponseEntity<ApiResult> changeCardPositions(@RequestBody ChangeCardPositionsPayload payload,
    HttpServletRequest request) {
    ChangeCardPositionsCommand command = payload.toCommand();
    addTriggeredBy(command, request);

    cardService.changePositions(command);
    return Result.ok();
  }

  @PutMapping("/api/cards/{cardId}/title")
  public ResponseEntity<ApiResult> changeTitle(@PathVariable long cardId,
    @RequestBody ChangeCardTitlePayload payload, HttpServletRequest request) {
    ChangeCardTitleCommand command = payload.toCommand(cardId);
    addTriggeredBy(command, request);

    cardService.changeCardTitle(command);
    return Result.ok();
  }

  @PutMapping("/api/cards/{cardId}/description")
  public ResponseEntity<ApiResult> changeDescription(@PathVariable long cardId,
    @RequestBody ChangeCardDescriptionPayload payload, HttpServletRequest request) {
    ChangeCardDescriptionCommand command = payload.toCommand(cardId);
    addTriggeredBy(command, request);

    cardService.changeCardDescription(command);
    return Result.ok();
  }

  @PostMapping("/api/cards/{cardId}/comments")
  public ResponseEntity<ApiResult> addCardComment(@PathVariable long cardId,
    @RequestBody AddCardCommentPayload payload, HttpServletRequest request) {
    AddCardCommentCommand command = payload.toCommand(new CardId(cardId));
    addTriggeredBy(command, request);

    Activity activity = cardService.addComment(command);
    return CommentActivityResult.build(activity);
  }

  @GetMapping("/api/cards/{cardId}/activities")
  public ResponseEntity<ApiResult> getCardActivities(@PathVariable long cardId) {
    List<Activity> activities = cardService.findCardActivities(new CardId(cardId));
    return CardActivitiesResult.build(activities);
  }

  @PostMapping("/api/cards/{cardId}/attachments")
  public ResponseEntity<ApiResult> addAttachment(@PathVariable long cardId,
    @RequestParam("file") MultipartFile file, HttpServletRequest request) {
    AddCardAttachmentCommand command = new AddCardAttachmentCommand(cardId, file);
    addTriggeredBy(command, request);

    Attachment attachment = cardService.addAttachment(command);
    return AttachmentResult.build(attachment, fileUrlCreator);
  }

  @GetMapping("/api/cards/{cardId}/attachments")
  public ResponseEntity<ApiResult> getAttachments(@PathVariable long cardId) {
    List<Attachment> attachments = cardService.getAttachments(new CardId(cardId));
    return AttachmentResults.build(attachments, fileUrlCreator);
  }
}
