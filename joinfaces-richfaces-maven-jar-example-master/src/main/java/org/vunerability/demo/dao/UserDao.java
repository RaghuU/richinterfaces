package org.vunerability.demo.dao;

import java.util.List;

public interface UserDao {

	List<User> findByName(String name);
	
	List<User> findAll();

}