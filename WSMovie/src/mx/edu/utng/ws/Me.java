package mx.edu.utng.ws;

public class Me {
	private int id;
	private String fullname;
	private String nickname;
	private String age;
	private String  hobby;
	public Me(int id, String fullname, String nickname, String age, String hobby) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.nickname = nickname;
		this.age = age;
		this.hobby = hobby;
	}
	
	public Me(){
		this(0,"","","","");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	@Override
	public String toString() {
		return "Me [id=" + id + ", fullname=" + fullname + ", nickname=" + nickname + ", age=" + age + ", hobby="
				+ hobby + "]";
	}
	

}
