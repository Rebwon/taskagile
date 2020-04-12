package com.rebwon.taskagile.config;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;

@Configuration
@ConfigurationProperties(prefix="app")
@Validated
@Getter
public class ApplicationProperties {
  @Email
  @NotBlank
  private String mailFrom;
}
