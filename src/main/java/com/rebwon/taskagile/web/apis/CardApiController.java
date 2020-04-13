package com.rebwon.taskagile.web.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.rebwon.taskagile.domain.application.CardService;
import com.rebwon.taskagile.domain.common.security.CurrentUser;
import com.rebwon.taskagile.domain.model.card.Card;
import com.rebwon.taskagile.domain.model.user.SimpleUser;
import com.rebwon.taskagile.web.payload.AddCardPayload;
import com.rebwon.taskagile.web.payload.ChangeCardPositionsPayload;
import com.rebwon.taskagile.web.results.AddCardResult;
import com.rebwon.taskagile.web.results.ApiResult;
import com.rebwon.taskagile.web.results.Result;
import com.rebwon.taskagile.web.updater.CardUpdater;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CardApiController {
  private final CardService cardService;
  private final CardUpdater cardUpdater;

  @PostMapping("/api/cards")
  public ResponseEntity<ApiResult> addCard(@RequestBody AddCardPayload payload,
    @CurrentUser SimpleUser currentUser) {
    Card card = cardService.addCard(payload.toCommand(currentUser.getUserId()));
    cardUpdater.onCardAdded(payload.getBoardId(), card);
    return AddCardResult.build(card);
  }

  @PostMapping("/api/cards/positions")
  public ResponseEntity<ApiResult> changeCardPositions(@RequestBody ChangeCardPositionsPayload payload) {
    cardService.changePositions(payload.toCommand());
    return Result.ok();
  }
}
