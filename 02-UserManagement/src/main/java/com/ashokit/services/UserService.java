package com.ashokit.services;

import java.util.Map;
import com.ashokit.dto.LoginDTO;
import com.ashokit.dto.RegisterDTO;
import com.ashokit.dto.ResetPwdDTO;
import com.ashokit.dto.UserDTO;

public interface UserService {

	public Map<Integer, String> getCountries();

	public Map<Integer, String> getStates(int cid);

	public Map<Integer, String> getCities(int sid);

	public UserDTO getUser(String email);

	public boolean registerUser(RegisterDTO regDTO);

	public UserDTO getUser(LoginDTO lohinDTO);

	public boolean resetPwd(ResetPwdDTO pwdDTO);

	public String getQuote(); // api calls

}
