package dto;

import java.sql.Date;

public class MembersDto {

	// field : members 테이블의 칼럼과 1:1 매칭

	private long mNo;
	private String mId;
	private String mName;
	private String mEmail;
	private Date mDate;
	
	// constructor
	// 1. 안 만든다. (디폴트 생성자)
	// 2. 2개 만든다. (디폴트 생성자 + 필드 사용)
	public MembersDto() {}
	public MembersDto(long mNo, String mId, String mName, String mEmail, Date mDate) {
		super();
		this.mNo = mNo;
		this.mId = mId;
		this.mName = mName;
		this.mEmail = mEmail;
		this.mDate = mDate;
	}

	// method
	// getter / setter
	public long getmNo() {
		return mNo;
	}
	public void setmNo(long mNo) {
		this.mNo = mNo;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public Date getmDate() {
		return mDate;
	}
	public void setmDate(Date mDate) {
		this.mDate = mDate;
	}
	public String getmEmail() {
		return mEmail;
	}
	public void setmEmail(String mEmail) {
		this.mEmail = mEmail;
	}
	
	// System.out.println()을 통해서 객체 값을 확인하기 위해서
	// toString() 메소드를 오버라이드 해 둔다
	@Override
	public String toString() {
		return "[mNo=" + mNo + ", mId=" + mId + ", mName=" + mName + ", mDate=" + mDate + "]";
	}
}
