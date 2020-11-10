package org.joinfaces.richfaces.example.dao;

import java.util.List;

public interface UserDao {

	List<User> findByName(String name);
	
	List<User> findAll();

}