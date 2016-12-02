package com.txlcommon;

import com.txlcommon.domain.user.TXLUserType;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lucas on 21/08/15.
 */
@Component
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface TXLUserTypeAccess {
    public TXLUserType[] value();
}
