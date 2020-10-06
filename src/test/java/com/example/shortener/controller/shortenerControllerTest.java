package com.example.shortener.controller;

import com.example.shortener.payload.ShortenRequest;
import com.example.shortener.service.ShortenerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(value = ShortenerController.class)
public class shortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShortenerService shortenerService;

    String shortUrl = "http://www.short.com/b";
    String longUrl = "http://google.com";
    String getOriginalRequest = "{\"shortUrl\":\"http://www.short.com/b\"}";
    String shortenRequest = "{\"longUrl\":\"http://google.com\", \"clientId\":1}";

    @Test
    public void getShortenedURL() throws Exception {
        Mockito.when(shortenerService.getShortenedURL(Mockito.anyString(), Mockito.anyLong()))
                .thenReturn(shortUrl);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/shorten")
                .content(shortenRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(shortUrl, response.getContentAsString());
    }

    @Test
    public void getOriginalURL() throws Exception {
        Mockito.when(shortenerService.getOriginalURL(Mockito.anyString()))
                .thenReturn(longUrl);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/original")
                .content(shortUrl)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(longUrl, response.getContentAsString());
    }

    @Test
    public void getHits() throws Exception {
        Mockito.when(shortenerService.getHitCount(Mockito.anyString()))
                .thenReturn(0L);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hits")
                .content(shortUrl)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(String.valueOf(0L), response.getContentAsString());
    }
}
