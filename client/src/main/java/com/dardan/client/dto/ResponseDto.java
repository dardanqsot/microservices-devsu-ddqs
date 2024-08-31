package com.dardan.client.dto;

import com.dardan.client.util.Constants;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    protected String mensaje;
    protected T data;

    public static <T> ResponseDto<T> successResponse(T data) {
        return ResponseDto.<T>builder().data(data).mensaje(Constants.OPERATION_SUCCEED).build();
    }
}
