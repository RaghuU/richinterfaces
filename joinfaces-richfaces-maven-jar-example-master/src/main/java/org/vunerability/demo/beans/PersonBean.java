package org.vunerability.demo.beans;

import java.io.Serializable;

import javax.inject.Named;

@Named
public class PersonBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
