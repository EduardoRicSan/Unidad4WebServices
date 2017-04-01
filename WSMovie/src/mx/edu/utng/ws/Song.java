package mx.edu.utng.ws;

public class Song {
	private int id;
	private String name;
	private String author;
	private String album;
	private int year;
	
	public Song(int id, String name, String author, String album, int year) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.album = album;
		this.year = year;
	}
	public Song(){
		this(0,"","","",0);
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	@Override
	public String toString() {
		return "Song [id=" + id + ", name=" + name + ", author=" + author + ", album=" + album + ", year=" + year + "]";
	}
	
	
	

}
