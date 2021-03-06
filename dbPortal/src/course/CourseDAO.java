package course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.DatabaseUtil;

public class CourseDAO {
	String usrID;
	public CourseDAO(String usrID) {
		this.usrID = usrID;
	}
	public CourseDAO() {
		
	}
	
	public ArrayList<CourseDTO> enrolled(String usrID, int year, int semester){
		ArrayList<CourseDTO> enrolledList = null;
		String SQL = "SELECT * FROM Enroll NATURAL JOIN Teach NATURAL JOIN Course WHERE Enroll.studentID = ? AND Enroll.year = ? AND Enroll.semester = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, usrID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, semester);
			
			rs = pstmt.executeQuery();
			enrolledList = new ArrayList<CourseDTO>();
			while(rs.next()) {
				CourseDTO course = new CourseDTO(
					rs.getInt(2),
					rs.getInt(3),
					rs.getInt(1),
					rs.getInt(4),
					rs.getString(5),
					rs.getString(12),
					rs.getString(13),
					rs.getInt(14),
					rs.getInt(15),
					rs.getInt(16),
					rs.getString(17),
					rs.getString(6),
					rs.getString(7),
					rs.getString(8),
					rs.getString(9),
					rs.getString(10),
					rs.getInt(11)
				);
				enrolledList.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try{if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
			try{if(rs != null) rs.close();} catch(Exception e) {e.printStackTrace();}
		}
		
		return enrolledList;
	}
	
	public ArrayList<CourseDTO> exceptEnroll(int year, int semester, ArrayList<CourseDTO> courseList){
		ArrayList<CourseDTO> enrolledCourse = enrolled(this.usrID, year, semester);
		ArrayList<CourseDTO> result = new ArrayList<CourseDTO>();
		if(enrolledCourse.size()==0) {
			return courseList;
		}
		for(int i=0;i<courseList.size();i++) {
			for(int j=0;j<enrolledCourse.size();j++) {
				if(enrolledCourse.get(j).getCourseNo() == courseList.get(i).getCourseNo()) {
					break;
				} else {
					if(j == enrolledCourse.size()-1)
						result.add(courseList.get(i));
				}
			}
		}
		
		return result;
	}
	
	public ArrayList<CourseDTO> getCourse(int year, int semester){
		ArrayList<CourseDTO> enrolledList = null;
		String SQL = "SELECT * FROM Teach NATURAL JOIN Course WHERE Enroll.year = ? AND Enroll.semester = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, year);
			pstmt.setInt(2, semester);
			
			rs = pstmt.executeQuery();
			enrolledList = new ArrayList<CourseDTO>();
			while(rs.next()) {
				CourseDTO course = new CourseDTO(
						rs.getInt(7),
						rs.getInt(8),
						rs.getInt(1),
						rs.getInt(9),
						null,
						rs.getString(11),
						rs.getString(12),
						rs.getInt(13),
						rs.getInt(14),
						rs.getInt(15),
						rs.getString(16),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getInt(10)
				);
				enrolledList.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try{if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
			try{if(rs != null) rs.close();} catch(Exception e) {e.printStackTrace();}
		}
		
		return enrolledList;
	}
	
	public ArrayList<CourseDTO> getMajorCourse(String major, int year, int semester){
		ArrayList<CourseDTO> courseList = new ArrayList<CourseDTO>();
		String SQL = "SELECT * FROM Teach NATURAL JOIN Course WHERE Teach.year = ? AND Teach.semester = ? AND Course.category LIKE '전공%' AND Course.major = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, year);
			pstmt.setInt(2, semester);
			pstmt.setString(3, major);
			
			rs = pstmt.executeQuery();
			courseList = new ArrayList<CourseDTO>();
			while(rs.next()) {
				CourseDTO course = new CourseDTO(
						rs.getInt(7),
						rs.getInt(8),
						rs.getInt(1),
						rs.getInt(9),
						null,
						rs.getString(11),
						rs.getString(12),
						rs.getInt(13),
						rs.getInt(14),
						rs.getInt(15),
						rs.getString(16),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getInt(10)					
					);
					courseList.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try{if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
			try{if(rs != null) rs.close();} catch(Exception e) {e.printStackTrace();}
		}
		
		return exceptEnroll(year, semester, courseList);
		
	}
	
	public ArrayList<CourseDTO> getGenCourse(int year, int semester){
		ArrayList<CourseDTO> courseList = null;
		String SQL = "SELECT * FROM Teach NATURAL JOIN Course WHERE Teach.year = ? AND Teach.semester = ? AND Course.category LIKE '교양%'";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, year);
			pstmt.setInt(2, semester);
			
			rs = pstmt.executeQuery();
			courseList = new ArrayList<CourseDTO>();
			while(rs.next()) {
				CourseDTO course = new CourseDTO(
						rs.getInt(7),
						rs.getInt(8),
						rs.getInt(1),
						rs.getInt(9),
						null,
						rs.getString(11),
						rs.getString(12),
						rs.getInt(13),
						rs.getInt(14),
						rs.getInt(15),
						rs.getString(16),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getInt(10)					
					);
					courseList.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try{if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
			try{if(rs != null) rs.close();} catch(Exception e) {e.printStackTrace();}
		}
		
		return exceptEnroll(year, semester, courseList);
		
	}
	
	public ArrayList<CourseDTO> getPfCourse(int year, int semester){
		ArrayList<CourseDTO> courseList = null;
		String SQL = "SELECT * FROM Teach NATURAL JOIN Course WHERE Teach.year = ? AND Teach.semester = ? AND Course.PF = 1";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, year);
			pstmt.setInt(2, semester);
			
			rs = pstmt.executeQuery();
			courseList = new ArrayList<CourseDTO>();
			while(rs.next()) {
				CourseDTO course = new CourseDTO(
						rs.getInt(7),
						rs.getInt(8),
						rs.getInt(1),
						rs.getInt(9),
						null,
						rs.getString(11),
						rs.getString(12),
						rs.getInt(13),
						rs.getInt(14),
						rs.getInt(15),
						rs.getString(16),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getInt(10)						
					);
					courseList.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try{if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
			try{if(rs != null) rs.close();} catch(Exception e) {e.printStackTrace();}
		}
		
		return exceptEnroll(year, semester, courseList);
	}
	public ArrayList<CourseDTO> getCyberCourse(int year, int semester){
		ArrayList<CourseDTO> courseList = null;
		String SQL = "SELECT * FROM Teach NATURAL JOIN Course WHERE Teach.year = ? AND Teach.semester = ? AND Course.cyber = 1";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, year);
			pstmt.setInt(2, semester);
			
			rs = pstmt.executeQuery();
			courseList = new ArrayList<CourseDTO>();
			while(rs.next()) {
				CourseDTO course = new CourseDTO(
						rs.getInt(7),
						rs.getInt(8),
						rs.getInt(1),
						rs.getInt(9),
						null,
						rs.getString(11),
						rs.getString(12),
						rs.getInt(13),
						rs.getInt(14),
						rs.getInt(15),
						rs.getString(16),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getInt(10)						
					);
					courseList.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try{if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
			try{if(rs != null) rs.close();} catch(Exception e) {e.printStackTrace();}
		}
		
		return exceptEnroll(year, semester, courseList);
	}
	
	public ArrayList<CourseDTO> getList(String category, String major, String search, int pageNumber){
		if(category.equals("전체")) {
			category = "";
		}
		if(major.equals("전체")) {
			major = "";
		}
		ArrayList<CourseDTO> courseList = null;
		String SQL = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
			try {
				SQL = "SELECT * FROM Course WHERE category LIKE ? "
						+ "AND major LIKE ? AND courseName LIKE ? ORDER BY courseNo DESC LIMIT " + pageNumber * 10 + ", " + pageNumber * 10 + 11;
				conn = DatabaseUtil.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1,  "%" + category + "%");
				pstmt.setString(2, "%" + major + "%");
				pstmt.setString(3, "%" + search + "%");
				rs = pstmt.executeQuery();
				courseList = new ArrayList<CourseDTO>();
				while(rs.next()) {
					CourseDTO course = new CourseDTO(
							rs.getString(1),
							rs.getString(2),
							rs.getInt(3),
							rs.getInt(4),
							rs.getInt(5),
							rs.getString(6),
							rs.getInt(7));
					courseList.add(course);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try{if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
				try{if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
				try{if(rs != null) rs.close();} catch(Exception e) {e.printStackTrace();}
			}
			
		return courseList; // 실패하면 null값이 들어가 있을 것
	}
	
	public int write(CourseDTO courseDTO) {
		String SQL = "INSERT INTO COURSE VALUES (?, ?, ?, ?, ?, ?, NULL)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if(check(courseDTO.getCourseName()) == 0) {
			try {
				conn = DatabaseUtil.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, courseDTO.getCategory());
				pstmt.setString(2, courseDTO.getMajor());
				pstmt.setInt(3, courseDTO.getCredit());
				pstmt.setInt(4, courseDTO.getCyber());
				pstmt.setInt(5, courseDTO.getPF());
				pstmt.setString(6, courseDTO.getCourseName());
				return pstmt.executeUpdate(); //
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try{if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
				try{if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
				try{if(rs != null) rs.close();} catch(Exception e) {e.printStackTrace();}
			}
		}
		else
			return -2;
		return -1; 
	}
	
	public int dropCourse(String studentID, int courseNo, int classNo, int year, int semester) {
		String SQL = "DELETE FROM Enroll WHERE studentID = ? AND year = ? AND semester = ? AND courseNo = ? AND classNo = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, studentID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, semester);
			pstmt.setInt(4, courseNo);
			pstmt.setInt(5, classNo);
			return pstmt.executeUpdate(); //
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try{if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
		}
		return -1;
	}
	
	// 시간 비교 함수
	public boolean compareDay(String day1, String day2) {
		System.out.println(day1);
		System.out.println(day2);
		for(int i=0;i<day1.length();i++) {
			for(int j=0;j<day2.length();j++) {
				if(day1.charAt(i) == day2.charAt(j)) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean compareTime(int startTime1, int endTime1, int startTime2, int endTime2) {
		if(startTime1 > endTime2 || endTime1 < startTime2)
			return false;
		return true;
	}
	
	public int insert(String studentID, int courseNo, int classNo, int year, int semester) {
		ArrayList<CourseDTO> enrolledList = enrolled(studentID, year, semester);
		String SQL2 = "SELECT day, startTime, endTime FROM Teach WHERE courseNo = ? AND classNo = ? AND year = ? AND semester = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String day = null; int startTime = 0, endTime = 0;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL2);
			pstmt.setInt(1, courseNo);
			pstmt.setInt(2, classNo);
			pstmt.setInt(3, year);
			pstmt.setInt(4, semester);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				day = rs.getString(1);
				startTime = Integer.parseInt(rs.getString(2));
				endTime = Integer.parseInt(rs.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try{if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
		}
		for(int i=0;i<enrolledList.size();i++) {
			if(compareDay(enrolledList.get(i).getDay(), day)) {
				if(compareTime(Integer.parseInt(enrolledList.get(i).getStartTime()), Integer.parseInt(enrolledList.get(i).getEndTime()), startTime, endTime))
					return -2;
			}
		}
		
		String SQL = "INSERT INTO Enroll (studentID, year, semester, courseNo, classNo) VALUES (?,?,?,?,?)";
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, studentID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, semester);
			pstmt.setInt(4, courseNo);
			pstmt.setInt(5, classNo);
			return pstmt.executeUpdate(); //
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try{if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
		}
		return -1;
	}
	
	public static int check(String courseName) {
		String SQL = "SELECT courseName, courseNo FROM course WHERE courseName = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, courseName);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getString(1) != null) {
					return rs.getInt(2);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		};
		return 0;
	}
	
	//수정하기
	public int modify(CourseDTO courseDTO) {
		String SQL = "UPDATE Course SET category = ?, major = ?, credit = ?, cyber = ?,PF = ?, courseName = ? WHERE courseNo = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int checkNum = check(courseDTO.getCourseName());
		if(checkNum == 0 || checkNum == courseDTO.courseNo) {
			try {
				conn = DatabaseUtil.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, courseDTO.getCategory());
				pstmt.setString(2, courseDTO.getMajor());
				pstmt.setInt(3, courseDTO.getCredit());
				pstmt.setInt(4, courseDTO.getCyber());
				pstmt.setInt(5, courseDTO.getPF());
				pstmt.setString(6, courseDTO.getCourseName());
				pstmt.setInt(7, courseDTO.getCourseNo());
				return pstmt.executeUpdate(); //
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try{if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
				try{if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
				try{if(rs != null) rs.close();} catch(Exception e) {e.printStackTrace();}
			}
		}
		else
			return -2;
		return -1; 
	}
		
	public int delete(String courseNo) {
		String SQL = "DELETE FROM Course WHERE courseNo = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,Integer.parseInt(courseNo));
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1; //데이터베이스 오류
	}
	
	//강의 게시글 가져오기
	public CourseDTO viewCourse(int courseNo){
		String SQL = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
			try {
				SQL = "SELECT * FROM Course WHERE courseNo = ?";
				conn = DatabaseUtil.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, courseNo);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					CourseDTO course = new CourseDTO(
							rs.getString(1),
							rs.getString(2),
							rs.getInt(3),
							rs.getInt(4),
							rs.getInt(5),
							rs.getString(6),
							rs.getInt(7));
					return course;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try{if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
				try{if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}
				try{if(rs != null) rs.close();} catch(Exception e) {e.printStackTrace();}
			}
			
		return null;
	}
	
}
