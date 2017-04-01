package mx.edu.utng.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompanyWS {

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement ps;
	
	public CompanyWS() {
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
	public int addCompany(Company company){
		int result=0;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("INSERT INTO company (name,owner,foundation,type,objetive,partner) "
						+ " VALUES (?,?,?,?,?,?);");
				ps.setString(1, company.getName());
				ps.setString(2, company.getOwner());
				ps.setString(3, company.getFoundation());
				ps.setString(4, company.getType());
				ps.setString(5, company.getObjetive());
				ps.setString(6, company.getPartner());
				result=ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//para hacer un update
	public int updateCompany(Company company){
		int result=0;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("UPDATE company  SET name= ?,owner= ?, foundation= ?, type= ?, objetive=?, partner=? WHERE id= ?;");
					
				ps.setString(1, company.getName());
				ps.setString(2, company.getOwner());
				ps.setString(3, company.getFoundation());
				ps.setString(4, company.getType());
				ps.setString(5, company.getObjetive());
				ps.setString(6, company.getPartner());
				ps.setInt(7, company.getId());
				result=ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//para borrar
	public int removeCompany(int id){
		int result=0;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("DELETE FROM company WHERE id= ?;");
				ps.setInt(1, id);
				result=ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//lista de documentos
	public Company[] getCompanies(){
		Company[] result = null;
		List<Company> companies=new ArrayList<Company>();
		if(connection!=null){
			try {
				statement=connection.createStatement();
				resultSet=statement.executeQuery("SELECT * FROM company;");
				while(resultSet.next()){
					Company company=new Company(
							resultSet.getInt("id"),
							resultSet.getString("name"),
							resultSet.getString("owner"), 
							resultSet.getString("foundation"), 
							resultSet.getString("type"),
							resultSet.getString("objetive"),
							resultSet.getString("partner"));
					companies.add(company);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = companies.toArray(new Company[companies.size()]);
		return result;
	}
	//
	public Company getCompanybyId(int id){
		Company company = null;
		if(connection!=null){
			try {
				ps=connection.prepareStatement("SELECT * FROM company WHERE id = ?;");
				ps.setInt(1, id);
				resultSet=ps.executeQuery();
				while(resultSet.next()){
					company=new Company(
							resultSet.getInt("id"),
							resultSet.getString("name"),
							resultSet.getString("owner"), 
							resultSet.getString("foundation"), 
							resultSet.getString("type"),
							resultSet.getString("objetive"),
							resultSet.getString("partner"));	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return company;
	}
	/*
	public static void main(String[] args) {
		MovieWS ws=new MovieWS();
		Movie movie=new Movie(0, "King kong", "Un chango grande", 0, 200);
		ws.addMovie(movie);
	}*/

}
