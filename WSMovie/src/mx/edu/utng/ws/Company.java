package mx.edu.utng.ws;

public class Company {
	private int id;
	private String name;
	private String owner;
	private String foundation;
	private String type;
	private String objetive;
	private String partner;
	
	public Company(int id, String name, String owner, String foundation, String type, String objetive, String partner) {
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.foundation = foundation;
		this.type = type;
		this.objetive = objetive;
		this.partner = partner;
	}
	
	public Company(){
		this(0,"","","","","","");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getFoundation() {
		return foundation;
	}

	public void setFoundation(String foundation) {
		this.foundation = foundation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getObjetive() {
		return objetive;
	}

	public void setObjetive(String objetive) {
		this.objetive = objetive;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", owner=" + owner + ", foundation=" + foundation + ", type="
				+ type + ", objetive=" + objetive + ", partner=" + partner + "]";
	}
	
	

}
