package main.exception;

public class JsonWriterIOException extends RuntimeException {

    /**
    * 
    */
    private static final long serialVersionUID = 1876553739985673725L;

    public JsonWriterIOException() {

    }

    public JsonWriterIOException(String str) {
        super(str);
    }

    public JsonWriterIOException(Exception e) {
        super(e);
    }
}
