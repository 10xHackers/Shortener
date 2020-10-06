package com.example.shortener.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Url {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;


    private Long clientId;
    private Long hits = 0L;
    private String longUrl;

    @Column(unique=true)
    private String shortUrl;
}
