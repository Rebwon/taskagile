package com.rebwon.taskagile.web.socket;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;

import com.rebwon.taskagile.utils.JsonUtils;

public class ChannelHandlerInvoker {
  private static final Logger log = LoggerFactory.getLogger(ChannelHandlerInvoker.class);
  private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

  private String channelPattern;
  private Object handler;
  // Map의 Key는 동작이고, 값은 핸들러 메서드이다.
  private final Map<String, Method> actionMethods = new HashMap<>();

  public ChannelHandlerInvoker(Object handler) {
    Assert.notNull(handler, "Parameter `handler` must not be null");

    Class<?> handlerClass = handler.getClass();
    ChannelHandler handlerAnnotation = handlerClass.getAnnotation(ChannelHandler.class);
    Assert.notNull(handlerAnnotation, "Parameter `handler` must have annotation @ChannelHandler");

    Method[] methods = handlerClass.getMethods();
    for(Method method : methods) {
      Action actionAnnotation = method.getAnnotation(Action.class);
      if(actionAnnotation == null) {
        continue;
      }

      String action = actionAnnotation.value();
      actionMethods.put(action, method);
      log.debug("Mapped action `{}` in channel handler `{}#{}`", action, handlerClass.getName(), method);
    }

    this.channelPattern = ChannelHandlers.getPattern(handlerAnnotation);
    this.handler = handler;
  }

  public boolean supports(String action) {
    return actionMethods.containsKey(action);
  }

  public void handle(IncomingMessage incomingMessage, RealTimeSession session) {
    Assert.isTrue(antPathMatcher.match(channelPattern, incomingMessage.getChannel()), "Channel of the handler must match");
    Method actionMethod = actionMethods.get(incomingMessage.getAction());
    Assert.notNull(actionMethod, "Action method for `" + incomingMessage.getAction() + "` must exist");

    // 필요한 매개변수를 찾는다.
    Class<?>[] parameterTypes = actionMethod.getParameterTypes();
    // 각 매개변수에 대한 애노테이션.
    Annotation[][] allParameterAnnotations = actionMethod.getParameterAnnotations();
    // 액션 메서드로 전달될 인자값.
    Object[] args = new Object[parameterTypes.length];

    try{
      // 인자 값을 채운다.
      for(int i=0; i<parameterTypes.length; i++) {
        Class<?> parameterType = parameterTypes[i];
        Annotation[] parameterAnnotations = allParameterAnnotations[i];

        // 이 매개변수엔 애노테이션이 지정되지 않는다.
        if(parameterAnnotations.length == 0) {
          if(parameterType.isInstance(session)) {
            args[i] = session;
          } else{
            args[i] = null;
          }
          continue;
        }

        // 매개변수에 첫번째 애노테이션만 적용함.
        Annotation parameterAnnotation = parameterAnnotations[0];
        if(parameterAnnotation instanceof Payload) {
          Object arg = JsonUtils.toObject(incomingMessage.getPayload(), parameterType);
          if (arg == null) {
            throw new IllegalArgumentException("Unable to instantiate parameter of type `" +
              parameterType.getName() + "`.");
          }
          args[i] = arg;
        } else if(parameterAnnotation instanceof ChannelValue) {
          args[i] = incomingMessage.getChannel();
        }
      }

      actionMethod.invoke(handler, args);
    } catch (Exception e) {
      String error = "Failed to invoker action method `" + incomingMessage.getAction() +
        "` at channel `" + incomingMessage.getChannel() + "` ";
      log.error(error, e);
      session.error(error);
    }
  }
}
