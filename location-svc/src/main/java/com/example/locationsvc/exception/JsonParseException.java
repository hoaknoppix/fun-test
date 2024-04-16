package com.example.locationsvc.exception;

public class JsonParseException extends RuntimeException {

  public JsonParseException(Exception exception) {
    super(exception);
  }

}
