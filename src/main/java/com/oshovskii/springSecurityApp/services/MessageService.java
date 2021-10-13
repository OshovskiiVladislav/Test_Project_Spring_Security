package com.oshovskii.springSecurityApp.services;

import com.oshovskii.springSecurityApp.exceptions_handling.ResourceNotFoundException;
import com.oshovskii.springSecurityApp.payload.request.MessageRequest;
import com.oshovskii.springSecurityApp.payload.response.MessageResponse;
import com.oshovskii.springSecurityApp.payload.response.MessagesPageDto;
import com.oshovskii.springSecurityApp.repository.MessageRepository;
import com.oshovskii.springSecurityApp.utils.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserDetailsServiceImpl userDetailsService;

    public void saveMessage(MessageRequest messageRequest, String username){
        messageRepository.save(messageMapper.massageRequestToMessage(messageRequest, userDetailsService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"))));
    }

    public MessageResponse findMessageById(Long id){
        return messageMapper.messageToMessageResponse(messageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Message not found")));
    }

    public MessagesPageDto findAllMessages(Integer page, Integer pageSize) {
        return messageMapper.pageToMessagesPage(messageRepository.findAll(PageRequest.of(page - 1, pageSize)));
    }
}
