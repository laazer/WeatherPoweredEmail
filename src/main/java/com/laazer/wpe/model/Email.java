package com.laazer.wpe.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Laazer
 */
@Data
@Builder
public class Email {
    private String sender;
    private List<String> recipients;
    private String subject;
    private String text;
}