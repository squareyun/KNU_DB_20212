package profile;
import java.sql.Timestamp;

public class ProfileDTO {

	String Fname;
	String Lname;
	int PRid;
	String Email;
	String Password;
	int Restriction;
	String Phone_num;
	Timestamp Create_date;
	String Country;
	String State;
	String City;
	String Street;
	String Nickname;
	String Gender;
	String ProfileImg;
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
		return Restriction;
	}

	public void setRestrction(int restrction) {
		Restriction = restrction;
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

	public String getStreet() {
		return Street;
	}

	public void setStreet(String street) {
		Street = street;
	}

	public String getNickname() {
		return Nickname;
	}

	public void setNickname(String nickname) {
		Nickname = nickname;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getProfileImg() {
		return ProfileImg;
	}

	public void setProfileImg(String profileImg) {
		ProfileImg = profileImg;
	}

	public int getRole_id() {
		return Role_id;
	}

	public void setRole_id(int role_id) {
		Role_id = role_id;
	}

	@Override
	public String toString() {
		return "ProfileDTO [Fname=" + Fname + ", Lname=" + Lname + ", PRid=" + PRid + ", Email=" + Email + ", Password="
				+ Password + ", Restriction=" + Restriction + ", Phone_num=" + Phone_num + ", Create_date="
				+ Create_date + ", Country=" + Country + ", State=" + State + ", City=" + City + ", Street=" + Street
				+ ", Nickname=" + Nickname + ", Gender=" + Gender + ", ProfileImg=" + ProfileImg + ", Role_id="
				+ Role_id + "]";
	}

	public ProfileDTO(String fname, String lname, int pRid, String email, String password, int restrction,
			String phone_num, Timestamp create_date, String country, String state, String city, String street,
			String nickname, String gender, String profileImg, int role_id) {
		Fname = fname;
		Lname = lname;
		PRid = pRid;
		Email = email;
		Password = password;
		Restriction = restrction;
		Phone_num = phone_num;
		Create_date = create_date;
		Country = country;
		State = state;
		City = city;
		Street = street;
		Nickname = nickname;
		Gender = gender;
		ProfileImg = profileImg;
		Role_id = role_id;
	}

	public ProfileDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProfileDTO(ProfileDTO pRdto) {
		// TODO Auto-generated constructor stub
		Fname = pRdto.getFname();
		Lname = pRdto.getLname();
		PRid = pRdto.getPRid();
		Email = pRdto.getEmail();
		Password = pRdto.getPassword();
		Restriction = pRdto.getRestrction();
		Phone_num = pRdto.getPhone_num();
		Create_date = pRdto.getCreate_date();
		Country = pRdto.getCountry();
		State = pRdto.getState();
		City = pRdto.getCity();
		Street = pRdto.getStreet();
		Nickname = pRdto.getNickname();
		Gender = pRdto.getGender();
		ProfileImg = pRdto.getProfileImg();
		Role_id = pRdto.getRole_id();
	}

}