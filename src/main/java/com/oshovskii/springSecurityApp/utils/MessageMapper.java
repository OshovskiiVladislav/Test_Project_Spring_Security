package com.oshovskii.springSecurityApp.utils;

import com.oshovskii.springSecurityApp.models.Message;
import com.oshovskii.springSecurityApp.models.User;
import com.oshovskii.springSecurityApp.payload.request.MessageRequest;
import com.oshovskii.springSecurityApp.payload.response.MessageResponse;
import com.oshovskii.springSecurityApp.payload.response.MessagesPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MessageMapper {

    public Message massageRequestToMessage(MessageRequest messageRequest, User user) {
        Message message = new Message();
        message.setText(messageRequest.getText());
        message.setUser(user);
        return message;
    }

    public MessageResponse messageToMessageResponse(Message message) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setId(message.getId());
        messageResponse.setText(message.getText());
        messageResponse.setUsername(message.getUser().getUsername());
        messageResponse.setCreatedAt(message.getCreatedAt());
        return messageResponse;
    }

    public MessagesPageDto pageToMessagesPage(Page<Message> page) {
        MessagesPageDto messagesPageDto = new MessagesPageDto();
        messagesPageDto.setPage(page.getPageable().getPageNumber() + 1);
        messagesPageDto.setMessagesList(page.getContent().stream().map(this::messageToMessageResponse).collect(Collectors.toList()));
        messagesPageDto.setTotalMessages((int) page.getTotalElements());
        messagesPageDto.setPageSize(page.getSize());
        return messagesPageDto;
    }
}
