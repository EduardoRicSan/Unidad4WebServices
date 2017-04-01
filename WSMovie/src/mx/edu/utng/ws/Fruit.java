package mx.edu.utng.ws;

public class Fruit {
	private int id;
	private String name;
	private String flavor;
	private String colour;
	private float price;
	
	public Fruit(int id, String name, String flavor, String colour, float price) {
		super();
		this.id = id;
		this.name = name;
		this.flavor = flavor;
		this.colour = colour;
		this.price = price;
	}
	
	public Fruit(){
		this(0,"","","",0.0f);
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

	public String getFlavor() {
		return flavor;
	}

	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Fruit [id=" + id + ", name=" + name + ", flavor=" + flavor + ", colour=" + colour + ", price=" + price
				+ "]";
	}
	
	
	
	
}
