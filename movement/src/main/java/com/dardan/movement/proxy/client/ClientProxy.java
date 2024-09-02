package com.dardan.movement.proxy.client;

import com.dardan.movement.dto.ResponseDto;
import com.dardan.movement.proxy.client.dto.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="client-service")
public interface ClientProxy {

    @GetMapping("/v1/client/{id}")
    ResponseDto<ClientDto> getClient(@PathVariable("id") Integer id);

}
