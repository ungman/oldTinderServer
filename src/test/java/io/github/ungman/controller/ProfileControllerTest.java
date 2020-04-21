package io.github.ungman.controller;

import io.github.ungman.AbstractTest;
import io.github.ungman.domain.Profile;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.Assert.*;

public class ProfileControllerTest extends AbstractTest {
    String URL;

    @Before
    public void setUp() {
        super.setUp();
        URL = "/api/profile";
    }

    @SneakyThrows
    @Test
    public void getList() {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

    }

    @Test
    public void getOne() {
    }

    @Test
    public void create() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void showCurrent() {
    }

    @Test
    public void shownNext() {
    }

    @Test
    public void testDelete() {
    }

    @Test
    public void editProfile() {
    }
}