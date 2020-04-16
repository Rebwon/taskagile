package com.rebwon.taskagile.utils;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Size implements Serializable {
  private static final long serialVersionUID = -4143050815950980095L;

  private int width;
  private int height;

  @Override
  public String toString() {
    return width + "x" + height;
  }
}
