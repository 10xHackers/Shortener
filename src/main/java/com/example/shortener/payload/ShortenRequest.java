package com.example.shortener.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShortenRequest {
    private String longUrl;
    private Long clientId;
}
