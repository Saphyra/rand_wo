package com.github.saphyra.common.testcomponent;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.github.saphyra.randwo.common.ObjectMapperDelegator;
import lombok.RequiredArgsConstructor;

@TestComponent
@RequiredArgsConstructor
public class MockMvcWrapper {
    private final MockMvc mockMvc;
    private final ObjectMapperDelegator objectMapperDelegator;

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
