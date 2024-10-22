package com.technova.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@RestController
public class MappingController {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @GetMapping("/mappings")
    public String listAllMappings() {
        StringBuilder mappings = new StringBuilder();
        requestMappingHandlerMapping.getHandlerMethods().forEach((requestMappingInfo, handlerMethod) -> {
            mappings.append("Mapping: ").append(requestMappingInfo)
                    .append(" -> Method: ").append(handlerMethod).append("<br>");
        });
        return mappings.toString();
    }
}
