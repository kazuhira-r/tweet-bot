package org.littlewings.tweetbot.application.lilymyu.lyrics.qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PACKAGE, ElementType.TYPE})
public @interface LilymyuLyricsTweetBot {
}
