package com.standardstate.couch4j.options;

import com.standardstate.couch4j.util.Utils;

public class QueryParameters {

    private Boolean descending = Boolean.FALSE;
    private Integer limit = 0;
    private Boolean includeDocs = Boolean.FALSE;
    private String startKey = null;
    private String endKey = null;

    public Boolean isDescending() {
        return descending;
    }

    public void setDescending(final Boolean descending) {
        this.descending = descending;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(final Integer limit) {
        this.limit = limit;
    }

    public Boolean isIncludeDocs() {
        return includeDocs;
    }

    public void setIncludeDocs(final Boolean includeDocs) {
        this.includeDocs = includeDocs;
    }
    
    @Override
    public String toString() {
        return Utils.objectToJSON(this);
    }
    
}
