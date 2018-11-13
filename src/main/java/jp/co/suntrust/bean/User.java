package jp.co.suntrust.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private String userID;

	@Column(name = "NAME")
	private String name;

	@Column(name = "BIRTH")
	private String birth;

	@Column(name = "AGE")
	private int age;

	@Column(name = "SEX")
	private int sex;

	@Column(name = "NATIVE_PREF")
	private int nativePref;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getNativePref() {
		return nativePref;
	}

	public void setNativePref(int nativePref) {
		this.nativePref = nativePref;
	}

}
