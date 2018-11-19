package database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.IOException;
import java.sql.*;

import model.Lesson;
import model.Member;

public class Memberdb {
	private String databaseusername = "root";
	private String databasepassword = "test";

	private ResultSet rs;
	private Connection con;
	private Statement st;

	public Memberdb() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym?autoReconnect=true&useSSL=false",
				databaseusername, databasepassword);
		st = con.createStatement();
	}

	public boolean logincontrol(String email, String password)
			throws IOException, ClassNotFoundException, SQLException {
		try {
			rs = st.executeQuery("select * from members where email='" + email + "' and password='" + password + "';");

			if (rs.next()) {
				con.close();
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		con.close();
		return false;
	}

	public Member profile(String login, String email) throws ClassNotFoundException, SQLException, ParseException {
		if (login == "true") {

			try {
				rs = st.executeQuery("select * from members where email='" + email + "';");
				if (rs.next()) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = format.parse(rs.getString("birthday"));
					java.sql.Date birthdaydate = new java.sql.Date(date.getTime());

					Member user;
					if (rs.getString("branch_number") != null) {
						user = new Member(Integer.parseInt(rs.getString("id")), rs.getString("email"),
								rs.getString("password"), rs.getString("name"), rs.getString("surname"),
								rs.getString("id_number"), rs.getString("sec_answer"),
								Integer.parseInt(rs.getString("branch_number")), birthdaydate,
								rs.getString("mobile_number"), rs.getString("address"), rs.getString("picture"));
					} else {
						user = new Member(Integer.parseInt(rs.getString("id")), rs.getString("email"),
								rs.getString("password"), rs.getString("name"), rs.getString("surname"),
								rs.getString("id_number"), rs.getString("sec_answer"), -1, birthdaydate,
								rs.getString("mobile_number"), rs.getString("address"), rs.getString("picture"));
					}
					con.close();
					return user;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		con.close();
		return null;
	}

	public Boolean editprofileprocess(String email, String name, String surname, String birthday, String address,
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
	
	public Boolean registerprocess(Member user) throws IOException, ClassNotFoundException, SQLException {
		try {
			st.executeUpdate(
					"INSERT INTO members (name, surname, id_number, email, password, sec_answer, birthday, mobile_number, address, picture, member_type) VALUES ('"
							+ user.getName() + "','" + user.getSurname() + "','" + user.getId_number() + "','"
							+ user.getEmail() + "','" + user.getPassword() + "','" + user.getSec_answer() + "','"
							+ user.getDate() + "','" + user.getMobile_number() + "','" + user.getAddress()
							+ "', 'default.png', 2 );");
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public Boolean deleteprofileprocess(String email, String password)
			throws IOException, ClassNotFoundException, SQLException {

		try {
			rs = st.executeQuery("select * from members where email='" + email + "' and password='" + password + "';");
			if (rs.next()) {
				String query = "delete from members where email='" + email + "';";
				st.executeUpdate(query);
			} else {
				return false;
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return true;
	}

	public ArrayList<Member> searchuser(String login, String nameoremail)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Member> users = new ArrayList<Member>();

		if (login == "true") {
			try {
				rs = st.executeQuery("select * from members where name like '%" + nameoremail + "%' or email like '%"
						+ nameoremail + "%';");
				Member user = null;
				while (rs.next()) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = format.parse(rs.getString("birthday"));
					java.sql.Date birthdaydate = new java.sql.Date(date.getTime());

					if (rs.getString("branch_number") != null) {
						user = new Member(Integer.parseInt(rs.getString("id")), rs.getString("email"),
								rs.getString("password"), rs.getString("name"), rs.getString("surname"),
								rs.getString("id_number"), rs.getString("sec_answer"),
								Integer.parseInt(rs.getString("branch_number")), birthdaydate,
								rs.getString("mobile_number"), rs.getString("address"), rs.getString("picture"));
					} else {
						user = new Member(Integer.parseInt(rs.getString("id")), rs.getString("email"),
								rs.getString("password"), rs.getString("name"), rs.getString("surname"),
								rs.getString("id_number"), rs.getString("sec_answer"), -1, birthdaydate,
								rs.getString("mobile_number"), rs.getString("address"), rs.getString("picture"));
					}
					users.add(user);
				}

				con.close();
				return users;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		con.close();
		return null;
	}

	public Boolean joinClass(String id, String email) throws IOException, ClassNotFoundException, SQLException {
		try {
			rs = st.executeQuery("select * from members where email='" + email + "';");
			String userid = "";
			if (rs.next()) {
				userid = rs.getString("id");
				System.out.println("INSERT INTO lesson_attend values (" + id + ", " + userid + ", " + 1 + ");");
			}
			st.executeUpdate("INSERT INTO lesson_attend values (" + id + ", " + userid + ", " + 1 + ");");
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	
	public boolean control(ArrayList<String> words,String word){
		for(String w:words){
			if(w.equals(word)){
				return true;
			}
		}
		return false;
	}

	public ArrayList<Lesson> getLessons(String email) throws IOException, ClassNotFoundException, SQLException {
		try {
			rs = st.executeQuery("select * from members where email='" + email + "';");
			String userid = "";
			if (rs.next()) {
				userid = rs.getString("id");
			}
			rs = st.executeQuery("select * from lesson_attend where member_id=" + userid + ";");
			ArrayList<String> mylesson = new ArrayList<String>();
			while (rs.next()) {
				mylesson.add(rs.getString("lesson_id"));
			}
			rs = st.executeQuery(
					"select o.id as id, o.name as name, l.name as coursename, b.name as branchname, e.name as trainername, o.date as coursedate, o.days as days, o.enddate as enddate from opened_lessons o inner join employee e on e.id=o.trainer inner join lessons l on l.id=o.lesson_id inner join branch_offices b on b.id=o.branch;");
			ArrayList<Lesson> lessons = new ArrayList<Lesson>();
			while (rs.next()) {
				if (control(mylesson,rs.getString("id").toString())) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = format.parse(rs.getString("coursedate"));
					java.sql.Date startdate = new java.sql.Date(date.getTime());
					date = format.parse(rs.getString("enddate"));
					java.sql.Date enddate = new java.sql.Date(date.getTime());
					Lesson l = new Lesson(rs.getString("id"), rs.getString("name"), rs.getString("coursename"),
							rs.getString("branchname"), rs.getString("trainername"), startdate, enddate,
							rs.getString("days"));
					lessons.add(l);
				}
			}
			con.close();
			return lessons;
		} catch (Exception e) {
			System.out.println(e);
		}
		con.close();
		return null;
	}

	public ArrayList<Lesson> searchCourse(String coursename) throws IOException, ClassNotFoundException, SQLException {
		try {
			rs = st.executeQuery("select o.id as id, o.name as name, l.name as coursename, b.name as branchname, e.name as trainername, o.date as coursedate, o.days as days, o.enddate as enddate from opened_lessons o inner join employee e on e.id=o.trainer inner join lessons l on l.id=o.lesson_id inner join branch_offices b on b.id=o.branch where l.name like '%"+coursename+"%';");
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
}
