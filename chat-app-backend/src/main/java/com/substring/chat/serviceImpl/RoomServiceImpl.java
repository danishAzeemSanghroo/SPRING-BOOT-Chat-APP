package com.substring.chat.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.substring.chat.entities.Message;
import com.substring.chat.entities.Room;
import com.substring.chat.repositories.RoomRepository;
import com.substring.chat.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository roomRepository;
	
	@Override
	public boolean createRoom(String roomId) {
		boolean flag = false;
		if(roomRepository.findByRoomId(roomId)==null) {
			
			Room room = new Room();
			room.setRoomId(roomId);
		    Room save = roomRepository.save(room);
			
			flag = true;
			
		}
		
		return flag;
	}

}
