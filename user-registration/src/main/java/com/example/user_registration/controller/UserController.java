package com.example.user_registration.controller;

import com.example.user_registration.model.User;
import com.example.user_registration.service.UserService;
import com.example.user_registration.service.PDFGenerationService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PDFGenerationService pdfGenerationService;

    public UserController(UserService userService, PDFGenerationService pdfGenerationService) {
        this.userService = userService;
        this.pdfGenerationService = pdfGenerationService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registrationForm";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users/register?success";
    }

    @GetMapping("/view")
    public String viewUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "viewUsers";
    }

    @GetMapping("/download-pdf")
    public ResponseEntity<InputStreamResource> downloadUsersPDF() {
        List<User> users = userService.getAllUsers();
        ByteArrayInputStream pdfStream = pdfGenerationService.generateUsersPDF(users);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}