package com.galvanize;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomsRepository extends MongoRepository<Room, String>{

}
