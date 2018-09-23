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
package com.jdon.jivejdon.repository.dao.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.*;

import com.jdon.jivejdon.repository.dao.SetupDao;

/**
 * @author <a href="mailto:banqJdon<AT>jdon.com">banq</a>
 *
 */

public class SetupDaoSql implements SetupDao {
    private final static Logger logger = LogManager.getLogger(SetupDaoSql.class);

    private JdbcTempSource jdbcTempSource;

    public SetupDaoSql(JdbcTempSource jdbcTempSource) {
        this.jdbcTempSource = jdbcTempSource;
    }

    public String getSetupValue(String name) {
        String sql = "select value from setup where name = ?";
        List queryParams = new ArrayList();
        queryParams.add(name);

        String result = "";
        try {
            List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, sql);
            Iterator iter = list.iterator();
            if (iter.hasNext()) {
                Map map = (Map) iter.next();
                result = (String) map.get("value");
            }

        } catch (Exception e) {
            logger.error(e);
        }
        return result;
    }

    public void saveSetupValue(String name, String value) {
        String sql = "REPLACE INTO setup (name, value) VALUES(?,?)";
        List queryParams = new ArrayList();
        queryParams.add(name);
        queryParams.add(value);        
        try {
            jdbcTempSource.getJdbcTemp().operate(queryParams, sql);
        } catch (Exception e) {
            logger.error(e);
        }
    }

}
