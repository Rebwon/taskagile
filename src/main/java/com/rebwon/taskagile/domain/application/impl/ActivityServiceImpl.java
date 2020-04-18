package com.rebwon.taskagile.domain.application.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rebwon.taskagile.domain.application.ActivityService;
import com.rebwon.taskagile.domain.model.activity.Activity;
import com.rebwon.taskagile.domain.model.activity.ActivityRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
  private final ActivityRepository activityRepository;

  @Override
  public void saveActivity(Activity activity) {
    activityRepository.save(activity);
  }
}
