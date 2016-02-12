package chat;

import java.io.Serializable;

public class Person implements Serializable { 
	private String nickName;
	private int id;
	private static int idGen = 0;
	
	public Person(String nickName) { 
	    this.nickName = nickName;
	    this.id = idGen++;
	} 
	
	public String getNickName() {
		return this.nickName;
	} 
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getId() {
		return this.id;
	}
}  