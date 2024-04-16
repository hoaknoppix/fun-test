package com.example.taxisvc.exception;

public class JsonParseException extends RuntimeException {

  public JsonParseException(Exception exception) {
    super(exception);
  }

}
