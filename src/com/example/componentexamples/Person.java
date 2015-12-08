package com.example.componentexamples;

public class Person {
	
	private String name;
	private Integer born;

	public Person(String name, Integer born) {
		super();
		this.name = name;
		this.born = born;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBorn() {
		return born;
	}

	public void setBorn(Integer born) {
		this.born = born;
	}

	public Person() {
		// TODO Auto-generated constructor stub
	
	}

}
