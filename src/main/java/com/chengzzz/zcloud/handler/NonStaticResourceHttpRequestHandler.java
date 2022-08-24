package com.chengzzz.zcloud.handler;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 统一资源处理
 *
 * @author Yet
 * @date 2022/08/24 21:43
 **/
@Component
public class NonStaticResourceHttpRequestHandler extends ResourceHttpRequestHandler {

    public static final String ATTR_FILE = "NON-STATIC-FILE";

    @Override
    protected Resource getResource(HttpServletRequest request) throws IOException {
        String filePath = (String) request.getAttribute(ATTR_FILE);
        return new FileSystemResource(filePath);
    }
}
