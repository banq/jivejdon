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
package com.jdon.jivejdon.infrastructure.repository.dao.sql;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.model.query.JdbcTemp;

/**
 * this is for another dataSource that User SSO Login Database
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class JdbcTempSSOSource {
	private final static Logger logger = LogManager.getLogger(JdbcTempSSOSource.class);

	protected DataSource dataSource;

	protected JdbcTemp jdbcTemp;

	public JdbcTempSSOSource(String jndiname) {

		try {
			InitialContext ic = new InitialContext();
			dataSource = (DataSource) ic.lookup(jndiname);
			jdbcTemp = new JdbcTemp(dataSource);
		} catch (NamingException e) {
			logger.error("NamingException" + e);
		} catch (Exception slx) {
			logger.error(slx);
		}

	}

	/**
	 * @return Returns the dataSource.
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource
	 *            The dataSource to set.
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @return Returns the jdbcTemp.
	 */
	public JdbcTemp getJdbcTemp() {
		return jdbcTemp;
	}

	/**
	 * @param jdbcTemp
	 *            The jdbcTemp to set.
	 */
	public void setJdbcTemp(JdbcTemp jdbcTemp) {
		this.jdbcTemp = jdbcTemp;
	}
}
