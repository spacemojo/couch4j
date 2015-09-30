package com.standardstate.couch4j.mock;

import com.standardstate.couch4j.AbstractCouchDBDocument;
import java.util.Objects;
import org.joda.time.DateTime;

public class MockObject extends AbstractCouchDBDocument {

    private String type = null;
    private String name = null;
    private Integer intValue = 0;
    private DateTime date = null;
    private Boolean active = Boolean.FALSE;

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

    public DateTime getDate() {
        return new DateTime(date.getMillis());
    }

    public void setDate(final DateTime date) {
        this.date = new DateTime(date.getMillis());
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
        hash = 19 * hash + Objects.hashCode(this.get_id());
        hash = 19 * hash + Objects.hashCode(this.get_rev());
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
        if (!Objects.equals(this.get_id(), other.get_id())) {
            return false;
        }
        if (!Objects.equals(this.get_rev(), other.get_rev())) {
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
