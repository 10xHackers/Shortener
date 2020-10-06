package com.example.shortener.repository;

import com.example.shortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortUrl(String shortUrl);
    Url findByLongUrlAndClientId(String longUrl, Long clientId);
}
