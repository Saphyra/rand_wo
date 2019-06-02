package com.github.saphyra.common.testcomponent;

import com.github.saphyra.randwo.common.ObjectMapperDelegator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@TestComponent
@RequiredArgsConstructor
public class MockMvcWrapper {
    private final MockMvc mockMvc;
    private final ObjectMapperDelegator objectMapperDelegator;

    public MockHttpServletResponse deleteRequest(String url, Object requestBody) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = delete(url)
            .content(objectMapperDelegator.writeValueAsString(requestBody));

        return sendRequest(requestBuilder);
    }

    public MockHttpServletResponse postRequest(String url, Object requestBody, Object... pathVariables) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post(url, pathVariables)
            .content(objectMapperDelegator.writeValueAsString(requestBody));

        return sendRequest(requestBuilder);
    }

    public MockHttpServletResponse putRequest(String url, Object requestBody) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = put(url)
            .content(objectMapperDelegator.writeValueAsString(requestBody));

        return sendRequest(requestBuilder);
    }

    private MockHttpServletResponse sendRequest(MockHttpServletRequestBuilder requestBuilder) throws Exception {
        requestBuilder.accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);
        return mockMvc.perform(requestBuilder)
            .andReturn()
            .getResponse();
    }
}
