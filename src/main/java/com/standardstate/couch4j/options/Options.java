package com.standardstate.couch4j.options;

import com.standardstate.couch4j.util.Utils;

public class Options {

    private String key = null;
    private Boolean descending = null;
    private Integer limit = null;
    private Boolean includeDocs = null;
    private String startKey = null;
    private String endKey = null;

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

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
        if (limit > 0) {
            this.limit = limit;
        }
    }

    public Boolean isIncludeDocs() {
        return includeDocs;
    }

    public void setIncludeDocs(final Boolean includeDocs) {
        this.includeDocs = includeDocs;
    }

    public String getStartKey() {
        return startKey;
    }

    public void setStartKey(final String startKey) {
        this.startKey = startKey;
    }

    public String getEndKey() {
        return endKey;
    }

    public void setEndKey(final String endKey) {
        this.endKey = endKey;
    }

    @Override
    public String toString() {
        return Utils.objectToJSON(this);
    }

}
