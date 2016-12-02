package com.txlcommon.service;

import com.txlcommon.TXLUserTypeAccess;
import com.txlcommon.domain.user.TXLUser;
import com.txlcommon.domain.user.TXLUserType;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

/**
 * Created by lucas on 21/08/15.
 */
@Component
@Aspect
public class TXLUserTypeAccessAspect {
    @Before(value = "@annotation(com.txlcommon.TXLUserTypeAccess)")
    public void before(JoinPoint joinPoint) throws Throwable {
        boolean allow = false;

        // Get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TXLUser user = (TXLUser) auth.getPrincipal();

        // Get annotation
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        TXLUserTypeAccess annotation = signature.getMethod().getAnnotation(TXLUserTypeAccess.class);
        if (annotation != null) {
            // Get annotated user types
            TXLUserType[] allowedTypes = annotation.value();

            // Check if user is one of the annotated types
            TXLUserType userType = user.getType();
            for (TXLUserType allowedType:allowedTypes) {
                if (userType == allowedType) {
                    allow = true;
                    break;
                }
            }
        }

        if (allow == false) {
            String message = "User '"+user.getUsername()+"' denied access for: " + joinPoint.getSignature().getDeclaringType().getSimpleName() + ", method: " + joinPoint.getSignature().getName();
            // TODO do not return exception to browser/client
            throw new IllegalAccessException(message);
        }

    }

    @After(value = "@annotation(com.txlcommon.TXLUserTypeAccess)")
    public void after(JoinPoint joinPoint) throws Throwable {
        LogFactory.getLog(TXLUserTypeAccessAspect.class).info(" After, class: " + joinPoint.getSignature().getDeclaringType().getSimpleName() + ", method: " + joinPoint.getSignature().getName());
    }
}
