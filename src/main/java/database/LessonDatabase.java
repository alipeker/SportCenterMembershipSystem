package database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.IOException;
import java.sql.*;

import model.Lesson;
import model.Member;

public class LessonDatabase {
	private String databaseusername = "root";
	private String databasepassword = "test";

	private ResultSet rs;
	private Connection con;
	private Statement st;

	public LessonDatabase() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym?autoReconnect=true&useSSL=false",
				databaseusername, databasepassword);
		st = con.createStatement();
	}

	public Lesson getLesson(String id)
			throws IOException, ClassNotFoundException, SQLException {
		try {
			rs = st.executeQuery("select o.id as id, o.name as name, l.name as coursename, b.name as branchname, e.name as trainername, o.date as coursedate, o.days as days, o.enddate as enddate from opened_lessons o inner join employee e on e.id=o.trainer inner join lessons l on l.id=o.lesson_id inner join branch_offices b on b.id=o.branch where o.id="+id+";");
			
			if (rs.next()) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = format.parse(rs.getString("coursedate"));
				java.sql.Date startdate = new java.sql.Date(date.getTime());
				date = format.parse(rs.getString("enddate"));
				java.sql.Date enddate = new java.sql.Date(date.getTime());
				Lesson l=new Lesson(rs.getString("id"),rs.getString("name"),rs.getString("coursename"),rs.getString("branchname"),rs.getString("trainername"),startdate,enddate,rs.getString("days"));
				con.close();
				return l;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		con.close();
		return null;
	}
	
	public ArrayList<Lesson> getAllLesson()
			throws IOException, ClassNotFoundException, SQLException {
		try {
			rs = st.executeQuery("select o.id as id, o.name as name, l.name as coursename, b.name as branchname, e.name as trainername, o.date as coursedate, o.days as days, o.enddate as enddate from opened_lessons o inner join employee e on e.id=o.trainer inner join lessons l on l.id=o.lesson_id inner join branch_offices b on b.id=o.branch;");
			ArrayList<Lesson> lessons=new ArrayList<Lesson>();
			while (rs.next()) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = format.parse(rs.getString("coursedate"));
				java.sql.Date startdate = new java.sql.Date(date.getTime());
				date = format.parse(rs.getString("enddate"));
				java.sql.Date enddate = new java.sql.Date(date.getTime());
				Lesson l=new Lesson(rs.getString("id"),rs.getString("name"),rs.getString("coursename"),rs.getString("branchname"),rs.getString("trainername"),startdate,enddate,rs.getString("days"));
				lessons.add(l);
			}
			con.close();
			return lessons;
		} catch (Exception e) {
			System.out.println(e);
		}
		con.close();
		return null;
	}
	
	public Boolean editlessonprocess(String email, String name, String surname, String birthday, String address,
			String picture) throws IOException, ClassNotFoundException, SQLException {
		try {
			String query;
			if (picture != null) {
				query = "UPDATE members set name='" + name + "', surname='" + surname + "', birthday='" + birthday
						+ "', address='" + address + "', picture='" + picture + "' where email='" + email + "';";
			} else {
				query = "UPDATE members set name='" + name + "', surname='" + surname + "', birthday='" + birthday
						+ "', address='" + address + "'" + " where email='" + email + "';";
			}
			st.executeUpdate(query);
			return true;
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return false;
	}
}