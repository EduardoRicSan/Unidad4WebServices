package mx.edu.utng.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MeWS {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement ps;
	
	public MeWS() {
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
	public int addMe(Me me){
		int result=0;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("INSERT INTO me (fullname,nickname,age,hobby) "
						+ " VALUES (?,?,?,?);");
				ps.setString(1, me.getFullname());
				ps.setString(2, me.getNickname());
				ps.setString(3, me.getAge());
				ps.setString(4, me.getHobby());
				result=ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//para hacer un update
	public int updateMe(Me me){
		int result=0;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("UPDATE me  SET fullname= ?,nickname= ?, age= ?, hobby= ? WHERE id= ?;");
					
				ps.setString(1, me.getFullname());
				ps.setString(2, me.getNickname());
				ps.setString(3, me.getAge());
				ps.setString(4, me.getHobby());
				ps.setInt(5, me.getId());
				result=ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//para borrar
	public int removeMe(int id){
		int result=0;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("DELETE FROM me WHERE id= ?;");
				ps.setInt(1, id);
				result=ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//lista de documentos
	public Me[] getMine(){
		Me[] result = null;
		List<Me> mine=new ArrayList<Me>();
		if(connection!=null){
			try {
				statement=connection.createStatement();
				resultSet=statement.executeQuery("SELECT * FROM me;");
				while(resultSet.next()){
					Me me=new  Me(
							resultSet.getInt("id"),
							resultSet.getString("fullname"),
							resultSet.getString("nickname"), 
							resultSet.getString("age"), 
							resultSet.getString("hobby"));
					mine.add(me);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = mine.toArray(new Me[mine.size()]);
		return result;
	}
	//
	public Me getMebyId(int id){
		Me me = null;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("SELECT * FROM me WHERE id = ?;");
				ps.setInt(1, id);
				resultSet=ps.executeQuery();
				while(resultSet.next()){
					me=new Me(
							resultSet.getInt("id"),
							resultSet.getString("fullname"),
							resultSet.getString("nickname"), 
							resultSet.getString("age"), 
							resultSet.getString("hobby"));	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return me;
	}
	/*
	public static void main(String[] args) {
		MovieWS ws=new MovieWS();
		Movie movie=new Movie(0, "King kong", "Un chango grande", 0, 200);
		ws.addMovie(movie);
	}*/

}
