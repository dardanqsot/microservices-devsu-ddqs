package com.dardan.movement.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestApiException extends RuntimeException {

    private static final long serialVersionUID = 1905122041910261207L;

    public BadRequestApiException(String message) {
        super(message);
    }

}
