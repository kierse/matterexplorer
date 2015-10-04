package com.pissiphany.matterexplorer.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by kierse on 15-10-03.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerIntentService { }
