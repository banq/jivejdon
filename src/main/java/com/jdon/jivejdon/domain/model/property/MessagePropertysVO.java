/*
 * Copyright 2003-2009 the original author or authors.
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
package com.jdon.jivejdon.domain.model.property;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

/**
 *
 * it should be load with messageVO,, because it's mask function will maks the
 * content body of messageVO
 *
 * @author banq
 *
 */
public class MessagePropertysVO {

  public static final String PROPERTY_MASKED = "MASKED";

  public static final String PROPERTY_IP = "IP";

  public static final String DIG_NUMBER = "digNumber";

  private final Collection<Property> propertys;

  private int digCount = -1;

  // for read or load
  public MessagePropertysVO(Collection<Property> propertys) {
    this.propertys = propertys;
    String digCountS = this.getPropertyValue(DIG_NUMBER);
    digCount = digCountS != null ? Integer.parseInt(digCountS) : 0;
  }

  public Collection<Property> getPropertys() {
    return propertys;
  }

  public boolean isMasked() {
    return  getPropertyValue(MessagePropertysVO.PROPERTY_MASKED) != null? true: false;
  }

  // this method is called in messageForm, it is a inputDTO
  public void setMasked(boolean masked) {
    if (masked) {
      replaceProperty(new Property(MessagePropertysVO.PROPERTY_MASKED, "true"));
    } else {
      removeProperty(MessagePropertysVO.PROPERTY_MASKED);
    }
  }

  public void updateMasked(boolean masked) {
    if (masked) {
      Property newproperty = new Property();
      newproperty.setName(MessagePropertysVO.PROPERTY_MASKED);
      newproperty.setValue(MessagePropertysVO.PROPERTY_MASKED);
      replaceProperty(newproperty);
    } else {
      removeProperty(MessagePropertysVO.PROPERTY_MASKED);
    }
  }

  public String getPostip() {
    String ipaddress = "";

    String saveipaddress = getPropertyValue(MessagePropertysVO.PROPERTY_IP);
    if (saveipaddress != null) ipaddress = saveipaddress;
    return ipaddress;
  }

  public void addMessageDigCount() {
    digCount++;
    Property Numberproperty = new Property(DIG_NUMBER, String.valueOf(digCount));
    this.replaceProperty(Numberproperty);
  }

  public int getDigCount() {
    return digCount;
  }

  public void replacePropertys(Collection<Property> newprops) {
    for (Property newproperty:newprops) {
       replaceProperty(newproperty) ;
    }
  }

  public void removeProperty(String propName) {
    propertys.removeIf(item -> item.getName().equalsIgnoreCase(propName));
  }

  private Property getProperty(String propName) {
    Optional<Property> propertyO =  propertys.stream().filter(p -> p.getName().equalsIgnoreCase(propName)).findFirst();
    return propertyO.isPresent()?propertyO.get():null;
  }

  private void replaceProperty(Property newproperty) {
    Optional<Property> propertyO =  propertys.stream().filter(p -> p.getName().equalsIgnoreCase(newproperty.getName())).findFirst();
    if(propertyO.isPresent())
      propertyO.get().setValue(newproperty.getValue());
    else
      propertys.add(newproperty);
  }

  private String getPropertyValue(String propName) {
    Optional<Property> propertyO =  propertys.stream().filter(p -> p.getName().equalsIgnoreCase(propName)).findFirst();
    return propertyO.isPresent()?propertyO.get().getValue():null;
  }
}
