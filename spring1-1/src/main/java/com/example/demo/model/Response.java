package com.example.demo.model;

import org.springframework.stereotype.Component;

@Component
public class Response {
	private int error;	// 0: success
	private String mesg;
	private int insertId;
	
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public String getMesg() {
		return mesg;
	}
	public void setMesg(String mesg) {
		this.mesg = mesg;
	}
	public int getInsertId() {
		return insertId;
	}
	public void setInsertId(int insertId) {
		this.insertId = insertId;
	}
	
	
}