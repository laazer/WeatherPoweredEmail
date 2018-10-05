package com.laazer.wpe.controller;

import com.laazer.wpe.dao.IZipCodeAccessor;
import com.laazer.wpe.db.UserRepository;
import com.laazer.wpe.internal.exception.DuplicateEntityException;
import com.laazer.wpe.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Laazer
 */
@Slf4j
@Controller
public class SignUpController {

    @Autowired
    private IZipCodeAccessor zipCodeAccessor;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signUp")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid @ModelAttribute("user") User user) throws DuplicateEntityException {
        final String zipCode = zipCodeAccessor.getZipCodeFromCity(user.getLocation());
        user.setZipCode(zipCode);
        if (userRepository.existsById(user.getEmail())) {
            throw new DuplicateEntityException("Email {0} already saved", user.getEmail());
        }
        userRepository.save(user);
        return "result";
    }

}
