package com.rebwon.taskagile.web.socket;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ChannelHandler {
  // WebSocketRequestDispatcher에서 처리할 핸들러를 매핑할 채널 패턴으로
  // AntPathMatcher를 사용함.
  String pattern() default "";
  String value() default "";
}
