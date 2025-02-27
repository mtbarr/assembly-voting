package io.github.mtbarr.assemblyvoting.integration.client;


import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient( value = "UserInfoClient", url = "${user-info.url}", configuration = ClientConfiguration.class )
@Headers( {"Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8"} )
public interface CpfStatusClient {
}
