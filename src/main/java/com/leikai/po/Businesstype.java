package com.leikai.po;

// default package
// Generated Oct 19, 2013 5:02:47 PM by Hibernate Tools 3.4.0.CR1

/**
 * Businesstype generated by hbm2java
 */
public class Businesstype implements java.io.Serializable {

	private Integer id;
	private String type;

	public Businesstype() {
	}

	public Businesstype(String type) {
		this.type = type;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		return this.getId() + ":" + this.getType();
	}
}
