package com.rebwon.taskagile.web.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.rebwon.taskagile.domain.application.CardListService;
import com.rebwon.taskagile.domain.common.security.CurrentUser;
import com.rebwon.taskagile.domain.model.cardlist.CardList;
import com.rebwon.taskagile.domain.model.user.SimpleUser;
import com.rebwon.taskagile.web.payload.AddCardListPayload;
import com.rebwon.taskagile.web.payload.ChangeCardListPositionsPayload;
import com.rebwon.taskagile.web.results.AddCardListResult;
import com.rebwon.taskagile.web.results.ApiResult;
import com.rebwon.taskagile.web.results.Result;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CardListApiController {
  private final CardListService cardListService;

  @PostMapping("/api/card-lists")
  public ResponseEntity<ApiResult> addCardList(@RequestBody AddCardListPayload payload,
    @CurrentUser SimpleUser currentUser) {
    CardList cardList = cardListService.addCardList(payload.toCommand(currentUser.getUserId()));
    return AddCardListResult.build(cardList);
  }

  @PostMapping("/api/card-lists/positions")
  public ResponseEntity<ApiResult> changeCardListPositions(@RequestBody ChangeCardListPositionsPayload payload) {
    cardListService.changePositions(payload.toCommand());
    return Result.ok();
  }
}
