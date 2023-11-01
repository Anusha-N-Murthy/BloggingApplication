package com.codewithsaurabh.blog_app_apis.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codewithsaurabh.blog_app_apis.exceptions.ApiException;
import com.codewithsaurabh.blog_app_apis.payloads.JwtAuthRequest;
import com.codewithsaurabh.blog_app_apis.payloads.JwtAuthResponse;
import com.codewithsaurabh.blog_app_apis.payloads.UserDto;
import com.codewithsaurabh.blog_app_apis.security.JwtTokenHelper;
import com.codewithsaurabh.blog_app_apis.services.UserService;
//@CrossOrigin("http://localhost:3000")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth/")
public class AuthController {
//LoginController

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		System.err.println("In AuthController -> createToken!!!!!!!!!!!!!!!!");
		String userName = null;
if(request.getUsername() != null){
	userName = request.getUsername();
}
		this.authenticate(request.getUsername(), request.getPassword());

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

		String generatedToken = this.jwtTokenHelper.generateToken(userDetails);
		UserDto userDto = this.userService.getUserIdByUsername(userName);	
		System.err.println("In AuthController -> generatedToken - "+generatedToken);
		System.err.println("In AuthController -userID - "+userDto.getUserId());
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setToken(generatedToken);
		
		jwtAuthResponse.setUser(userDto);
		

		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);
		
//		
	}

	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				username, password);
		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (BadCredentialsException ex) {
			System.out.println("Invalid username or password!");
			throw new ApiException("Invalid username or password!");
		}
	}

//	UserDto registerNewUser(UserDto userDto)
//	register new user Api
	@PostMapping("/register")
	public ResponseEntity<UserDto> registeruser(@RequestBody UserDto userDto) {
		UserDto registeredUser = this.userService.registerNewUser(userDto);

		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
	}

}
