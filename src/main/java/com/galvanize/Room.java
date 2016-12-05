package com.galvanize;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

public class Room {
	@Id
	private String id;
	
	@NotNull
	@Size(min= 1)
    private String name;
    private int capacity;
    private boolean havingVc;

	public void setName(String name) {
		this.name = name;
		
	}
	public String getName() {
		return this.name ;
		
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
		
	}
	public int getCapacity() {
		return this.capacity ;
		
	}

	public void setHavingVc(boolean havingVc) {
		this.havingVc = havingVc;
		
	}
	
	public boolean getHavingVc(){
		return this.havingVc;
	}
	
	public void setId(String id) {
		this.id = id;
		
	}
	
	public String getId() {
		return this.id;
		
	}

}
