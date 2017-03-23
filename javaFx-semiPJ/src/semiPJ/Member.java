package semiPJ;

public class Member {
	private int code;
	private String name;
	private String id;
	private String birth;
	private String phone;
	private double times;
	
	// Member 생성자
	public Member(int code, String name, String id, String birth, String phone, double times) {
		this.code = code;
		this.name = name;
		this.id = id;
		this.birth = birth;
		this.phone = phone;
		this.times = times;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getTimes() {
		return times;
	}

	public void setTimes(double times) {
		this.times = times;
	}
	
	
	

	

}
