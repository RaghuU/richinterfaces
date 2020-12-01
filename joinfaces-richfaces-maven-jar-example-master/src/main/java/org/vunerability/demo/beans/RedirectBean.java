package org.vunerability.demo.beans;


import java.io.IOException;
import java.io.Serializable;

import javax.inject.Named;

@Named
public class RedirectBean implements Serializable {

	private static final long serialVersionUID = 1L;
	

	public void execute(String value) throws IOException {
		System.out.println("Output value....."+value);
	}
	
}