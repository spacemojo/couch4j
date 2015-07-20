package com.standardstate.couch4j.response;

import com.standardstate.couch4j.options.Options;
import java.util.ArrayList;
import java.util.List;

public class AllDocuments {

    private Options options = null;
    private Integer totalRows = 0;
    private Integer offset = 0;
    private final List<String> rows = new ArrayList<>();

    public Options getOptions() {
        return options;
    }

    public void setOptions(final Options options) {
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

    public void addRow(final String toAdd) {
        rows.add(toAdd);
    }

    public List<String> getRows() {
        return this.rows;
    }
    
}
