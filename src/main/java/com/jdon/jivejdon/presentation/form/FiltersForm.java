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
package com.jdon.jivejdon.presentation.form;

import com.jdon.jivejdon.domain.model.message.MessageVO;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class FiltersForm extends BaseForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BeanDescriptor[] availableDescriptors; // all filter's Descriptors

	private Collection unInstalledDescriptors; // unInstalled filter's
												// Descriptors

	private BeanDescriptor[] descriptors; // Installed filter's Descriptors

	private Function<MessageVO, MessageVO>[] filters; // Installed filters

	private Map propertyDescriptors = new HashMap();

	private int filterCount;
	private int filterIndex;
	private int pos;
	private boolean up;
	private boolean down;

	/**
	 * @return Returns the down.
	 */
	public boolean isDown() {
		return down;
	}

	/**
	 * @param down
	 *            The down to set.
	 */
	public void setDown(boolean down) {
		this.down = down;
	}

	/**
	 * @return Returns the filterIndex.
	 */
	public int getFilterIndex() {
		return filterIndex;
	}

	/**
	 * @param filterIndex
	 *            The filterIndex to set.
	 */
	public void setFilterIndex(int filterIndex) {
		this.filterIndex = filterIndex;
	}

	/**
	 * @return Returns the up.
	 */
	public boolean isUp() {
		return up;
	}

	/**
	 * @param up
	 *            The up to set.
	 */
	public void setUp(boolean up) {
		this.up = up;
	}

	/**
	 * @return Returns the availableDescriptors.
	 */
	public BeanDescriptor[] getAvailableDescriptors() {
		return availableDescriptors;
	}

	/**
	 * @param availableDescriptors
	 *            The availableDescriptors to set.
	 */
	public void setAvailableDescriptors(BeanDescriptor[] availableDescriptors) {
		this.availableDescriptors = availableDescriptors;
	}

	/**
	 * @return Returns the filtersSize.
	 */
	public int getFiltersSize() {
		return availableDescriptors.length;
	}

	/**
	 * @return Returns the filters.
	 */
	public Function<MessageVO, MessageVO>[] getFilters() {
		return filters;
	}

	/**
	 * @param filters The filters to set.
	 */
	public void setFilters(Function<MessageVO, MessageVO>[] filters) {
		this.filters = filters;
	}

	public Function<MessageVO, MessageVO> getFiltersIndexed(Integer index) {
		return (Function<MessageVO, MessageVO>) filters[index.intValue()];
	}

	public void setStringIndexed(int index, Function<MessageVO, MessageVO> filter) {
		filters[index] = filter;
	}

	/**
	 * @return Returns the propertyDescriptors.
	 */
	public PropertyDescriptor[] getPropertyDescriptors(BeanDescriptor descriptor) {
		return (PropertyDescriptor[]) propertyDescriptors.get(descriptor);
	}

	/**
	 * @param propertyDescriptors
	 *            The propertyDescriptors to set.
	 */
	public void setPropertyDescriptors(BeanDescriptor descriptor, PropertyDescriptor[] propertyDescriptors) {
		this.propertyDescriptors.put(descriptor, propertyDescriptors);
	}

	/**
	 * @return Returns the filterCount.
	 */
	public int getFilterCount() {
		return filterCount;
	}

	/**
	 * @param filterCount
	 *            The filterCount to set.
	 */
	public void setFilterCount(int filterCount) {
		this.filterCount = filterCount;
	}

	/**
	 * @return Returns the descriptors.
	 */
	public BeanDescriptor[] getDescriptors() {
		return descriptors;
	}

	/**
	 * @param descriptors
	 *            The descriptors to set.
	 */
	public void setDescriptors(BeanDescriptor[] descriptors) {
		this.descriptors = descriptors;
	}

	/**
	 * @return Returns the pos.
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * @param pos
	 *            The pos to set.
	 */
	public void setPos(int pos) {
		this.pos = pos;
	}

	/**
	 * @return Returns the propertyDescriptors.
	 */
	public Map getPropertyDescriptors() {
		return propertyDescriptors;
	}

	/**
	 * @param propertyDescriptors
	 *            The propertyDescriptors to set.
	 */
	public void setPropertyDescriptors(Map propertyDescriptors) {
		this.propertyDescriptors = propertyDescriptors;
	}

	/**
	 * @return Returns the unInstalledDescriptors.
	 */
	public Collection getUnInstalledDescriptors() {
		return unInstalledDescriptors;
	}

	/**
	 * @param unInstalledDescriptors
	 *            The unInstalledDescriptors to set.
	 */
	public void setUnInstalledDescriptors(Collection unInstalledDescriptors) {
		this.unInstalledDescriptors = unInstalledDescriptors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.presentation.form.BaseForm#doValidate(org.apache.struts
	 * .action.ActionMapping, javax.servlet.http.HttpServletRequest,
	 * java.util.List)
	 */
	public void doValidate(ActionMapping mapping, HttpServletRequest request, List errors) {
		// TODO Auto-generated method stub

	}

}
