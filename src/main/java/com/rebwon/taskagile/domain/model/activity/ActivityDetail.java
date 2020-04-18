package com.rebwon.taskagile.domain.model.activity;

import java.util.HashMap;
import java.util.Map;

import com.rebwon.taskagile.utils.JsonUtils;

public class ActivityDetail {
  private Map<String, Object> detail = new HashMap<>();

  private ActivityDetail() {}

  static ActivityDetail blank() {
    return new ActivityDetail();
  }

  public ActivityDetail add(String key, Object value) {
    detail.put(key, value);
    return this;
  }

  String toJson() {
    return JsonUtils.toJson(detail);
  }
}
