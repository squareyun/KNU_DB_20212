package profile;

import java.sql.Timestamp;

public class ProfileDTO{
	
	
	String Fname;
	String Lname;
	int PRid;
	String Email;
	String Password;
	int Restrction;
	String Phone_num;
	Timestamp Create_date;
	String Country;
	String State;
	String City;
	String Street;
	String Nickname;
	int Role_id;
	
	
	public String getFname() {
		return Fname;
	}
	public void setFname(String fname) {
		Fname = fname;
	}
	public String getLname() {
		return Lname;
	}
	public void setLname(String lname) {
		Lname = lname;
	}
	public int getPRid() {
		return PRid;
	}
	public void setPRid(int pRid) {
		PRid = pRid;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public int getRestrction() {
		return Restrction;
	}
	public void setRestrction(int restrction) {
		Restrction = restrction;
	}
	public String getPhone_num() {
		return Phone_num;
	}
	public void setPhone_num(String phone_num) {
		Phone_num = phone_num;
	}
	public Timestamp getCreate_date() {
		return Create_date;
	}
	public void setCreate_date(Timestamp create_date) {
		Create_date = create_date;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getNickname() {
		return Nickname;
	}
	public void setNickname(String nickname) {
		Nickname = nickname;
	}
	public int getRole_id() {
		return Role_id;
	}
	public void setRole_id(int role_id) {
		Role_id = role_id;
	}
}