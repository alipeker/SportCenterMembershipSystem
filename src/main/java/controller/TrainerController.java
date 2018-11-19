package controller;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.apache.commons.fileupload.FileItem;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import model.Lesson;
import model.Member;
import model.Staff;
import database.*;

@Controller
public class TrainerController {
	private final String UPLOAD_DIRECTORY = "D:\\javaworks\\SportCenter\\src\\main\\webapp\\resources\\images\\employee\\";
	
	@RequestMapping(value = "/trainerprofile")
	public ModelAndView profile(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("trainer") == "true") {
			modelAndView = new ModelAndView("trainer/profile");
			try {
				StaffDatabase database = new StaffDatabase();
				Staff trainer = database.trainerprofile("T",
						request.getSession().getAttribute("login_user").toString());
				modelAndView.addObject("error", request.getSession().getAttribute("invalidpassword"));
				request.getSession().setAttribute("invalidpassword", "");
				if (trainer != null) {
					modelAndView.addObject("email", trainer.getEmail());
					modelAndView.addObject("name", trainer.getName());
					modelAndView.addObject("surname", trainer.getSurname());
					modelAndView.addObject("phone_number", trainer.getMobile_number());
					modelAndView.addObject("address", trainer.getAddress());
					modelAndView.addObject("birthday", trainer.getDate());
					modelAndView.addObject("picture", trainer.getPicture());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			modelAndView = new ModelAndView(new RedirectView("/loginstaff", true));
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/trainercalendar")
	public ModelAndView calendar(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException, IOException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("trainer").equals("true")) {
			modelAndView = new ModelAndView("trainer/calendar");

			ArrayList<JSONObject> lessons = new ArrayList<JSONObject>();

			LessonDatabase database = new LessonDatabase();
			ArrayList<Lesson> lessonobjects = database.getAllLesson();

			for (int j = 0; j < lessonobjects.size(); j++) {
				Lesson l = lessonobjects.get(j);
				String[] dates = l.getDates().split("-");
				Date start = l.getStartdate();
				Date start2 = l.getStartdate();
				Date end = l.getEnddate();
				int diffInDays = (int) ((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));
				int k = 0;
				for (int i = 0; i <= diffInDays; i++) {
					if (checkIfExists(dates, (start.getDay() + i) % 7 + 1 + "")) {
						JSONObject lesson = new JSONObject();
						Date sumDate = new Date(start2.getTime() + (i - k) * (1000 * 60 * 60 * 24));
						start2 = sumDate;
						k = i;
						lesson.put("name", l.getCoursename() + "\n" + l.getBranchname());
						lesson.put("date", sumDate);
						lesson.put("url", "./showclasstrainer?id=" + l.getId());
						lessons.add(lesson);
					}
				}
			}
			modelAndView.addObject("dersler", lessons);
		} else {
			modelAndView = new ModelAndView(new RedirectView("/loginstaff", true));
		}

		return modelAndView;
	}

	@RequestMapping(value = "/edittrainerprofile")
	public ModelAndView edittrainerprofile(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("trainer") == "true") {
			modelAndView = new ModelAndView("trainer/editprofile");

			try {
				StaffDatabase database = new StaffDatabase();
				Staff trainer = database.trainerprofile("T",
						request.getSession().getAttribute("login_user").toString());

				if (trainer != null) {
					modelAndView.addObject("email", trainer.getEmail());
					modelAndView.addObject("name", trainer.getName());
					modelAndView.addObject("surname", trainer.getSurname());
					modelAndView.addObject("phone_number", trainer.getMobile_number());
					modelAndView.addObject("address", trainer.getAddress());
					modelAndView.addObject("birthday", trainer.getDate());
					modelAndView.addObject("picture", trainer.getPicture());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			modelAndView = new ModelAndView(new RedirectView("/loginstaff", true));
		}
		return modelAndView;
	}
	
	public static boolean checkIfExists(String[] myStringArray, String stringToLocate) {
		for (String element : myStringArray) {
			if (element.equals(stringToLocate)) {
				return true;
			}
		}
		return false;
	}

	@RequestMapping(value = "/edittrainerprofileprocess", method = RequestMethod.POST)
	public String editTrainerProfileProcess(@RequestParam("image") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		try {
			StaffDatabase database = new StaffDatabase();
			String foldername = UPLOAD_DIRECTORY + request.getSession().getAttribute("login_user");
			new File(foldername).mkdirs();

			String filename = file.getOriginalFilename().toString();
			String extension = "";

			int control = 0;
			for (int i = 0; i < filename.length(); i++) {
				if (filename.charAt(i) == '.') {
					control++;
				} else if (control != 0) {
					extension += filename.charAt(i);
				}
			}

			control = 0;
			InputStream in = new ByteArrayInputStream(file.getBytes());
			BufferedImage image = ImageIO.read(in);

			if (image != null) {
				ImageIO.write(image, extension, new File(foldername + "\\" + filename));
				database.editprofileprocess(request.getSession().getAttribute("login_user").toString(),
						request.getParameter("name"), request.getParameter("surname"), request.getParameter("birthday"),
						request.getParameter("address"),
						"employee/" + request.getSession().getAttribute("login_user") + "/" + filename);
			} else {
				database.editprofileprocess(request.getSession().getAttribute("login_user").toString(),
						request.getParameter("name"), request.getParameter("surname"), request.getParameter("birthday"),
						request.getParameter("address"), null);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return "redirect:trainerprofile";
	}
	
	@RequestMapping(value = "/lessons")
	public ModelAndView lessons(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException, IOException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("trainer") == "true") {
			modelAndView = new ModelAndView("trainer/home");

			ArrayList<JSONObject> lessons = new ArrayList<JSONObject>();
			ArrayList<JSONObject> memberlist = new ArrayList<JSONObject>();
			ArrayList<JSONObject> numberlist = new ArrayList<JSONObject>();

			StaffDatabase database = new StaffDatabase();
			if (database.getLessons(request.getSession().getAttribute("login_user").toString())) {
				ArrayList<Lesson> lessonobjects = database.getLessons();
				ArrayList<Member> members = database.getMembers();
				ArrayList<Integer> number = database.getNumber();

				for (int j = 0; j < lessonobjects.size(); j++) {
					JSONObject lesson = new JSONObject();
					lesson.put("id", lessonobjects.get(j).getId());
					lesson.put("name", lessonobjects.get(j).getCoursename());
					lessons.add(lesson);
				}

				for (int j = 0; j < number.size(); j++) {
					JSONObject membersj = new JSONObject();
					membersj.put("id", members.get(j).getId());
					membersj.put("email", members.get(j).getEmail());
					membersj.put("phonenumber", members.get(j).getMobile_number());
					membersj.put("name", members.get(j).getName());
					membersj.put("surname", members.get(j).getSurname());
					membersj.put("birthday", members.get(j).getDate());
					membersj.put("address", members.get(j).getAddress());
					membersj.put("picture", members.get(j).getPicture());
					memberlist.add(membersj);

					JSONObject numbers = new JSONObject();
					numbers.put("number", number.get(j));
					numberlist.add(numbers);
				}
				modelAndView.addObject("lessons", lessons);
				modelAndView.addObject("members", memberlist);
				modelAndView.addObject("numbers", numberlist);
			}
		} else {
			modelAndView = new ModelAndView(new RedirectView("/loginstaff", true));
		}
		return modelAndView;
	}

	@RequestMapping(value = "/attend")
	public ModelAndView attend(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException, IOException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("trainer") == "true") {
			modelAndView = new ModelAndView("trainer/today");
			ArrayList<JSONObject> memberlist = new ArrayList<JSONObject>();
			StaffDatabase database = new StaffDatabase();
			ArrayList<Member> members = new ArrayList<Member>();
			members = database.getLessonsAttend(request.getSession().getAttribute("login_user").toString());

			if (members != null) {
				for (int j = 0; j < members.size(); j++) {
					JSONObject membersj = new JSONObject();
					membersj.put("id", members.get(j).getId());
					membersj.put("email", members.get(j).getEmail());
					membersj.put("phonenumber", members.get(j).getMobile_number());
					membersj.put("name", members.get(j).getName());
					membersj.put("surname", members.get(j).getSurname());
					membersj.put("birthday", members.get(j).getDate());
					membersj.put("address", members.get(j).getAddress());
					membersj.put("picture", members.get(j).getPicture());
					memberlist.add(membersj);
				}
			}
			modelAndView.addObject("members", memberlist);
			modelAndView.addObject("lessonid", database.getLessonId());
		} else {
			modelAndView = new ModelAndView(new RedirectView("/loginstaff", true));
		}
		return modelAndView;
	}

	@RequestMapping(value = "/attendprocess", method = RequestMethod.GET)
	public String attendProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		try {
			StaffDatabase database = new StaffDatabase();
			String lessonid=request.getParameter("lessonid").toString();
			ArrayList<String> members=database.getAllLessonMembers(lessonid);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.now();
			for(String m : members){
				String[] results = request.getParameterValues(m);
				database.memberAttendProcess(lessonid, m, results[0], dtf.format(localDate).toString());
				database.getAttendStatus(lessonid, m);
			}
			
		} 
		catch (Exception ex) {
			System.out.println(ex);
		}

		return "redirect:attend";
	}

	@RequestMapping(value = "/showclasstrainer")
	public ModelAndView showClass(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		ModelAndView modelAndView;
		LessonDatabase database = new LessonDatabase();
		Lesson l = database.getLesson(request.getParameter("id"));

		if (request.getSession().getAttribute("login_user") != null) {
			modelAndView = new ModelAndView("trainer/showlesson");
			modelAndView.addObject("id", l.getId());
			modelAndView.addObject("coursename", l.getCoursename());
			modelAndView.addObject("branchname", l.getBranchname());
			modelAndView.addObject("branchoffice", l.getBranchoffice());
			modelAndView.addObject("trainername", l.getTrainername());
			modelAndView.addObject("lessondate", l.getStartdate().toString());
			modelAndView.addObject("enddate", l.getEnddate().toString());
			modelAndView.addObject("lessondates", l.getDates());

		} else {
			modelAndView = new ModelAndView(new RedirectView("/", true));
		}
		return modelAndView;
	}

	@RequestMapping(value = "/viewprofiletrainer")
	public ModelAndView viewprofile(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("trainer").equals("true")) {
			modelAndView = new ModelAndView("trainer/viewprofile");

			try {
				Memberdb database = new Memberdb();
				Member user = database.profile("true", request.getParameter("email").toString());

				if (user != null) {
					modelAndView.addObject("email", user.getEmail());
					modelAndView.addObject("name", user.getName());
					modelAndView.addObject("surname", user.getSurname());
					modelAndView.addObject("phone_number", user.getMobile_number());
					modelAndView.addObject("address", user.getAddress());
					modelAndView.addObject("birthday", user.getDate());
					modelAndView.addObject("picture", user.getPicture());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			modelAndView = new ModelAndView(new RedirectView("/", true));
		}
		return modelAndView;
	}

	@RequestMapping(value = "/manipulatelessontrainer")
	public ModelAndView manipulatelesson(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException, IOException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("trainer") == "true") {
			modelAndView = new ModelAndView("trainer/manipulatelesson");

			StaffDatabase database = new StaffDatabase();
			if (database.getLessonsManipulate(request.getSession().getAttribute("login_user").toString())) {
				ArrayList<Lesson> lessonobjects = database.getLessons();
				ArrayList<JSONObject> lessons = new ArrayList<JSONObject>();
				for (Lesson l : lessonobjects) {
					JSONObject lesson = new JSONObject();
					lesson.put("id", l.getId());
					lesson.put("coursename", l.getCoursename().toString());
					lesson.put("branchname", l.getBranchname().toString());
					lesson.put("startdate", l.getStartdate().toString());
					lesson.put("enddate", l.getEnddate().toString());
					lesson.put("dates", l.getDates().toString());
					lessons.add(lesson);
				}

				modelAndView.addObject("lessons", lessons);
			}
		} else {
			modelAndView = new ModelAndView(new RedirectView("/loginstaff", true));
		}
		return modelAndView;
	}
}