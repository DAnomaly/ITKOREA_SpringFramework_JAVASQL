package main;

import java.util.List;
import java.util.Scanner;

import dao.MembersDao;
import dto.MembersDto;

public class MembersHandler {

	// field
	private MembersDao dao = MembersDao.getInstantce();
	private Scanner sc = new Scanner(System.in);
	
	// method
	private void menu() {
		System.out.println("=====회원관리=====");
		System.out.println("0.프로그램 종료");
		System.out.println("1.가입");
		System.out.println("2.탈퇴");
		System.out.println("3.수정");
		System.out.println("4.검색");
		System.out.println("==================");
	} // menu()
	
	public void excute() {
		while(true) {
			menu();
			System.out.print("선택(0~) >>> ");
			switch (sc.nextInt()) {
			case 0: System.out.println("[프로그램 종료]"); return;
			case 1: join(); break;
			case 2: leave(); break;
			case 3: edit(); break;
			case 4: findID(); break;
			case 5: selectMembers(); break;
			default: System.out.println("잘못된 선택입니다. 다시 입력하세요.");
			}
		}
	} // excute()

	public void join() { // 선택 1
		System.out.println("[가입]");
		System.out.print("아이디 >> ");
		String mId = sc.next();
		System.out.print("사용자명 >> ");
		String mName = sc.next();
		System.out.print("이메일 >> ");
		String mEmail = sc.next();
	
		MembersDto dto = new MembersDto();
		dto.setmId(mId);
		dto.setmName(mName);
		dto.setmEmail(mEmail);
		
		int result = dao.insertMembers(dto);
		if(result > 0)
			System.out.println(mId + "님이 가입되었습니다");
		else
			System.out.println(mId + "님의 가입이 실패했습니다");
		
	} // join()
	
	public void leave() { // 선택 2
		System.out.println("[탈퇴]");
		System.out.print("아이디 >> ");
		String mId = sc.next();
		
		System.out.println("탈퇴할까요?(y/n)");
		System.out.print(">> ");
		String yn = sc.next();
		if(yn.equalsIgnoreCase("y")) {
			int result = dao.deleteMembers(mId);
			if(result > 0)
				System.out.println(mId + "님이 탈퇴되었습니다.");
			else {
				System.out.println(mId + "님이 탈퇴되지 않았습니다");
			}
		} else {
			System.out.println("[탈퇴취소]");
		}
	}
	
	public void edit() { // 선택 3
		System.out.println("[수정]");
		System.out.print("아이디 >> ");
		String mId = sc.next();
		System.out.print("이름 >> ");
		String mName = sc.next();
		System.out.print("이메일 >> ");
		String mEmail = sc.next();
		
		MembersDto dto = new MembersDto();
		dto.setmId(mId);
		dto.setmName(mName);
		dto.setmEmail(mEmail);
		
		int result = dao.updateMembers(dto);
		if(result > 0)
			System.out.println(mId + "님의 정보가 수정되었습니다");
		else
			System.out.println(mId + "님의 정보가 수정되지 않았습니다");
	}
	
	// 아이디 찾기(FROM 이메일)
	public void findID() { // 선택 4
		System.out.println("[검색]");
		System.out.print("가입 이메일 >> ");
		String mEmail = sc.next();
		String mId = dao.selectMembers_mEmail(mEmail);
		if(mId != null)
			System.out.println("아이디 : " + mId);
		else
			System.out.println("일치하는 정보가 없습니다");
	}
	
	public void selectMembers() { // 선택 5
		List<MembersDto> list = dao.select_list();
		for (MembersDto dto : list) {
			System.out.println(dto);
		}
	}
}
