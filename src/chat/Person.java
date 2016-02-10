package chat;

import java.io.Serializable;

public class Person implements Serializable { 
	private String nickName;  
	
	public Person(String nickName) { 
	    this.nickName = nickName; 
	} 
	
	public String getNickName() {
		return nickName;
	} 
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}  