package com.example.shortener.controller;

import com.example.shortener.exception.BadRequestException;
import com.example.shortener.payload.ShortenRequest;
import com.example.shortener.service.ShortenerService;
import com.example.shortener.util.URLValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Null;

@RestController
public class ShortenerController {

    @Autowired
    private ShortenerService shortenerService;

    @GetMapping("/shorten")
    public ResponseEntity<?> getShortenedURL(@RequestBody ShortenRequest shortenRequest) throws Exception {
        if (shortenRequest.getClientId() == null) {
            throw new BadRequestException("Please enter a Client Id");
        }

        if (!URLValidator.INSTANCE.validateURL(shortenRequest.getLongUrl())) {
            throw new BadRequestException("Please enter a valid URL");
        }

        return ResponseEntity.ok(shortenerService.getShortenedURL(shortenRequest.getLongUrl(), shortenRequest.getClientId()));
    }

    @GetMapping("/original")
    public ResponseEntity<?> getOriginalURL(@RequestBody String shortUrl) throws Exception {
        if (!URLValidator.INSTANCE.validateURL(shortUrl)) {
            throw new BadRequestException("Please enter a valid URL");
        }
        return ResponseEntity.ok(shortenerService.getOriginalURL(shortUrl));
    }

    @GetMapping("/hits")
    public ResponseEntity<?> getHitCount(@RequestBody String shortUrl) throws Exception {
        if (!URLValidator.INSTANCE.validateURL(shortUrl)) {
            throw new BadRequestException("Please enter a valid URL");
        }

        return ResponseEntity.ok(shortenerService.getHitCount(shortUrl));
    }
}
