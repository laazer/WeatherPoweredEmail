package com.laazer.wpe.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 *
 *
 * Created by laazer
 */
@Data
public class User {

    @Id
    private String email;
    private String location;
    private String zipCode;
    private String timeZone;
}
