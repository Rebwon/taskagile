package com.rebwon.taskagile.utils;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class IpAddress implements Serializable {
  private static final long serialVersionUID = -146284720882028407L;

  private String value;

  public IpAddress(String value) {
    this.value = value == null ? "" : value;
  }

  public String value() {
    return value;
  }
}
