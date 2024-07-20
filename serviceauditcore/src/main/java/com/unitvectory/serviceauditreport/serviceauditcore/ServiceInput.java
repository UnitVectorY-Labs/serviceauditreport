/*
 * Copyright 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.unitvectory.serviceauditreport.serviceauditcore;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * The service input annotation. This annotation is used to specify the input
 * classes that are used by the service.
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ServiceInput {

    /**
     * The parent class of the service.
     * 
     * @return the parent class of the service
     */
    Class<?> parent();

    /**
     * The relatives classes of the service. These are classes that are related to
     * the class. The dependencies between classes and their relatives must form an
     * acyclic graph.
     */
    Class<?>[] relatives() default {};
}
