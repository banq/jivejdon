package com.jdon.jivejdon.model.query.specification;

import com.jdon.jivejdon.model.query.ResultSort;
import com.jdon.util.UtilValidate;

public class ThreadListSpec {

	protected String sorttableName;

	protected ResultSort resultSort;

	public ThreadListSpec() {
		sorttableName = "creationDate";
	}

	public String getResultSortSQL() {
		return getResultSortSQL("");
	}

	public String getResultSortSQL(String sortName) {
		if (!UtilValidate.isEmpty(sortName)) {
			sorttableName = sortName;
		}

		if (resultSort != null && resultSort.isASCENDING()) {
			if (UtilValidate.isEmpty(sorttableName))
				return "  ASC ";
			else
				return " ORDER BY " + sorttableName + " ASC ";
		} else {
			if (UtilValidate.isEmpty(sorttableName))
				return " DESC ";
			else
				return " ORDER BY " + sorttableName + " DESC ";
		}
	}

	public ResultSort getResultSort() {
		return resultSort;
	}

	public void setResultSort(ResultSort resultSort) {
		this.resultSort = resultSort;
	}

	public String getSorttableName() {
		return sorttableName;
	}

	public void setSorttableName(String sorttableName) {
		this.sorttableName = sorttableName;
	}

}
