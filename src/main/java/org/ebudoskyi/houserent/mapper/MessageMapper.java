package org.ebudoskyi.houserent.mapper;

import org.ebudoskyi.houserent.dto.MessageDTO;
import org.ebudoskyi.houserent.model.Message;

import java.util.List;
import java.util.stream.Collectors;

public class MessageMapper {

    public MessageDTO toDTO(Message message) {
        if (message == null) {
            return null;
        }
        MessageDTO messageDTO = new MessageDTO(
                message.getId(),
                message.getFromUser().getId(),
                message.getToUser().getId(),
                message.getContent(),
                message.getTimestamp()
        );
        return messageDTO;
    }
    public Message toEntity(MessageDTO messageDTO) {
        if (messageDTO == null) {
            return null;
        }
        Message message = new Message();
        message.setId(messageDTO.getId());
        message.setContent(messageDTO.getContent());
        message.setTimestamp(messageDTO.getTimestamp());
        return message;
    }

    public List<MessageDTO> toDTOlist(List<Message> messages) {
        if (messages == null) {
            return null;
        }
        return messages.stream().map(this::toDTO).collect(Collectors.toList());
    }
    public List<Message> toEntitylist(List<MessageDTO> messages) {
        if (messages == null) {
            return null;
        }
        return messages.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
