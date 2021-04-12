package main;

import java.util.List;
import java.util.Scanner;

import dao.EmployeesDao;
import dto.EmployeesDto;

public class Handler {

	// field
	private EmployeesDao dao = EmployeesDao.getInstance();
	private Scanner sc = new Scanner(System.in);
	
	// method
	private void menu() {
		System.out.println("--- HR TABLE");
		System.out.println("| 0.프로그램 종료");
		System.out.println("| 1.부서검색");
		System.out.print("입력 >> ");
	}
	
	public void execute() {
		while(true) {
			menu();
			switch (sc.nextInt()) {
			case 0: System.out.println("[프로그램 종료]"); return;
			case 1: departments_search(); break;
			default:
				break;
			}
			
		}
	}
	
	private void departments_search() {
		System.out.println("[부서검색]");
		System.out.print("Department_id(10~110) >> ");
		int department_id = sc.nextInt();
		List<EmployeesDto> list = dao.selectEmployeesByDepartmentId(department_id);
		System.out.println("총 사원 수: " + list.size());
		if(list.size() == 0) {
			System.out.println("검색된 사원이 없습니다");
			return;
		}
		for (EmployeesDto dto : list) {
			System.out.println(dto);
		}
	}
	
}
