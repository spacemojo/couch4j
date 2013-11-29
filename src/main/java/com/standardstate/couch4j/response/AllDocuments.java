package com.standardstate.couch4j.response;

import com.standardstate.couch4j.options.AllDocumentsOptions;
import java.util.ArrayList;
import java.util.List;

public class AllDocuments<T> {

    private AllDocumentsOptions options = null;
    private Integer totalRows = 0;
    private Integer offset = 0;
    private List<T> rows = new ArrayList<>();

    public AllDocumentsOptions getOptions() {
        return options;
    }

    public void setOptions(final AllDocumentsOptions options) {
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

    public void addRow(final T toAdd) {
        rows.add(toAdd);
    }

    public List<T> getRows() {
        return this.rows;
    }
    
}
