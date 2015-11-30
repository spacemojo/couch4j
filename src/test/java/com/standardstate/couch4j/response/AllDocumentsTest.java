package com.standardstate.couch4j.response;

import com.standardstate.couch4j.options.Options;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class AllDocumentsTest {
    
    @Test
    public void constructorTest() { 
        assertNotNull("constructorTest()", new AllDocuments());
    }
    
    @Test
    public void getterSetterTest() {
        
        final AllDocuments allDocuments = new AllDocuments();
        
        allDocuments.setOffset(12);
        assertEquals("offset 12 ", new Integer(12), allDocuments.getOffset());
        
        allDocuments.setTotalRows(1000);
        assertEquals("total rows 1000", new Integer(1000), allDocuments.getTotalRows());
        
        allDocuments.setOptions(new Options());
        assertNotNull("options ", allDocuments.getOptions());
        
    }
    
}
