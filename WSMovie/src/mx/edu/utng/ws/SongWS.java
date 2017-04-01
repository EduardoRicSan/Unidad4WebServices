package mx.edu.utng.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SongWS {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement ps;
	
	public SongWS() {
		conectar();
	}
	private void conectar(){
		try {
			Class.forName("org.postgresql.Driver");
			connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/wstest","postgres","1234");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
		e.printStackTrace();
		}
	}
	public int addSong(Song song){
		int result=0;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("INSERT INTO song (name,author,album,year) "
						+ " VALUES (?,?,?,?);");
				ps.setString(1, song.getName());
				ps.setString(2, song.getAuthor());
				ps.setString(3, song.getAlbum());
				ps.setInt(4, song.getYear());
				result=ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//para hacer un update
	public int updateSong(Song song){
		int result=0;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("UPDATE song  SET name= ?,author= ?, album= ?,year= ? WHERE id= ?;");
					
				ps.setString(1, song.getName());
				ps.setString(2, song.getAuthor());
				ps.setString(3, song.getAlbum());
				ps.setInt(4, song.getYear());
				ps.setInt(5, song.getId());
				result=ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//para borrar
	public int removeSong(int id){
		int result=0;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("DELETE FROM song WHERE id= ?;");
				ps.setInt(1, id);
				result=ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//lista de documentos
	public Song[] getSongs(){
		Song[] result = null;
		List<Song> songs=new ArrayList<Song>();
		if(connection!=null){
			try {
				statement=connection.createStatement();
				resultSet=statement.executeQuery("SELECT * FROM song;");
				while(resultSet.next()){
					Song song=new Song(
							resultSet.getInt("id"),
							resultSet.getString("name"),
							resultSet.getString("author"), 
							resultSet.getString("album"), 
							resultSet.getInt("year"));
					songs.add(song);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = songs.toArray(new Song[songs.size()]);
		return result;
	}
	//
	public Song getSongbyId(int id){
		Song song = null;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("SELECT * FROM song WHERE id = ?;");
				ps.setInt(1, id);
				resultSet=ps.executeQuery();
				while(resultSet.next()){
					song=new Song(
							resultSet.getInt("id"),
							resultSet.getString("name"),
							resultSet.getString("author"), 
							resultSet.getString("album"), 
							resultSet.getInt("year"));	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return song;
	}
	

}



