package com.jdon.jivejdon.model.query;

import java.util.Collection;

/**
 *  QuerySpecification
 *  we regard  SQL as a specification that belongs to domain layer ,
 *  now default is Databse SQL, in the future, can be extends to Hibernate SQL or other
 *  
 * @author banq
 *
 */
public interface QuerySpecification {

	void parse();
	
	void parse(String tablename);
	
	Collection getParams();
	
	String getWhereSQL();
	
	String getResultSortSQL();
	
	String getResultSortSQL(String tablename);
	
}
