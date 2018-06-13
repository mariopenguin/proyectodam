package com.dam.servicios.servicios.almacenamiento;
import com.dam.servicios.servicios.clases.Usuario;
import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service("FileSystemStorageService")
@FeignClient("rh-service")
public interface usuarioClient {

        @RequestMapping(method = RequestMethod.GET, value = "/empresa/all")
        String patata();

        @RequestMapping("/empresa/all")

        String greeting();

        @RequestMapping(method = RequestMethod.POST, value = "/empresa/add")
        @Headers("Content-Type: application/json")

        void json(Usuario usuario);


}


