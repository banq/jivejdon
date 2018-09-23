package com.jdon.jivejdon.presentation.form;

import com.jdon.model.ModelForm;

public class ImageInfoForm extends ModelForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String imageId;

	private String name;

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
