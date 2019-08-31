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
package com.jdon.jivejdon.model.proptery;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.event.domain.producer.read.LazyLoaderRole;
import com.jdon.jivejdon.model.Property;
import com.jdon.jivejdon.model.util.LazyLoader;

import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * it should be load with messageVO,, because it's mask function will maks the
 * content body of messageVO
 * 
 * @author banq
 * 
 */
public class MessagePropertysVO extends LazyLoader {
	public final static String PROPERTY_MASKED = "MASKED";

	public final static String PROPERTY_IP = "IP";

	public final static String DIG_NUMBER = "digNumber";

	private final long messageId;

	private LazyLoaderRole lazyLoaderRole;

	private Collection<Property> propertys;

	// for read or load
	public MessagePropertysVO(long messageId, LazyLoaderRole lazyLoaderRole) {
		this.messageId = messageId;
		this.lazyLoaderRole = lazyLoaderRole;
	}

	// for be written
	public MessagePropertysVO(long messageId, Collection<Property> propertys) {
		this.messageId = messageId;
		this.propertys = propertys;
	}

	public long getMessageId() {
		return messageId;
	}

	public Collection<Property> getPropertys() {
		if (this.propertys != null)
			return this.propertys;

		if (propertys == null && lazyLoaderRole != null) {
			// synchronized (this) {
			Collection<Property> propertys = (Collection) super.loadBlockedResult();
			if (propertys != null)
				this.propertys = propertys;
			// }
		} else if (propertys == null && lazyLoaderRole == null) {
			System.err.print("my god, how propertys was bornd?");
		}
		return propertys;
	}

	public void setPropertys(Collection<Property> propertys) {
		this.propertys = propertys;
	}

	public void preLoadPropertys() {
		super.preload();
	}

	public boolean isMasked() {
		if (getPropertyValue(MessagePropertysVO.PROPERTY_MASKED) != null)
			return true;
		else
			return false;
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
		if (saveipaddress != null)
			ipaddress = saveipaddress;
		return ipaddress;
	}

	public void addMessageDigCount() {
		int number = getDigCount();
		number++;
		Property Numberproperty = new Property(DIG_NUMBER, String.valueOf(number));
		this.replaceProperty(Numberproperty);
	}

	public int getDigCount() {
		if (this.getPropertyValue(DIG_NUMBER) == null)
			return 0;
		return Integer.parseInt(this.getPropertyValue(DIG_NUMBER));
	}

	public void replacePropertys(Collection<Property> newprops) {
		Iterator iter = newprops.iterator();
		while (iter.hasNext()) {
			Property newproperty = (Property) iter.next();
			Property oldproperty = getProperty(newproperty.getName());
			if (oldproperty == null) {
				getPropertys().add(newproperty);
			} else {
				oldproperty.setValue(newproperty.getValue());
			}
		}

	}

	public void removeProperty(String propName) {
		Iterator iter = getPropertys().iterator();
		while (iter.hasNext()) {
			Property property = (Property) iter.next();
			if (property.getName().equalsIgnoreCase(propName)) {
				getPropertys().remove(property);
				break;
			}
		}
	}

	private Property getProperty(String propName) {
		Iterator iter = getPropertys().iterator();
		while (iter.hasNext()) {
			Property property = (Property) iter.next();
			if (property.getName().equalsIgnoreCase(propName)) {
				return property;
			}
		}
		return null;
	}

	private void replaceProperty(Property newproperty) {
		boolean found = false;
		Iterator iter = getPropertys().iterator();
		while (iter.hasNext()) {
			Property property = (Property) iter.next();
			if (property.getName().equalsIgnoreCase(newproperty.getName())) {
				property.setValue(newproperty.getValue());
				found = true;
			}
		}
		if (!found) {
			getPropertys().add(newproperty);
		}
	}

	private String getPropertyValue(String propName) {
		Iterator iter = getPropertys().iterator();
		while (iter.hasNext()) {
			Property property = (Property) iter.next();
			if (property.getName().equalsIgnoreCase(propName)) {
				return property.getValue();
			}
		}
		return null;
	}

	@Override
	public DomainMessage getDomainMessage() {
		if (lazyLoaderRole == null){
			System.err.println("lazyLoaderRole is null for message="+this.messageId);
		}
		return lazyLoaderRole.loadMessageProperties(messageId);
	}

}
