package com.galvanize;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import static java.util.stream.Collectors.toList;


import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
public class RoomsController {
	@Autowired
	private RoomsRepository roomsRepository;
	
	@RequestMapping(value="/rooms", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Room createRoom(@Valid @RequestBody Room room){
		Room newRoom = new Room();
		newRoom.setName(room.getName());
		newRoom.setCapacity(room.getCapacity());
		newRoom.setHavingVc(room.getHavingVc());
		roomsRepository.save(newRoom);

		return newRoom;
	}

	@ExceptionHandler
	@ResponseStatus(value = UNPROCESSABLE_ENTITY)
	public Map<String, Object> handleException(MethodArgumentNotValidException e) {
		Map<String, Object> errorBody = new HashMap<>();
		errorBody.put("reason", UNPROCESSABLE_ENTITY.getReasonPhrase());
		errorBody.put("errors", e.getBindingResult().getFieldErrors().stream()
				.map(FieldError::getDefaultMessage)
				.collect(toList()));
		return errorBody;
	}

}
