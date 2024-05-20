package com.ashokit.dto;

public class UserDTO {
	
	private Integer userId;
	private String name;
	private String email;
	private String phNo;
	private String pwd;
	private String pwdUpdate;
	private String newPwd;
	private String confirmPwd;
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhNo() {
		return phNo;
	}
	public void setPhNo(String phNo) {
		this.phNo = phNo;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPwdUpdate() {
		return pwdUpdate;
	}
	public void setPwdUpdate(String pwdUpdate) {
		this.pwdUpdate = pwdUpdate;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public String getConfirmPwd() {
		return confirmPwd;
	}
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	public Integer getStateId() {
		return stateId;
	}
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", name=" + name + ", email=" + email + ", phNo=" + phNo + ", pwd=" + pwd
				+ ", pwdUpdate=" + pwdUpdate + ", newPwd=" + newPwd + ", confirmPwd=" + confirmPwd + ", countryId="
				+ countryId + ", stateId=" + stateId + ", cityId=" + cityId + "]";
	}
	
	
	
	
	

}
