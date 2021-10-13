package com.oshovskii.springSecurityApp.controllers;

import com.oshovskii.springSecurityApp.exceptions_handling.IncorrectFieldException;
import com.oshovskii.springSecurityApp.payload.request.MessageRequest;
import com.oshovskii.springSecurityApp.payload.response.MessageResponse;
import com.oshovskii.springSecurityApp.payload.response.MessagesPageDto;
import com.oshovskii.springSecurityApp.services.MessageService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void saveMessage(@RequestBody MessageRequest messageRequest, Principal principal) {
        messageService.saveMessage(messageRequest, principal.getName());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public MessageResponse findMessageById(@PathVariable Long id) {
        return messageService.findMessageById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public MessagesPageDto getAllMessages(@RequestParam(defaultValue = "1", name = "p") Integer page,
                                          @RequestParam(defaultValue = "10", name = "s") Integer size) {
        if (page < 1 || size < 1) {
            throw new IncorrectFieldException("Value 'p' and 's' are incorrect");
        }
        return messageService.findAllMessages(page, size);
    }
}
