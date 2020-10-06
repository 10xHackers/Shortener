package com.example.shortener.service;

import com.example.shortener.exception.ResourceNotFoundException;
import com.example.shortener.model.Url;
import com.example.shortener.repository.UrlRepository;
import com.example.shortener.util.IDConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ShortenerService {
    private UrlRepository urlRepository;
    private static long id = 1;

    public String getShortenedURL(String longUrl, Long clientId) {

        Url url = urlRepository.findByLongUrlAndClientId(longUrl, clientId);
        if (url == null) {
            url = new Url();
            url.setClientId(clientId);
            url.setLongUrl(longUrl);

            String uniqueId = IDConverter.INSTANCE.createUniqueID(id);
            id = id + 1;
            url.setShortUrl("http://www.short.com/" + uniqueId);
            urlRepository.save(url);
        }

        return url.getShortUrl();
    }

    public String getOriginalURL(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new ResourceNotFoundException("Original Url", "short url", shortUrl));
        url.setHits(url.getHits()+1);
        urlRepository.save(url);
        return url.getLongUrl();
        //TODO: Null case
    }

    public Long getHitCount(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new ResourceNotFoundException("Original Url", "short url", shortUrl));
        return url.getHits();
        //TODO: Null case
    }
}
