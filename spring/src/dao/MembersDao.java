package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.MembersDto;

public class MembersDao {

	// singleton
	// MemberDao 내부에서 1개의 객체를 미리 생성해 두고,
	// getInstantce() 메소드를 통해서 외뷍서 사용할 수 있도록 처리
	private static MembersDao membersDao = new MembersDao();
	private MembersDao() {}
	public static MembersDao getInstantce() {
		return membersDao;
	}
	// getInstatnce() 메소드 호출방법
	// 클래스 메소드는 클래스로 호출한다.
	// MembersDao dao = MembersDao.getInstantce();

	// field
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private String sql;
	private int result;

	// 접속과 접속해제
	private Connection getConnection() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");		// ClassNotFoundException
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String user = "spring";
		String password = "1111";
		return DriverManager.getConnection(url, user, password);// SQLException
	} // getConnection()
	
	private void close(Connection con, PreparedStatement ps, ResultSet rs) {
		try {
			if(rs != null)
				rs.close();
			if(ps != null)
				ps.close();
			if(con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // close(con,ps,rs);
	
	// 가입
	// (부가 : 같은 아이디, 같은 이메일은 가입을 미리 방지
	public int insertMembers(MembersDto dto) { // dto(mId, mName, mEmail)
		
		if(selectMembers_mId(dto.getmId()) != null) {
			System.out.println("이미 사용중인 아이디입니다");
			return 0;
		}
				
		if(selectMembers_mEmail(dto.getmEmail()) != null) {
			System.out.println("이미 사용중인 이메일입니다");
			return 0;
		}
		
		try {
			con = getConnection();
			sql = "INSERT INTO members (mno,mid,mname,memail,mdate) "
					+ "VALUES (members_seq.NEXTVAL,?,?,?,SYSDATE)";
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getmId());
			ps.setString(2, dto.getmName());
			ps.setString(3, dto.getmEmail());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con,ps,null);
		}
		return result;
	}
	
	// 탈퇴
	public int deleteMembers(String mId) {
		try {
			con = getConnection();
			sql = "DELETE FROM members WHERE mid = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, mId);
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con,ps,null);
		}
		return result;
	} // deleteMemebers(mId)
	
	// 회원정보수정
	// 수정 : mName, mEmail
	public int updateMembers(MembersDto dto) {
		try {
			con = getConnection();
			sql = "UPDATE members SET mname = ?, memail = ? WHERE mid = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getmName());
			ps.setString(2, dto.getmEmail());
			ps.setString(3, dto.getmId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con,ps,null);
		}
		return result;
	} // updateMembers(dto)
	
	// 아이디 찾기
	public String selectMembers_mEmail(String mEmail) {
		String mId = null;
		try {
			con = getConnection();
			sql = "SELECT mid FROM members WHERE memail = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, mEmail);
			rs = ps.executeQuery();
			if(rs.next())
				mId = rs.getString(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps, rs);
		}
		return mId;
	}
	
	// 회원번호에 의한 검색
	public String selectMembers(long mNo) {
		String mId = null;
		try {
			con = getConnection();
			sql = "SELECT mid FROM members WHERE mno = ?";
			ps = con.prepareStatement(sql);
			ps.setLong(1, mNo);
			rs = ps.executeQuery();
			if(rs.next())
				mId = rs.getString(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps, rs);
		}
		return mId;
	}
	
	// 회원아이디에 의한 검색
	public MembersDto selectMembers_mId(String mId) {
		MembersDto dto = null;
		try {
			con = getConnection();
			sql = "SELECT mno,mname,memail,mdate FROM members WHERE mid = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, mId);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new MembersDto();
				dto.setmId(mId);
				dto.setmNo(rs.getLong(1));
				dto.setmName(rs.getString(2));
				dto.setmEmail(rs.getString(3));
				dto.setmDate(rs.getDate(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps, rs);
		}
		return dto;
	}
	
	// 전체 검색
	public List<MembersDto> select_list () {
		List<MembersDto> list = new ArrayList<MembersDto>();
		try {
			con = getConnection();
			sql = "SELECT mno,mid,mname,memail,mdate FROM members";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				MembersDto dto = new MembersDto();
				dto.setmNo(rs.getLong(1));
				dto.setmId(rs.getString(2));
				dto.setmName(rs.getString(3));
				dto.setmEmail(rs.getString(4));
				dto.setmDate(rs.getDate(5));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
