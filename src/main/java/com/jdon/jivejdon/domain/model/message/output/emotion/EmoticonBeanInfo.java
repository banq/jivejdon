/*
 * Copyright 2003-2006 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.domain.model.message.output.emotion;

import com.jdon.jivejdon.domain.model.message.output.beanutil.FilterBeanInfo;

/**
 * BeanInfo class for the Emoticon filter.
 */
public class EmoticonBeanInfo extends FilterBeanInfo {

    public static final String [] PROPERTY_NAMES = {
        "imageURL",
        "filteringSubject",
        "filteringBody"
    };

    public EmoticonBeanInfo() {
        super();
    }

    public Class getBeanClass() {
        return Emoticon.class;
    }

    public String [] getPropertyNames() {
        return PROPERTY_NAMES;
    }

    public String getName() {
        return "Emoticon";
    }
}
