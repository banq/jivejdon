package com.jdon.jivejdon.model.query.specification;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.jivejdon.model.query.MultiCriteria;
import com.jdon.jivejdon.model.query.QueryCriteria;
import com.jdon.jivejdon.model.query.QuerySpecification;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.UtilValidate;

public class QuerySpecDBModifiedDate implements QuerySpecification {
	private final static Logger logger = LogManager.getLogger(QuerySpecDBModifiedDate.class);

	private QueryCriteria qc;
	private Collection params;
	private String whereSQL;

	public QuerySpecDBModifiedDate(QueryCriteria qc) {
		this.qc = qc;
		this.params = new ArrayList(5);
	}

	public void parse() {
		parse("");
	}

	public void parse(String tablename) {
		logger.debug("enter parse");
		String prefix = "";
		if (!UtilValidate.isEmpty(tablename)) {
			prefix = tablename + ".";
		}
		StringBuilder where = new StringBuilder();
		where.append(" WHERE ");
		where.append(prefix).append("modifiedDate >= ? and ");
		where.append(prefix).append("modifiedDate <= ? ");

		String fromDate = ToolsUtil.dateToMillis(qc.getFromDate().getTime());
		logger.debug("fromDate=" + qc.getFromDate() + " sql formate=" + fromDate);
		String toDate = ToolsUtil.dateToMillis(qc.getToDate().getTime());
		logger.debug("toDate=" + qc.getToDate() + " sql formate=" + toDate);
		params.add(fromDate);
		params.add(toDate);

		if (qc.getForumId() != null) {
			where.append(" and ");
			where.append(prefix).append("forumID = ? ");
			params.add(new Long(qc.getForumId()));
		}

		if (qc instanceof MultiCriteria) {
			MultiCriteria mmqc = (MultiCriteria) qc;
			if (mmqc.getUserID() != null) {
				where.append(" and ");
				where.append(prefix).append("userID = ? ");
				params.add(mmqc.getUserID());
			}
		}
		this.whereSQL = where.toString().intern();
	}

	public Collection getParams() {
		return params;
	}

	public String getWhereSQL() {
		return whereSQL;
	}

	public String getResultSortSQL() {
		return getResultSortSQL("modifiedDate");
	}

	public String getResultSortSQL(String sortName) {
		String sorttableName = "";
		if (!UtilValidate.isEmpty(sortName)) {
			sorttableName = sortName;
		}

		if (qc.getResultSort().isASCENDING()) {
			return " ORDER BY " + sorttableName + " ASC ";
		} else {
			return " ORDER BY " + sorttableName + " DESC ";
		}
	}

}
