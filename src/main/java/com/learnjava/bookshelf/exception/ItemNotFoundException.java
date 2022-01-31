package com.learnjava.bookshelf.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String entity, String field, String value) {
        super(String.format("No item found for %s with field %s value = %s", entity, field, value));
    }

    public ItemNotFoundException(String entity, String field, int value) {
        super(String.format("No item found for %s with field %s value = %s", entity, field, value));
    }
}
