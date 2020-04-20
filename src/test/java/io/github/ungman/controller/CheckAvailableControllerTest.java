package io.github.ungman.controller;


import io.github.ungman.AbstractTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.net.HttpURLConnection;

import static org.assertj.core.api.Assertions.assertThat;


public class CheckAvailableControllerTest extends AbstractTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void isAvailable() throws Exception {
        String uri = "/api/available";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertThat(status).isEqualTo(HttpURLConnection.HTTP_OK);
        String content = mvcResult.getResponse().getContentAsString();
        Boolean isAvailableServer = super.mapFromJson(content, Boolean.class);
        assertThat(isAvailableServer).isTrue();
    }
}