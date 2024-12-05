package com.substring.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import com.substring.chat.entities.Message;
import com.substring.chat.entities.Room;
import com.substring.chat.payload.MessageRequest;
import com.substring.chat.repositories.RoomRepository;

@Controller
@CrossOrigin("https://localhost:3000")
public class ChatController {

	
	@Autowired
	private RoomRepository roomRepository;
	
	//for sending and recievoing messages
	@MessageMapping("/sendMessage/{roomId}")
	@SendTo("/topic/room/{roomId}")
	public Message sendMessage(
			@DestinationVariable String roomId,
			@RequestBody MessageRequest messsageRequest
			) {
		
		Room room= roomRepository.findByRoomId(messsageRequest.getRoomId());
		
		Message message = new Message();
		message.setContent(messsageRequest.getContent());
		message.setSender(messsageRequest.getSender());
		
		if(room!=null) {
			room.getMessages().add(message);
			roomRepository.save(room);
			
		}else {
			throw new RuntimeException("Room not Found!!");
		}
		
		return message;
	}
	
}
