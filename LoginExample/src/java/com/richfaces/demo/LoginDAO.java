/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.richfaces.demo;

/**
 *
 * @author admin
 */
public class LoginDAO {
    
  public static boolean validate(String user, String password) {
      if("kumar".equals(user) && "Kumar123".equals(password)){
         return true; 
      }else{
          return false;
	}
}  
}
