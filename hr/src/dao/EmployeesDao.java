package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.EmployeesDto;

/*
 *  부서번호 (10~110)를 입력 받아서,
 *  해당 부서에 근무하는 사원들의 정보를 조회하시오
 *  (이름, 부서명, 연봉, 입사일)
 */

public class EmployeesDao {
	// single-ton
	private static EmployeesDao employeeDao = new EmployeesDao();
	private EmployeesDao() {}
	public static EmployeesDao getInstance() {
		return employeeDao;
	}
	
	// field
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	// method
	private Connection getConnection() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String user = "HR";
		String password = "1111";
		return DriverManager.getConnection(url, user, password);
	} // getConnection()
	
	private void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if(rs != null)
				rs.close();
			if(ps != null)
				ps.close();
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // close(conn,ps,rs)
	
	// 부서별 조회
	public List<EmployeesDto> selectEmployeesByDepartmentId(int department_id){
		List<EmployeesDto> list = new ArrayList<EmployeesDto>();
		try {
			conn = getConnection();
			String sql = "SELECT e.first_name "
					  +       ", e.last_name "
				  	  +       ", d.department_name AS 부서명 "
				  	  +       ", e.salary AS 연봉 "
				  	  +       ", e.hire_date AS 입사일 "
	    			  +    "FROM departments d RIGHT OUTER JOIN employees e "
	      			  +      "ON d.department_id = e.department_id "
	      			  +   "WHERE e.department_id = ? "
	      			  +   "ORDER BY e.employee_id ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, department_id);
			rs = ps.executeQuery();
			while(rs.next()) {
				EmployeesDto dto = new EmployeesDto();
				dto.setFirst_name(rs.getString(1));
				dto.setLast_name(rs.getString(2));
				dto.setDepartment_name(rs.getString(3));
				dto.setSalary(rs.getDouble(4));
				dto.setHire_date(rs.getDate(5));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, ps, rs);
		}
		return list;
	} // selectEmployeeList(start_id,end_id)
	
	
}
