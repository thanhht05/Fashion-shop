package com.thanh.fashion_shop.domain.respone;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestRespone<T> {
    private int statusCode;
    private String error;
    private Object message;
    private T data;
}
