package main.jsonwriter;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JsonWriterTest {
    
    private StringWriter stringWriter;
    private JsonWriter jsonWriter;
    
    @Before
    public void setUp() {
        stringWriter = new StringWriter();
        jsonWriter = new JsonWriter(stringWriter);
    }
    
    @After
    public void tearDown() {
        jsonWriter = null;
        stringWriter = null;
    }

    @Test
    public void testWriteObjectBegin() {
        jsonWriter.writeObjectBegin();
        jsonWriter.flush();

        String actual = stringWriter.toString();
        String expected = "{";
        
        assertEquals(expected, actual);
    }

    @Test
    public void testWriteObjectEnd() {
        jsonWriter.writeObjectEnd();
        jsonWriter.flush();

        String actual = stringWriter.toString();
        String expected = "}";
        
        assertEquals(expected, actual);
    }

    @Test
    public void testWriteArrayBegin() {
        jsonWriter.writeArrayBegin();
        jsonWriter.flush();

        String actual = stringWriter.toString();
        String expected = "[";
        
        assertEquals(expected, actual);
    }

    @Test
    public void testWriteArrayEnd() {
        jsonWriter.writeArrayEnd();
        jsonWriter.flush();

        String actual = stringWriter.toString();
        String expected = "]";
        
        assertEquals(expected, actual);
    }

    @Test
    public void testWriteString() {
        jsonWriter.writeString("str");
        jsonWriter.flush();

        String actual = stringWriter.toString();
        String expected = "\"str\"";
        
        assertEquals(expected, actual);
    }

    @Test
    public void testWriteNumber() {
        jsonWriter.writeNumber(2.2d);
        jsonWriter.flush();

        String actual = stringWriter.toString();
        String expected = "2.2";
        
        assertEquals(expected, actual);
    }

    @Test
    public void testWritePropertySeparator() {
        jsonWriter.writePropertySeparator();
        jsonWriter.flush();

        String actual = stringWriter.toString();
        String expected = ":";
        
        assertEquals(expected, actual);
    }

    @Test
    public void testWriteBoolean_true() {
        jsonWriter.writeBoolean(true);
        jsonWriter.flush();

        String actual = stringWriter.toString();
        String expected = "true";
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testWriteBoolean_false() {
        jsonWriter.writeBoolean(false);
        jsonWriter.flush();

        String actual = stringWriter.toString();
        String expected = "false";
        
        assertEquals(expected, actual);
    }

    @Test
    public void testWriteNull() {
        jsonWriter.writeNull();
        jsonWriter.flush();

        String actual = stringWriter.toString();
        String expected = "null";
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testWriteSeparator() {
        jsonWriter.writeArrayBegin();
        jsonWriter.writeNull();
        jsonWriter.writeSeparator();
        for (int i = 0; i < 2; i++) {
            jsonWriter.writeNumber(i);
            jsonWriter.writeSeparator();
        }
        jsonWriter.writeNumber(2);
        jsonWriter.writeArrayEnd();
        jsonWriter.flush();

        String actual = stringWriter.toString();
        String expected = "[null,0,1,2]";
        
        assertEquals(expected, actual);
        
    }

}
