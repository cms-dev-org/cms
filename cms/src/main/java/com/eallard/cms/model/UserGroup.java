package com.eallard.cms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 用户组对象，用户和组关系
 * @author renzw
 *
 */
@Entity
@Table(name = "cms_user_group")
public class UserGroup {
	
	private int id;
	
	private User user;
	
	private Group group;

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "group_id")
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
	public static byte[] HexString2Bytes(String src){  
        byte[] ret = new byte[src.length()/2];  
        byte[] tmp = src.getBytes();  
        for(int i=0; i< tmp.length/2; i++){  
          ret[i] = uniteBytes(tmp[i*2], tmp[i*2+1]);  
        }  
        return ret;  
      }  
	 public static byte uniteBytes(byte src0, byte src1) {  
		    byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();  
		    _b0 = (byte)(_b0 << 4);  
		    byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();  
		    byte ret = (byte)(_b0 ^ _b1);  
		    return ret;  
		    } 
	public static void main(String[] args) {
		System.out.println(String.valueOf((int)Math.floor(Math.random() * 10)));
	}
}
