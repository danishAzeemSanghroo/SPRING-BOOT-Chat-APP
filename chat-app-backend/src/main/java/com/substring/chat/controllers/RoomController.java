package com.substring.chat.controllers;

import java.util.List;

import javax.swing.RepaintManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.substring.chat.entities.Message;
import com.substring.chat.entities.Room;
import com.substring.chat.repositories.RoomRepository;
import com.substring.chat.service.RoomService;

@RestController
@RequestMapping("api/v1/rooms")
@CrossOrigin("https://localhost:3000")
public class RoomController {
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private RoomRepository roomRepository;

	//create room
	@PostMapping
	public ResponseEntity<?> createRoom(@RequestBody String roomId) {
		boolean flag = false;
		
		if(!roomId.isEmpty())
		{
			flag =  roomService.createRoom(roomId);
		}
		
		if(flag) {
			Room room = roomRepository.findByRoomId(roomId);
			return ResponseEntity.status(HttpStatus.CREATED).body(room);
		}else {
			return ResponseEntity.badRequest().body("Room Already Exists");
		}
		
		
	}
	
	//get room join
	@GetMapping("/{roomId}")
	public ResponseEntity<?> joinRoom(@PathVariable String roomId){
		
		Room room = roomRepository.findByRoomId(roomId);
		
		if(room==null) {
			return ResponseEntity.badRequest().body("User not Found");
		}
		
		return ResponseEntity.ok(room);
	}
	
	
	//get  messages
	@GetMapping("/{roomId}/messages")
	public ResponseEntity<?> getMessages(
			@PathVariable String roomId,
			@RequestParam(value = "page",defaultValue = "0",required  = false) int page,
			@RequestParam(value = "size",defaultValue = "20",required = false) int size
			){
		
		Room room = roomRepository.findByRoomId(roomId);
		if(room ==null) {
			return ResponseEntity.badRequest().build();
		}

		List<Message> messages = room.getMessages();
		//pagination
		int start = Math.max(0, messages.size() - (page+1)*size);
		int end= Math.min(messages.size(), messages.size() + start);
		messages.subList(start, end);
		
		return ResponseEntity.ok(messages);
		
	}
	
	
}
