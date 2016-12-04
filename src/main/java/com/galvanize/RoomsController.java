package com.galvanize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomsController {
	@Autowired
	private RoomsRepository roomsRepository;
	
	@RequestMapping(value="/rooms", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Room createRoom(@RequestBody Room room){
		Room newRoom = new Room();
		newRoom.setName(room.getName());
		newRoom.setCapacity(room.getCapacity());
		newRoom.setHavingVc(room.getHavingVc());
		roomsRepository.save(newRoom);

		return newRoom;
	}

}