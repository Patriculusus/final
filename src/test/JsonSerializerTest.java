package test;

import main.JsonSerializer;
import main.annotation.JsonIgnore;
import main.annotation.JsonProperty;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class JsonSerializerTest {

    private JsonSerializer serializer;

    @Before
    public void setUp() {
        serializer = new JsonSerializer();
    }

    @After
    public void tearDown() {
        serializer = null;
    }

    @Test
    public void testSerialize_BytePrimitive() {
        byte input = 113;
        String actual = serializer.serialize(input);
        String expected = "113";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_ByteObject() {
        Byte input = 113;
        String actual = serializer.serialize(input);
        String expected = "113";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_ShortPrimitive() {
        short input = 113;
        String actual = serializer.serialize(input);
        String expected = "113";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_ShortObject() {
        Short input = 113;
        String actual = serializer.serialize(input);
        String expected = "113";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_IntegerPrimitive() {
        int input = 113;
        String actual = serializer.serialize(input);
        String expected = "113";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_IntegerObject() {
        Integer input = 113;
        String actual = serializer.serialize(input);
        String expected = "113";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_LongPrimitive() {
        long input = 113;
        String actual = serializer.serialize(input);
        String expected = "113";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_LongObject() {
        Long input = 113123l;
        String actual = serializer.serialize(input);
        String expected = "113123";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_FloatPrimitive() {
        float input = 113.2f;
        String actual = serializer.serialize(input);
        String expected = "113.2";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_FloatObject() {
        Float input = 113.2f;
        String actual = serializer.serialize(input);
        String expected = "113.2";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_DoublePrimitive() {
        double input = 113.25d;
        String actual = serializer.serialize(input);
        String expected = "113.25";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_DoubleObject() {
        Double input = 113.25d;
        String actual = serializer.serialize(input);
        String expected = "113.25";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_BooleanPrimitive() {
        boolean input = false;
        String actual = serializer.serialize(input);
        String expected = "false";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_BooleanObject() {
        Boolean input = false;
        String actual = serializer.serialize(input);
        String expected = "false";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_CharacterPrimitive() {
        char input = 'a';
        String actual = serializer.serialize(input);
        String expected = "\"a\"";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_CharacterObject() {
        Character input = 'a';
        String actual = serializer.serialize(input);
        String expected = "\"a\"";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_String() {
        String input = "str";
        String actual = serializer.serialize(input);
        String expected = "\"str\"";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_Null() {
        String input = null;
        String actual = serializer.serialize(input);
        String expected = "null";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_Collection() {
        LinkedList<Object> input = new LinkedList<Object>();
        input.add(1);
        input.add("str");
        input.add(null);
        input.add(false);
        String actual = serializer.serialize(input);
        String expected = "[1,\"str\",null,false]";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_CollectionInCollection() {
        LinkedList<Object> input = new LinkedList<Object>();
        input.add(1);
        input.add("str");
        input.add(null);
        ArrayList<Object> arrayList = new ArrayList<Object>();
        arrayList.add(777);
        arrayList.add(123);
        arrayList.add("str2");
        input.add(arrayList);
        input.add(false);

        String actual = serializer.serialize(input);
        String expected = "[1,\"str\",null,[777,123,\"str2\"],false]";

        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_Map() {
        Map<String, Object> input = new LinkedHashMap<String, Object>();
        input.put("firstName", "Yuriy");
        input.put("lastName", "Sych");
        input.put("age", 19);
        List<String> brothers = new LinkedList<String>();
        brothers.add("Sergiy");
        brothers.add("Oleg");
        input.put("brothers", brothers);

        String actual = serializer.serialize(input);
        String expected = "{" + "\"firstName\":\"Yuriy\"," + "\"lastName\":\"Sych\"," + "\"age\":19,"
                + "\"brothers\":[\"Sergiy\",\"Oleg\"]" + "}";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_ObjectArray() {
        String[] input = new String[] { "str1", "str2", "str3" };
        String actual = serializer.serialize(input);
        String expected = "[\"str1\",\"str2\",\"str3\"]";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_PrimitiveArray() {
        int[] input = new int[] { 1, 2, 3 };
        String actual = serializer.serialize(input);
        String expected = "[1,2,3]";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_SimplePojo() {
        class InputClass {
            public int a = 2;
            public int b = 3;

            private int c = 4;
        }
        InputClass input = new InputClass();

        String actual = serializer.serialize(input);
        String expected = "{\"a\":2,\"b\":3}";
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialize_AnnotatedPojo() {
        class InputClass {
            @JsonIgnore
            public int a = 2;
            public int b = 3;
            public int c = 4;

            @JsonProperty(name = "c")
            private int d = 5;

            @JsonProperty
            private int e = 6;
        }

        InputClass input = new InputClass();

        String actual = serializer.serialize(input);
        String expected = "{\"b\":3,\"c\":5,\"e\":6}";
        assertEquals(expected, actual);
    }
    
    @Test
    public void testSerialize_InheritedPojo() {
        class Parent {
            public int namedVar = 2;
            
            @JsonIgnore
            public int b = 3;
            
            @JsonProperty
            private String var = "private";
        }

        class Child extends Parent {
            
            public int var = 3;
            
            @JsonProperty(name="namedVar")
            private int a = 123;
        }
        
        Child input = new Child();

        String actual = serializer.serialize(input);
        String expected = "{\"namedVar\":123,\"var\":3}";
        
        assertEquals(expected, actual);
    }
    
    @Test(expected = IllegalStateException.class, timeout=1000)
    public void testSerialize_IllegalStateException() {
        Map<String, Object> input = new LinkedHashMap<String, Object>();
        input.put("firstName", "Yuriy");
        input.put("", "Sych");
        
        String result = serializer.serialize(input);
        
        System.out.println(result);
    }

}
