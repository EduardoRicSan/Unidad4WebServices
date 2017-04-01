package mx.edu.utng.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DocumentWS {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement ps;
	
	public DocumentWS() {
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
	public int addDocument(Document document){
		int result=0;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("INSERT INTO document (description,active,university,date) "
						+ " VALUES (?,?,?,?);");
				ps.setString(1, document.getDescription());
				ps.setString(2, document.getActive());
				ps.setString(3, document.getUniversity());
				ps.setString(4, document.getDate());
				result=ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//para hacer un update
	public int updateDocument(Document document){
		int result=0;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("UPDATE document  SET description= ?,active= ?, university= ?,date= ? WHERE id= ?;");
					
				ps.setString(1, document.getDescription());
				ps.setString(2, document.getActive());
				ps.setString(3, document.getUniversity());
				ps.setString(4, document.getDate());
				ps.setInt(5, document.getId());
				result=ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//para borrar
	public int removeDocument(int id){
		int result=0;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("DELETE FROM document WHERE id= ?;");
				ps.setInt(1, id);
				result=ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//lista de documentos
	public Document[] getDocuments(){
		Document[] result = null;
		List<Document> documents=new ArrayList<Document>();
		if(connection!=null){
			try {
				statement=connection.createStatement();
				resultSet=statement.executeQuery("SELECT * FROM document;");
				while(resultSet.next()){
					Document document=new Document(
							resultSet.getInt("id"),
							resultSet.getString("description"),
							resultSet.getString("active"), 
							resultSet.getString("university"), 
							resultSet.getString("date"));
					documents.add(document);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = documents.toArray(new Document[documents.size()]);
		return result;
	}
	//
	public Document getDocumentbyId(int id){
		Document document = null;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("SELECT * FROM document WHERE id = ?;");
				ps.setInt(1, id);
				resultSet=ps.executeQuery();
				while(resultSet.next()){
					document=new Document(
							resultSet.getInt("id"),
							resultSet.getString("description"),
							resultSet.getString("active"), 
							resultSet.getString("university"), 
							resultSet.getString("date"));	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return document;
	}
	/*
	public static void main(String[] args) {
		MovieWS ws=new MovieWS();
		Movie movie=new Movie(0, "King kong", "Un chango grande", 0, 200);
		ws.addMovie(movie);
	}*/

}
