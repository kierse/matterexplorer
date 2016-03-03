package com.pissiphany.matterexplorer.di.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by kierse on 15-09-13.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity { }
