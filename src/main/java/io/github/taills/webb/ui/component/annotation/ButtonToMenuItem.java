package io.github.taills.webb.ui.component.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ButtonToMenuItem {
   String name() default "";
}
