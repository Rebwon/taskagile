package com.rebwon.taskagile.web.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBoardMemberPayload {
  private String usernameOrEmailAddress;
}
