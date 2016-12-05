package com.galvanize.models;

import java.util.Set;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;

import com.galvanize.Room;

public class RoomTest {
	private Validator validator;
	
	@Before
	public void setUp(){
		this.validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	@Test
	public void ensureNameIsNotNull() {
		Room room = new Room();
		room.setName(null);
        room.setCapacity(12);
        room.setHavingVc(false);
        
        Set<ConstraintViolation<Room>> violations = validator.validate(room);
        
        assertThat(violations.size(), equalTo(1));
	}
	
	@Test
	public void ensureNameIsNotEmpty(){
		Room room = new Room();
		room.setName("");
		room.setCapacity(12);
		room.setHavingVc(true);
		
		Set<ConstraintViolation<Room>> violations = validator.validate(room);
		
		assertThat(violations.size(), equalTo(1));
	}

}
