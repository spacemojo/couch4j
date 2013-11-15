package com.standardstate.couch4j.options;

public class AllDocumentsOptions {

    private Boolean descending = Boolean.FALSE;
    private Integer limit = 0;
    private Boolean includeDocs = Boolean.FALSE;

    public Boolean isDescending() {
        return descending;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Boolean isIncludeDocs() {
        return includeDocs;
    }

    public void setIncludeDocs(Boolean includeDocs) {
        this.includeDocs = includeDocs;
    }
    
}
