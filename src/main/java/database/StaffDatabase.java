package database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.sql.*;

import model.*;

public class StaffDatabase {
	private String databaseusername = "root";
	private String databasepassword = "test";

	private ResultSet rs;
	private Connection con;
	private Statement st;

	private ArrayList<Lesson> lessons;
	private ArrayList<Member> members;
	private ArrayList<Staff> staff;
	private ArrayList<Integer> number;
	
	private String lessonid2="";
	
	public String getLessonId() {
		return lessonid2;
	}

	public ArrayList<Lesson> getLessons() {
		return lessons;
	}

	public ArrayList<Member> getMembers() {
		return members;
	}
	
	public ArrayList<Staff> getStaffs() {
		return staff;
	}

	public ArrayList<Integer> getNumber() {
		return number;
	}

	public StaffDatabase() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/gym?autoReconnect=true&useSSL=false", databaseusername,
				databasepassword);
		st = con.createStatement();
	}
	
	public Boolean deletestaffprofileprocess(String email, String password)
			throws IOException, ClassNotFoundException, SQLException {

		try {
			rs = st.executeQuery("select * from employee where mail='" + email + "' and password='" + password + "';");
			if (rs.next()) {
				String query = "delete from members where mail='" + email + "';";
				st.executeUpdate(query);
			} else {
				return false;
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return true;
	}

	public boolean logincontroltrainer(String email, String password)
			throws IOException, ClassNotFoundException, SQLException {
		try {
			rs = st.executeQuery(
					"select * from employee where mail='" + email + "' and password='" + password + "' and role=3;");

			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public boolean logincontrolbranchmanager(String email, String password)
			throws IOException, ClassNotFoundException, SQLException {
		try {
			rs = st.executeQuery(
					"select * from employee where mail='" + email + "' and password='" + password + "' and role=2;");

			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public boolean logincontrolsystemmanager(String email, String password)
			throws IOException, ClassNotFoundException, SQLException {
		try {
			rs = st.executeQuery(
					"select * from employee where mail='" + email + "' and password='" + password + "' and role=1;");

			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public Staff trainerprofile(String login, String email)
			throws ClassNotFoundException, SQLException, ParseException {
		if (login.equals("T")) {
			try {
				rs = st.executeQuery("select * from employee where mail='" + email + "' and role=3;");
				if (rs.next()) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = format.parse(rs.getString("birthday"));
					java.sql.Date birthdaydate = new java.sql.Date(date.getTime());

					Staff staff = new Staff(Integer.parseInt(rs.getString("id")), rs.getString("mail"),
							rs.getString("password"), rs.getString("name"), rs.getString("surname"),
							rs.getString("id_number"), Integer.parseInt(rs.getString("role")),
							Integer.parseInt(rs.getString("branch")), birthdaydate, rs.getString("mobile_number"),
							rs.getString("address"), rs.getString("Image"));
					
					return staff;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Staff branchmanagerprofile(String login, String email)
			throws ClassNotFoundException, SQLException, ParseException {
		if (login == "T") {
			try {
				rs = st.executeQuery("select * from employee where mail='" + email + "' and role=2;");
				if (rs.next()) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = format.parse(rs.getString("birthday"));
					java.sql.Date birthdaydate = new java.sql.Date(date.getTime());

					Staff staff = new Staff(Integer.parseInt(rs.getString("id")), rs.getString("mail"),
							rs.getString("password"), rs.getString("name"), rs.getString("surname"),
							rs.getString("id_number"), Integer.parseInt(rs.getString("role")),
							Integer.parseInt(rs.getString("branch")), birthdaydate, rs.getString("mobile_number"),
							rs.getString("address"), rs.getString("Image"));
					
					return staff;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	

	public Staff systemmanagerprofile(String login, String email)
			throws ClassNotFoundException, SQLException, ParseException {
		if (login == "T") {
			try {
				rs = st.executeQuery("select * from employee where mail='" + email + "' and role=1;");
				if (rs.next()) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = format.parse(rs.getString("birthday"));
					java.sql.Date birthdaydate = new java.sql.Date(date.getTime());

					Staff staff = new Staff(Integer.parseInt(rs.getString("id")), rs.getString("mail"),
							rs.getString("password"), rs.getString("name"), rs.getString("surname"),
							rs.getString("id_number"), Integer.parseInt(rs.getString("role")),
							Integer.parseInt(rs.getString("branch")), birthdaydate, rs.getString("mobile_number"),
							rs.getString("address"), rs.getString("Image"));
					
					return staff;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Boolean editprofileprocess(String email, String name, String surname, String birthday, String address,
			String picture) throws IOException, ClassNotFoundException, SQLException {
		try {
			String query;
			if (picture != null) {
				query = "UPDATE employee set name='" + name + "', surname='" + surname + "', birthday='" + birthday
						+ "', address='" + address + "', Image='" + picture + "' where mail='" + email + "';";
			} else {
				query = "UPDATE employee set name='" + name + "', surname='" + surname + "', birthday='" + birthday
						+ "', address='" + address + "'" + " where mail='" + email + "';";
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
					"INSERT INTO members (`name`, `surname`, `id_number`, `email`, `password`, `sec_answer`, `birthday`, `mobile_number`, `address`, `picture`) VALUES ('"
							+ user.getName() + "','" + user.getSurname() + "','" + user.getId_number() + "','"
							+ user.getEmail() + "','" + user.getPassword() + "','" + user.getSec_answer() + "','"
							+ user.getDate() + "','" + user.getMobile_number() + "','" + user.getAddress()
							+ "', 'default.png' );");
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

				return users;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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

	public boolean getLessons(String email) throws IOException, ClassNotFoundException, SQLException {
		try {
			rs = st.executeQuery("select * from employee where mail='" + email + "';");
			String userid = "";
			if (rs.next()) {
				userid = rs.getString("id");
			}
			rs = st.executeQuery("select o.id from opened_lessons o inner join employee e on o.trainer=e.id where e.id="
					+ userid + ";");
			ArrayList<String> mylesson = new ArrayList<String>();
			int k = 0;
			while (rs.next()) {
				mylesson.add(rs.getString("id"));
				k++;
			}
			lessons = new ArrayList<Lesson>();
			members = new ArrayList<Member>();
			number = new ArrayList<Integer>();
			int c = 0;
			int n = 0;
			for (int i = 0; i < mylesson.size(); i++) {
				rs = st.executeQuery(
						"select distinct o.id as classid, o.name as classname, m.email as email, m.mobile_number as phonenumber, l.member_id as memberid, m.name as name,"
								+ " m.surname as surname, m.birthday, m.address, m.picture as picture from opened_lessons o inner join employee e on o.trainer=e.id inner join lesson_attend l on l.lesson_id=o.id inner"
								+ " join members m on m.id=l.member_id where e.id=" + userid + " and o.id="
								+ mylesson.get(i) + ";");
				while (rs.next()) {
					n++;
					if (c == 0) {
						c++;
						Lesson l = new Lesson(rs.getString("classid"), rs.getString("classname"), null, null, null,
								null, null, null);
						lessons.add(l);
					}
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = format.parse(rs.getString("birthday"));
					java.sql.Date birthdaydate = new java.sql.Date(date.getTime());
					Member user = new Member(Integer.parseInt(rs.getString("memberid")), rs.getString("email"), null,
							rs.getString("name"), rs.getString("surname"), null, null, -1, birthdaydate,
							rs.getString("phonenumber"), rs.getString("address"), rs.getString("picture"));
					members.add(user);
				}
				number.add(n);
				c = 0;
			}
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	
	public Boolean editmemberprofileprocess(String email, String name, String surname, String birthday, String address,
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
	
	public Boolean deletememberprofileprocess(String email)
			throws IOException, ClassNotFoundException, SQLException {

		try {
			String query = "delete from members where email='" + email + "';";
			st.executeUpdate(query);
		} catch (Exception ex) {
			System.out.println(ex);
			return false;
		}

		return true;
	}
	
	public Boolean deletestaffprofileprocess(String email)
			throws IOException, ClassNotFoundException, SQLException {

		try {
			String query = "delete from employee where mail='" + email + "';";
			st.executeUpdate(query);
		} catch (Exception ex) {
			System.out.println(ex);
			return false;
		}

		return true;
	}

	public boolean getAllMembers(String email) throws IOException, ClassNotFoundException, SQLException {
		try {
			rs = st.executeQuery("select * from members;");
			members = new ArrayList<Member>();
			rs = st.executeQuery(
					"select distinct * from members;");
			while (rs.next()) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = format.parse(rs.getString("birthday"));
				java.sql.Date birthdaydate = new java.sql.Date(date.getTime());
				Member user = new Member(Integer.parseInt(rs.getString("id")), rs.getString("email"), null,
						rs.getString("name"), rs.getString("surname"), null, null, -1, birthdaydate,
						rs.getString("mobile_number"), rs.getString("address"), rs.getString("picture"));
				members.add(user);
			}
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	
	public ArrayList<String> getAllLessonMembers(String id) throws IOException, ClassNotFoundException, SQLException {
		try {
			rs = st.executeQuery("select distinct member_id from lesson_attend where lesson_id="+id+";");
			ArrayList<String> memberid = new ArrayList<String>();
			while (rs.next()) {
				memberid.add(rs.getString("member_id"));
			}
			return memberid;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public Integer getBranchOffice(String name) throws IOException, ClassNotFoundException, SQLException {
		try {
			rs = st.executeQuery("select * from branch_offices where name='"+name+"';");
			if(rs.next()){
				return Integer.parseInt(rs.getString("id"));
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return -1;
	}
	
	
	public Boolean staffregisterprocess(Staff user) throws IOException, ClassNotFoundException, SQLException {
		try {
			st.executeUpdate(
					"INSERT INTO employee (name, surname, id_number, mail, password, branch, birthday, mobile_number, address, Image, role, activation) VALUES ('"
							+ user.getName() + "','" + user.getSurname() + "','" + user.getId_number() + "','"
							+ user.getEmail() + "','" + user.getPassword() + "','" + user.getBranch_num() + "','"
							+ user.getDate() + "','" + user.getMobile_number() + "','" + user.getAddress()
							+ "', 'default.png', '"+user.getRole()+"', '1' );");
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	
	public boolean getAllStaff(String email) throws IOException, ClassNotFoundException, SQLException {
		try {
			rs = st.executeQuery("select * from employee;");
			staff = new ArrayList<Staff>();
			rs = st.executeQuery(
					"select distinct * from employee;");
			while (rs.next()) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = format.parse(rs.getString("birthday"));
				java.sql.Date birthdaydate = new java.sql.Date(date.getTime());
				Staff user = new Staff(Integer.parseInt(rs.getString("id")), rs.getString("mail"), rs.getString("password"),
						rs.getString("name"), rs.getString("surname"), rs.getString("id_number"), Integer.parseInt(rs.getString("role")), -1, birthdaydate,
						rs.getString("mobile_number"), rs.getString("address"), rs.getString("Image"));
				staff.add(user);
			}
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public static boolean checkIfExists(String[] myStringArray, String stringToLocate) {
		for (String element : myStringArray) {
			if (element.equals(stringToLocate)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Member> getLessonsAttend(String email) throws IOException, ClassNotFoundException, SQLException {
		try {
			ArrayList<Member> memberss = new ArrayList<Member>();
			rs = st.executeQuery("select * from employee where mail='" + email + "';");
			String userid = "";
			if (rs.next()) {
				userid = rs.getString("id");
			}
			rs = st.executeQuery(
					" select o.id, o.date, o.enddate, o.days from opened_lessons o inner join employee e on o.trainer=e.id where e.id="
							+ userid + ";");

			ArrayList<String> lessonid = new ArrayList<String>();
			while (rs.next()) {
				lessonid.add(rs.getString("id"));
			}

			String nowcourse = "";
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.now();

			for (int j = 0; j < lessonid.size(); j++) {
				rs = st.executeQuery(
						"select o.id as id, o.name as name, l.name as coursename, b.name as branchname, e.name as trainername, o.date as coursedate, o.days as days, o.enddate as enddate from opened_lessons o inner join employee e on e.id=o.trainer inner join lessons l on l.id=o.lesson_id inner join branch_offices b on b.id=o.branch where o.id="
								+ lessonid.get(j) + ";");
				ArrayList<Lesson> lessons = new ArrayList<Lesson>();
				if (rs.next()) {
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
				Lesson l = lessons.get(j);
				String[] dates = l.getDates().split("-");
				Date start = l.getStartdate();
				Date start2 = l.getStartdate();
				Date end = l.getEnddate();
				int diffInDays = (int) ((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));
				int k = 0;
				for (int i = 0; i <= diffInDays; i++) {
					if (checkIfExists(dates, (start.getDay() + i) % 7 + 1 + "")) {
						Date sumDate = new Date(start2.getTime() + (i - k) * (1000 * 60 * 60 * 24));
						start2 = sumDate;
						k = i;
						if (dtf.format(localDate).toString().equals(sumDate.toString())) {
							nowcourse = l.getId();
							lessonid2=l.getId();
						}

					}
				}
			}
			
			if (!nowcourse.equals("")) {
				rs = st.executeQuery(
						"select distinct o.id as classid, o.name as classname, m.email as email, m.mobile_number as phonenumber, l.member_id as memberid, m.name as name,"
								+ " m.surname as surname, m.birthday, m.address, m.picture as picture from opened_lessons o inner join employee e on o.trainer=e.id inner join lesson_attend l on l.lesson_id=o.id inner"
								+ " join members m on m.id=l.member_id where e.id=" + userid + " and o.id=" + nowcourse
								+ ";");
				while (rs.next()) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = format.parse(rs.getString("birthday"));
					java.sql.Date birthdaydate = new java.sql.Date(date.getTime());
					Member user = new Member(Integer.parseInt(rs.getString("memberid")), rs.getString("email"), null,
							rs.getString("name"), rs.getString("surname"), null, null, -1, birthdaydate,
							rs.getString("phonenumber"), rs.getString("address"), rs.getString("picture"));
					memberss.add(user);
				}

				return memberss;
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public Boolean memberAttendProcess(String lessonid, String userid, String status, String date) throws IOException, ClassNotFoundException, SQLException {
		try {
			st.executeUpdate("insert into lesson_attend values('"+lessonid+"', '"+userid+"', '"+status+"', '"+date+"'"+");");
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public boolean getLessonsManipulate(String email) throws IOException, ClassNotFoundException, SQLException {
		try {
			rs = st.executeQuery("select * from employee where mail='" + email + "';");
			String userid = "";
			if (rs.next()) {
				userid = rs.getString("id");
			}
			rs = st.executeQuery(
					"select o.id as id, o.name as name, l.name as branch, o.days as days, o.date as start, o.enddate as end, o.trainer as trainer from opened_lessons o inner join employee e on o.trainer=e.id inner join lessons l on l.id=o.lesson_id where e.id="
							+ userid + ";");
			lessons = new ArrayList<Lesson>();
			while (rs.next()) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = format.parse(rs.getString("start"));
				java.sql.Date startdate = new java.sql.Date(date.getTime());
				java.util.Date edate = format.parse(rs.getString("end"));
				java.sql.Date enddate = new java.sql.Date(edate.getTime());
				Lesson l = new Lesson(rs.getString("id"), rs.getString("name"), rs.getString("branch"), null,
						rs.getString("trainer"), startdate, enddate, rs.getString("days"));
				lessons.add(l);
			}
			return true;
		} catch (Exception e) {

		}
		return false;
	}

	public boolean getLessonsManipulateBranchManager(String email)
			throws IOException, ClassNotFoundException, SQLException {
		try {
			rs = st.executeQuery(
					"select o.id as id, o.name as name, l.name as branch, o.days as days, o.date as start, o.enddate as end, o.trainer as trainer from opened_lessons o inner join employee e on o.trainer=e.id inner join lessons l on l.id=o.lesson_id");

			lessons = new ArrayList<Lesson>();
			while (rs.next()) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = format.parse(rs.getString("start"));
				java.sql.Date startdate = new java.sql.Date(date.getTime());
				java.util.Date edate = format.parse(rs.getString("end"));
				java.sql.Date enddate = new java.sql.Date(edate.getTime());
				Lesson l = new Lesson(rs.getString("id"), rs.getString("name"), rs.getString("branch"), null,
						rs.getString("trainer"), startdate, enddate, rs.getString("days"));
				lessons.add(l);
			}
			return true;
		} catch (Exception e) {

		}
		return false;
	}
	
	public boolean getAttendStatus(String lessonid, String userid)
			throws IOException, ClassNotFoundException, SQLException {
		try {
			rs = st.executeQuery("select count(status) as count from lesson_attend where lesson_id="+lessonid+" and member_id="+userid+" and status=2 group by status;");
			if(rs.next()){
				if(Integer.parseInt(rs.getString("count"))==3){
					String query = "delete from lesson_attend where member_id="+userid+";";
					st.executeUpdate(query);
				}
			}
			return true;
		} catch (Exception e) {

		}
		return false;
	}

	public Boolean editclassprocess(String id, String coursename, java.util.Date startdate, java.util.Date enddate,
			String days, String office, String branch) throws IOException, ClassNotFoundException, SQLException {
		try {
			String branchid = "";
			String officeid = "";
			rs = st.executeQuery("select id from branch_offices where name='" + office + "';");
			if (rs.next()) {
				officeid = rs.getString("id");
			}
			rs = st.executeQuery("select id from lessons where name='" + branch + "';");
			if (rs.next()) {
				branchid = rs.getString("id");
			}
			String query = "UPDATE opened_lessons set date='" + startdate + "', enddate='" + enddate + "', name='" + coursename + "', days='"
					+ days + "', branch='" + officeid + "', lesson_id='" + branchid + "' where id='" + id + "';";
			st.executeUpdate(query);
			return true;
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return false;
	}
	
	public Boolean createclassprocess(String coursename, String startdate, String enddate,
			String days, String office, String branch, String name) throws IOException, ClassNotFoundException, SQLException {
		try {
			String branchid = "";
			String officeid = "";
			String trainer = "";
			rs = st.executeQuery("select id from branch_offices where name='" + office + "';");
			if (rs.next()) {
				officeid = rs.getString("id");
			}
			rs = st.executeQuery("select id from lessons where name='" + branch + "';");
			if (rs.next()) {
				branchid = rs.getString("id");
			}
			rs = st.executeQuery("select id from employee where name='" + name + "';");
			if (rs.next()) {
				trainer = rs.getString("id");
			}
			
			String query = "insert into opened_lessons(lesson_id, branch, date, trainer, name, days, enddate)  values('" + branchid + "', '" + officeid + "', '" + startdate + "', '" + trainer + "', '"
					+ coursename + "', '" + days + "', '" + enddate + "');";
			st.executeUpdate(query);
			return true;
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return false;
	}

	public ArrayList<String> getbranchoffices() throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<String> offices = new ArrayList<String>();

		try {
			rs = st.executeQuery("select name from branch_offices;");
			while (rs.next()) {
				offices.add(rs.getString("name"));
			}
			return offices;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getTrainers() throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<String> offices = new ArrayList<String>();

		try {
			rs = st.executeQuery("select name from employee where role=3;");
			while (rs.next()) {
				offices.add(rs.getString("name"));
			}
			return offices;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> getbranchs() throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<String> branchs = new ArrayList<String>();

		try {
			rs = st.executeQuery("select name from lessons;");
			while (rs.next()) {
				branchs.add(rs.getString("name"));
			}
			return branchs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean deleteClassProcess(String id) throws IOException, ClassNotFoundException, SQLException {

		try {
			String query = "delete from opened_lessons where id='" + id + "';";
			st.executeUpdate(query);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return true;
	}
}
