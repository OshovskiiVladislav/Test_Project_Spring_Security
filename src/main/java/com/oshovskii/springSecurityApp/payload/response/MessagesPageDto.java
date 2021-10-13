package com.oshovskii.springSecurityApp.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MessagesPageDto {
    private int page;
    private int pageSize;
    private int totalMessages;
    private List<MessageResponse> messagesList;
}
