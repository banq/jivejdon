/*
 * Copyright 2003-2005 the original author or authors.
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
package com.jdon.jivejdon.model;

import com.jdon.jivejdon.repository.EmbedIF;

import java.io.Serializable;

/**
 * a Model acts two kinds of Actor in two diiferent flows:
 * * 1. In from repository to domain flow, It is aggregateRoot Entity;
 * * 2. In from domain to UI flow ,It is DTO;
 * *
 * *there are two construct methods in two flows.
 * * 1. using builder() and ForumModel's solid state when in first flow
 * * 2. using business special method such as addChild() and ForumModel's parameter state when in
 * * second flow!
 */
public class ForumModel implements EmbedIF, Serializable {
	private static final long serialVersionUID = 1L;

	// it is a solid object from repository
	protected volatile boolean solid;

	// it is a paramter from command if UI.
	// if  Id  is null, this is a DTO
	protected volatile boolean parameter;

	/**
	 * @return Returns the embedded.
	 */
	public boolean isSolid() {
		return solid;
	}

	/**
	 * when solid is false, this model are not completely ready, it has not
	 * embedded other association sub object. so the model can not be used by
	 * View client.
	 * 
	 */
	public void setSolid(boolean solid) {
		this.solid = solid;
		if (solid)
			this.setParameter(false);
	}

	//not used
	public boolean isParameter() {
		return parameter;
	}

	public void setParameter(boolean parameter) {
		this.parameter = parameter;
		if (parameter)
			this.setSolid(false);
	}

}
