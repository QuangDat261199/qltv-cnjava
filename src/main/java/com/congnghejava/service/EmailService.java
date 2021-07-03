package com.congnghejava.service;


import java.util.Map;

public interface EmailService {
    boolean sendEmailForgotPassword(String to, Map<String, Object> model);
}
