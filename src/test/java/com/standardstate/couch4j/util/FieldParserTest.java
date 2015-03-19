package com.standardstate.couch4j.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class FieldParserTest {
    
    @Test
    public void parse() {
        
        assertNotNull("parse()", new FieldParser());
        
        assertEquals("parse()", FieldParser.COUCHDB_ID, FieldParser.parse("0e82a5e01dc50a5f97e1cd0e5940ef58"));
        assertEquals("parse()", FieldParser.COUCHDB_ID, FieldParser.parse("0e82a5e01dc50a5f97e1cd0e5941bf52"));
        
        assertEquals("parse()", FieldParser.COUCHDB_REV, FieldParser.parse("47-0e82a5e01dc50a5f97e1cd0e5940ef58"));
        assertEquals("parse()", FieldParser.COUCHDB_REV, FieldParser.parse("3-0e82a5e01dc50a5f97e1cd0e5941bf52"));
        
    }
    
}
