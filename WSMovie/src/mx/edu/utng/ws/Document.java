package mx.edu.utng.ws;

public class Document {
	private int id;
	private String description;
	private String active;
	private String university;
	private String  date;
	
	public Document(int id, String description, String active, String university, String date) {
		super();
		this.id = id;
		this.description = description;
		this.active = active;
		this.university = university;
		this.date = date;
	}
	public Document(){
		this(0,"","","","");
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Document [id=" + id + ", description=" + description + ", active=" + active + ", university="
				+ university + ", date=" + date + "]";
	}
	
	
	
	

}
