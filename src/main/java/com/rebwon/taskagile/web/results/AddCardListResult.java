package com.rebwon.taskagile.web.results;

import org.springframework.http.ResponseEntity;

import com.rebwon.taskagile.domain.model.cardlist.CardList;

public class AddCardListResult {
  public static ResponseEntity<ApiResult> build(CardList cardList) {
    ApiResult apiResult = ApiResult.blank()
      .add("id", cardList.getId().value())
      .add("name", cardList.getName());
    return Result.ok(apiResult);
  }
}
