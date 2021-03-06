package com.pissiphany.matterexplorer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by kierse on 15-09-08.
 *
 * Based on implementation and discussion found at:
 * https://gist.github.com/JakeWharton/0d67d01badcee0ae7bc9
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoGson {
    // A reference to the AutoValue-generated class (e.g. AutoValue_MyClass). This is
    // necessary to handle obfuscation of the class names.
    Class autoParcelClass();
}
