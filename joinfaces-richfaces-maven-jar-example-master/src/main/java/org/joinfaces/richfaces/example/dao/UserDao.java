package org.joinfaces.richfaces.example.dao;

import java.util.List;

public interface UserDao {

	String findByName(String name);
	
	List<User> findAll();

}