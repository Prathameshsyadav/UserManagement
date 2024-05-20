package com.ashokit.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ashokit.dto.LoginDTO;
import com.ashokit.dto.QuoteDTO;
import com.ashokit.dto.RegisterDTO;
import com.ashokit.dto.ResetPwdDTO;
import com.ashokit.dto.UserDTO;
import com.ashokit.entity.CityEntity;
import com.ashokit.entity.CountryEntity;
import com.ashokit.entity.StateEntity;
import com.ashokit.entity.UserEntity;
import com.ashokit.repo.CityRepo;
import com.ashokit.repo.CountryRepo;
import com.ashokit.repo.StateRepo;
import com.ashokit.repo.UserRepo;
import com.ashokit.utils.EmailUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private CityRepo cityRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private StateRepo stateRepo;
	@Autowired
	private CountryRepo countryRepo;
	@Autowired
	private EmailUtils emailUtils;
	
	QuoteDTO[] quotation = null;
	
	@Override
	public Map<Integer, String> getCountries() {
		// TODO Auto-generated method stub
		Map<Integer, String> map = new HashMap<>();
		
		List<CountryEntity> listCountries = countryRepo.findAll();
		
		listCountries.forEach(c -> {
			map.put(c.getCountryId(), c.getCountryName());
		});
		
		return map;
	}

	@Override
	public Map<Integer, String> getStates(int cid) {
		// TODO Auto-generated method stub
		
		Map<Integer, String> statesMap = new HashMap<>();
		
	/*	
		CountryEntity country = new CountryEntity();
		country.setCountryId(cid);
		
		StateEntity states = new StateEntity();
		states.setCountry(country);
		
		Example<StateEntity> of = Example.of(states);
		
		List<StateEntity> stateList = stateRepo.findAll(of);
		
		stateList.forEach(s -> {
			statesMap.put(s.getStateId(), s.getStateName() );

		});
		*/
		
		List<StateEntity> stateList = stateRepo.findByCountryId(cid);
		stateList.forEach(s -> {
			statesMap.put(s.getStateId(), s.getStateName() );

		});
		
		return statesMap;
	}

	@Override
	public Map<Integer, String> getCities(int sid) {
		// TODO Auto-generated method stub
		Map<Integer, String> cityMap = new HashMap<>();
		
		List<CityEntity> cityList = cityRepo.findCityByStateId(sid);
		
		cityList.forEach(c -> {
			cityMap.put(c.getCityId(), c.getCityName()  );
		});
		
		return cityMap;
	}

	@Override
	public UserDTO getUser(String email) {
		// TODO Auto-generated method stub
		UserEntity userEntity = userRepo.findByEmail(email);
	/*	UserDTO dto = new UserDTO();
		BeanUtils.copyProperties(user, dto);*/
		
		if(userEntity == null) {
			return null;
		}
		
		ModelMapper mapper = new ModelMapper();
		UserDTO dto = mapper.map(userEntity, UserDTO.class);
		
		return dto;
	}

	@Override
	public boolean registerUser(RegisterDTO regDTO) {
		// TODO Auto-generated method stub
		ModelMapper mapper = new ModelMapper();
		UserEntity userEntity = mapper.map(regDTO, UserEntity.class);
		
		CountryEntity countryEntity = countryRepo.findById(regDTO.getCountryId()).orElseThrow();
		
		StateEntity stateEntity = stateRepo.findById(regDTO.getStateId()).orElseThrow();
		
		CityEntity cityEntity = cityRepo.findById(regDTO.getCityId()).orElseThrow();
		
		userEntity.setCountry(countryEntity);
		userEntity.setState(stateEntity);
		userEntity.setCity(cityEntity);
		
		userEntity.setPwd(generateRandom());
		userEntity.setPwdUpdate("NO");
		
		UserEntity save = userRepo.save(userEntity);
		
		String subject = "User Registration";
		
		String body = "Your password is : "+userEntity.getPwd();
		
		emailUtils.sendEmail(regDTO.getEmail(), subject, body);
		
		return save.getUserId()!=null;
	}

	@Override
	public UserDTO getUser(LoginDTO loginDTO) {
		// TODO Auto-generated method stub
		
		UserEntity userEntity = 
				userRepo.findByEmailAndPwd(loginDTO.getEmail(), loginDTO.getPwd());
		
		if(userEntity == null) {
			return null;
		}
		System.out.println(userEntity);
		ModelMapper mapper = new ModelMapper();
		UserDTO userDTO = mapper.map(userEntity, UserDTO.class);
		System.out.println(userDTO);
		return userDTO;
	}

	@Override
	public boolean resetPwd(ResetPwdDTO pwdDTO) {
		// TODO Auto-generated method stub
		
		UserEntity userEntity = 
				userRepo.findByEmailAndPwd(pwdDTO.getEmail(), pwdDTO.getOldPwd());
		if(userEntity != null) {
			userEntity.setPwd(pwdDTO.getNewPwd());
			userEntity.setPwdUpdate("YES");
			userRepo.save(userEntity);
			return true;
		}
		
		return false;
		
	}

	@Override
	public String getQuote() {
		// TODO Auto-generated method stub
		if(quotation == null) {
			String url = "https://type.fit/api/quotes";
			//web service call
			RestTemplate rt = new RestTemplate();
			ResponseEntity<String> forEntity = rt.getForEntity(url, String.class);
			String body = forEntity.getBody();
			ObjectMapper mapper = new ObjectMapper();
			try {
				 quotation = mapper.readValue(body, QuoteDTO[].class);
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Random r = new Random();
		int nextInt = r.nextInt(quotation.length-1);
		return quotation[nextInt].getText();
	}
	
	private static String generateRandom() {
		String atoZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		
		Random rand = new Random();
		StringBuilder res = new StringBuilder();
		for(int i = 0; i < 5; i++) {
			int randIndex = rand.nextInt(atoZ.length());
			res.append(atoZ.charAt(randIndex));
		}
		return res.toString();
	}

}
