package chat;

import java.io.Serializable;

public class Person implements Serializable { 
	private String nickName;
	private int id;
	
	public Person(String nickName) { 
	    this.nickName = nickName;
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
        public void setId(int nb) {
            this.id = nb;
	}
}  