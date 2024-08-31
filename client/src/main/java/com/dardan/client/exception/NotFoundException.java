package com.dardan.client.exception;

import java.util.function.Supplier;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public static Supplier<NotFoundException> supplier(String message) {
        return () -> { throw new NotFoundException(message); };
    }

}
