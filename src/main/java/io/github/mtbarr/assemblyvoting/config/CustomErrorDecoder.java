package io.github.mtbarr.assemblyvoting.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import io.github.mtbarr.assemblyvoting.exception.CommonException;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {
  @Override
  public Exception decode(String methodKey, Response response) {
    return new CommonException(response.reason(), HttpStatus.valueOf(response.status()));
  }
}