package mx.edu.utng.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FruitWS {

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement ps;
	public FruitWS() {
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
	public int addFruit(Fruit fruit){
		int result=0;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("INSERT INTO fruit (name,flavor,colour,price) "
						+ " VALUES (?,?,?,?);");
				ps.setString(1, fruit.getName());
				ps.setString(2, fruit.getFlavor());
				ps.setString(3, fruit.getColour());
				ps.setFloat(4, fruit.getPrice());
				result=ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//para hacer un update
	public int updateFruit(Fruit fruit){
		int result=0;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("UPDATE fruit  SET name= ?,flavor= ?,colour= ?,price= ? WHERE id= ?;");
					
				ps.setString(1, fruit.getName());
				ps.setString(2, fruit.getFlavor());
				ps.setString(3, fruit.getColour());
				ps.setFloat(4, fruit.getPrice());
				ps.setInt(5, fruit.getId());
				result=ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//para borrar
	public int removeFruit(int id){
		int result=0;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("DELETE FROM fruit WHERE id= ?;");
				ps.setInt(1, id);
				result=ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//lista de fruit
	public Fruit[] getFruits(){
		Fruit[] result = null;
		List <Fruit> fruits=new ArrayList<Fruit>();
		if(connection!=null){
			try {
				statement=connection.createStatement();
				resultSet=statement.executeQuery("SELECT * FROM fruit;");
				while(resultSet.next()){
					Fruit fruit=new Fruit(
							resultSet.getInt("id"),
							resultSet.getString("name"),
							resultSet.getString("flavor"), 
							resultSet.getString("colour"), 
							resultSet.getFloat("price"));
					fruits.add(fruit);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = fruits.toArray(new Fruit[fruits.size()]);
		return result;
	}
	//
	public Fruit getFruitbyId(int id){
		Fruit fruit=null;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("SELECT * FROM fruit WHERE id = ?;");
				ps.setInt(1, id);
				resultSet=ps.executeQuery();
				while(resultSet.next()){
					fruit=new Fruit(
							resultSet.getInt("id"),
							resultSet.getString("name"),
							resultSet.getString("flavor"), 
							resultSet.getString("colour"), 
							resultSet.getFloat("price"));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fruit;
	}
	

}