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

import java.util.ArrayList;
import java.util.Collection;
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
  private boolean masked = false;

  public static final String PROPERTY_IP = "IP";
  private String postIP;

  public static final String DIG_NUMBER = "digNumber";
  private int digCount = -1;

  private volatile String lastDigIP;

  private final Collection<Property> propertys;

  private boolean loaded;

  // for read or load
  public MessagePropertysVO() {
    this.propertys = new ArrayList<>();
  }

  // for read or load
  public void init() {

    String digCountS = this.getPropertyValue(DIG_NUMBER);
    digCount = digCountS != null ? Integer.parseInt(digCountS) : 0;

    masked = getPropertyValue(PROPERTY_MASKED) != null ? true : false;

    postIP = getPropertyValue(PROPERTY_IP);

    loaded = true;
  }

  public boolean isMasked() {
    if (!loaded)
      init();
    return masked;
  }

  public void updateMasked(boolean masked) {
    this.masked = masked;
    if (masked) {
      Property newproperty = new Property();
      newproperty.setName(MessagePropertysVO.PROPERTY_MASKED);
      newproperty.setValue(MessagePropertysVO.PROPERTY_MASKED);
      replaceProperty(newproperty);
    } else {
      removeProperty(MessagePropertysVO.PROPERTY_MASKED);
    }
    loaded = false;
  }

  public void removeProperty(String propName) {
    propertys.removeIf(item -> item.getName().equalsIgnoreCase(propName));
    loaded = false;
  }

  public String getPostip() {
    if (!loaded)
      init();
    return postIP;
  }

  private void setLastDigIP(String ip) {
    this.lastDigIP = ip;
  }

  public synchronized void addMessageDigCount(String ip) {
    if (lastDigIP != null && lastDigIP.equals(ip)) {   
      return;
    }

    digCount++;
    Property Numberproperty = new Property(DIG_NUMBER, String.valueOf(digCount));
    this.replaceProperty(Numberproperty);
    setLastDigIP(ip);
  }

  public int getDigCount() {
    if (!loaded)
      init();
    return digCount;
  }

  public Collection<Property> getPropertys() {
    return propertys;
  }

  private String getPropertyValue(String propName) {
    Optional<Property> propertyO = propertys.stream().filter(p -> p.getName().equalsIgnoreCase(propName)).findFirst();
    return propertyO.isPresent() ? propertyO.get().getValue() : null;
  }

  public Collection<Property> replacePropertys(Collection<Property> newprops) {
    for (Property newproperty : newprops) {
      replaceProperty(newproperty);
    }
    loaded = false;
    return propertys;
  }

  private Collection<Property> replaceProperty(Property newproperty) {
    Optional<Property> propertyO = propertys.stream().filter(p -> p.getName().equalsIgnoreCase(newproperty.getName()))
        .findFirst();
    if (propertyO.isPresent())
      propertyO.get().setValue(newproperty.getValue());
    else
      propertys.add(newproperty);
    return propertys;
  }

}
