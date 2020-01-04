package com.syphan.common.rest.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenApiWithDataResponse<T> extends OpenApiBaseResponse {
    T data;

    public OpenApiWithDataResponse(T data) {
        this.data = data;
    }

    OpenApiWithDataResponse() {
    }
}
