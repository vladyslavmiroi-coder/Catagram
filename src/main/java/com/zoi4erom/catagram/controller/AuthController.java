package com.zoi4erom.catagram.controller;

import com.zoi4erom.catagram.config.CustomUserDetails;
import com.zoi4erom.catagram.config.CustomUserDetailsService;
import com.zoi4erom.catagram.dto.user.UserCreateDTO;
import com.zoi4erom.catagram.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthenticationManager authenticationManager;
        private final UserService userService;

        @PostMapping("/register")
        public ResponseEntity<String> register(
                @RequestBody UserCreateDTO request
        ) {

                userService.create(request);

                return ResponseEntity.ok("Registered");
        }

        @PostMapping("/login")
        public ResponseEntity<String> login(
                @RequestBody UserCreateDTO request,
                HttpServletRequest httpRequest
        ) {

                Authentication authentication =
                        authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                        request.username(),
                                        request.password()
                                )
                        );

                SecurityContext context =
                        SecurityContextHolder.createEmptyContext();

                context.setAuthentication(authentication);

                SecurityContextHolder.setContext(context);

                HttpSession session = httpRequest.getSession(true);

                session.setAttribute(
                        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                        context
                );

                return ResponseEntity.ok("Logged in");
        }

        @PostMapping("/logout")
        public ResponseEntity<String> logout(
                HttpServletRequest request
        ) {

                HttpSession session = request.getSession(false);

                if (session != null) {
                        session.invalidate();
                }

                SecurityContextHolder.clearContext();

                return ResponseEntity.ok("Logged out");
        }

        @GetMapping("/me")
        public ResponseEntity<?> me(@AuthenticationPrincipal CustomUserDetails userDetails) {
                if (userDetails == null) {
                        return ResponseEntity.status(401).build();
                }

                return ResponseEntity.ok(
                        userService.findByUsername(userDetails.getUsername())
                );
        }
}