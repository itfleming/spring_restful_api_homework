package com.galvanize.intergration;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.Application;
import com.galvanize.Room;
import com.galvanize.RoomsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
public class RoomsApiIntegrationTest{
  RestTemplate restTemplate = new TestRestTemplate();
  final String BASE_URL = "http://localhost:8080/rooms";

  @Autowired
  RoomsRepository roomsRepository;
  
  @Before
  public void setUp(){
	  roomsRepository.deleteAll();
  }
  @Test
  public void postResponseWithStatusCodeCreated(){
	  Room room = new Room();
      room.setName("Ruby");
      room.setCapacity(12);
      room.setHavingVc(true);

    ResponseEntity<Room> response = restTemplate.postForEntity(
      BASE_URL, room, Room.class);

    assertThat(response.getStatusCode(), equalTo(CREATED));
  }
  
  @Test
  public void postResponseWithCreatedRoom(){
	  Room room = new Room();
      room.setName("Ruby");
      room.setCapacity(12);
      room.setHavingVc(true);
      
      ResponseEntity<Room> response = restTemplate.postForEntity(BASE_URL, room, Room.class);
    		  
      Room newRoom = response.getBody();
      assertThat(newRoom, notNullValue());
  }
  
  
  
  @Test
  public void addsTheInstanceToTheDatabase(){
	  Room room = new Room();
      room.setName("Ruby");
      room.setCapacity(12);
      room.setHavingVc(false);
      
      restTemplate.postForEntity(BASE_URL, room, Room.class);
      assertThat(roomsRepository.count(), equalTo(1L));
  }
  
  @Test
  public void postRespondsWithStatusCodeUnprocessableEntityForRoomWithEmptyName(){
	  Room room = new Room();
	  room.setName("");
	  room.setCapacity(12);
	  room.setHavingVc(false);
	  
	  ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL, room, String.class);
	  
	  assertThat(response.getStatusCode(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY));
  }
  
  @Test
  public void postRespondsWithDetailsOfValidError() throws Exception {
	  Room room = new Room();
	  room.setName("");
	  room.setCapacity(12);
	  room.setHavingVc(true);
	  
	  ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL, room, String.class);
	  
	  ObjectMapper objectMapper = new ObjectMapper();
	    Map<String, Object> error = objectMapper.readValue(response.getBody(),
	            new TypeReference<Map<String, Object>>() {});
	  
	  assertThat(error.get("reason"), equalTo("Unprocessable Entity"));
	  assertThat(error.get("error"), equalTo(singletonList("The name must not be empty!")));
  }

}
