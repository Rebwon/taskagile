package com.rebwon.taskagile.domain.model.activity;

import java.util.List;

import com.rebwon.taskagile.domain.model.card.CardId;

public interface ActivityRepository {
  void save(Activity activity);
  List<Activity> findCardActivities(CardId cardId);
}
