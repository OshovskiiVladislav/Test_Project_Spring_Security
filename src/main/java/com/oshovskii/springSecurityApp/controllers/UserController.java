package com.oshovskii.springSecurityApp.controllers;

import com.oshovskii.springSecurityApp.payload.response.UserDto;
import com.oshovskii.springSecurityApp.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;
    private final ModelMapper modelMapper;

    @GetMapping("/current")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public UserDto findUserById(Principal principal) {
        return modelMapper.map(userDetailsService.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found!")), UserDto.class);
    }
}
