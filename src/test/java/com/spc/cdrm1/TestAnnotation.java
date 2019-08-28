package com.spc.cdrm1;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.lang.model.element.Element;

@Retention(RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface TestAnnotation {

}
