package com.standardstate.couch4j.response;

import com.standardstate.couch4j.options.QueryParameters;
import java.util.ArrayList;
import java.util.List;

public class AllDocuments {

    private QueryParameters options = null;
    private Integer totalRows = 0;
    private Integer offset = 0;
    private final List rows = new ArrayList<>();

    public QueryParameters getOptions() {
        return options;
    }

    public void setOptions(final QueryParameters options) {
        this.options = options;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(final Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(final Integer offset) {
        this.offset = offset;
    }

    public void addRow(final Object toAdd) {
        rows.add(toAdd);
    }

    public List getRows() {
        return this.rows;
    }
    
}
