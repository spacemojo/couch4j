package com.standardstate.couch4j.response;

import com.standardstate.couch4j.options.AllDocumentsOptions;
import java.util.List;

public class AllDocuments {

    private AllDocumentsOptions options = null;
    private Integer totalRows = 0;
    private Integer offset = 0;
    private List rows = null;

    public AllDocumentsOptions getOptions() {
        return options;
    }

    public void setOptions(AllDocumentsOptions options) {
        this.options = options;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
    
}
