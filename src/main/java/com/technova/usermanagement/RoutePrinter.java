package com.technova.usermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import jakarta.annotation.PostConstruct;

@Component
public class RoutePrinter {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @PostConstruct
    public void printAllRoutes() {
        requestMappingHandlerMapping.getHandlerMethods().forEach((requestMappingInfo, handlerMethod) -> {
            System.out.println("Mapping: " + requestMappingInfo + " -> Method: " + handlerMethod);
        });
    }
}
