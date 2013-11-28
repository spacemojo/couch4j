package com.standardstate.couch4j.mock;

import java.util.Date;
import java.util.Objects;

public class MockObject {

    private String _id = null;
    private String _rev = null;
    private String name = null;
    private Integer intValue = 0;
    private Date date = null;
    private Boolean active = Boolean.FALSE;

    public String get_id() {
        return _id;
    }

    public void set_id(final String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(final String _rev) {
        this._rev = _rev;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getIntValue() {
        return intValue;
    }

    public void setIntValue(final Integer intValue) {
        this.intValue = intValue;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    public void setDate(final Date date) {
        this.date = new Date(date.getTime());
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this._id);
        hash = 19 * hash + Objects.hashCode(this._rev);
        hash = 19 * hash + Objects.hashCode(this.name);
        hash = 19 * hash + Objects.hashCode(this.intValue);
        hash = 19 * hash + Objects.hashCode(this.date);
        hash = 19 * hash + Objects.hashCode(this.active);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MockObject other = (MockObject) obj;
        if (!Objects.equals(this._id, other._id)) {
            return false;
        }
        if (!Objects.equals(this._rev, other._rev)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.intValue, other.intValue)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.active, other.active)) {
            return false;
        }
        return true;
    }
    
}
