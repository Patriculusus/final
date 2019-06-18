package main.jsonwriter;


import main.exception.JsonWriterIOException;

import java.io.IOException;
import java.io.Writer;

public class JsonWriter {

    protected static final char OBJ_BEGIN = '{';
    protected static final char OBJ_END = '}';
    protected static final char ARR_BEGIN = '[';
    protected static final char ARR_END = ']';
    protected static final char SEPARATOR = ',';
    protected static final char PROP_SEPARATOR = ':';
    protected static final char STR_SEPARATOR = '\"';
    protected static final String NULL = "null";

    protected Writer writer;

    protected boolean separatorLast;

    public JsonWriter(Writer writer) {
        this.writer = writer;
    }

    public void writeObjectBegin() {
        try {
            writer.write(OBJ_BEGIN);
        } catch (IOException e) {
            throw new JsonWriterIOException();
        }


    }

    public void writeObjectEnd() {
        try {
            writer.write(OBJ_END);
        } catch (IOException e) {
            throw new JsonWriterIOException();
        }
    }

    public void writeArrayBegin() {
        try {
            writer.write(ARR_BEGIN);
        } catch (IOException e) {
            throw new JsonWriterIOException();
        }
    }

    public void writeArrayEnd() {
        try {
            writer.write(ARR_END);
        } catch (IOException e) {
            throw new JsonWriterIOException();
        }

    }

    public void writeString(String str) {
        try {
            writer.append(STR_SEPARATOR).append(str).append(STR_SEPARATOR);
        } catch (IOException e) {
            throw new JsonWriterIOException();
        }
    }

    public void writeNumber(Number number) {
        try {
            writer.write(number.toString());
        } catch (IOException e) {
            throw new JsonWriterIOException();
        }
    }

    public void writeSeparator() {
        try {
            writer.write(SEPARATOR);
        } catch (IOException e) {
            throw new JsonWriterIOException();
        }
    }

    public void writePropertySeparator() {
        try {
            writer.write(PROP_SEPARATOR);
        } catch (IOException e) {
            throw new JsonWriterIOException();
        }
    }

    public void writeBoolean(Boolean val) {
        try {
            writer.write(val.toString());
        } catch (IOException e) {
            throw new JsonWriterIOException();
        }

    }

    public void writeNull() {
        try {
            writer.write(NULL);
        } catch (IOException e) {
            throw new JsonWriterIOException();
        }
    }

    public void flush() {
        try {
            writer.flush();
        } catch (IOException e) {
            throw new JsonWriterIOException();
        }
    }

}
