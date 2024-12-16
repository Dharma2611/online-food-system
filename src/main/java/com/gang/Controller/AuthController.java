package com.gang.Controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gang.ConfigSequrity.JwtProvider;
import com.gang.Entity.Cart;
import com.gang.Entity.User;
import com.gang.Entity.User_Role;
import com.gang.Repo.CartRepository;
import com.gang.Repo.UserRepository;
import com.gang.Request.LoginRequest;
import com.gang.Service.CustomerUserDetailsService;
import com.gang.response.AuthRespose;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired

	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	@Autowired
	private CartRepository cartRepository;
	@PostMapping("/signup")
	public ResponseEntity<AuthRespose>createUserHandler(@RequestBody User user) throws Exception{

		User byEmail = userRepository.findByEmail(user.getEmail());
		if(byEmail!=null) {
			throw new Exception("Email is already with Another User");
			
		}
		User createUser= new User();
		createUser.setEmail(user.getEmail());
		createUser.setFullName(user.getFullName());
		createUser.setRole(user.getRole());
		createUser.setPassword(passwordEncoder.encode(user.getPassword()));
		User saveUser= userRepository.save(createUser);
		Cart cart = new Cart();
		cart.setCustomer(saveUser);
		cartRepository.save(cart);
		
		Authentication authentication= new  UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		AuthRespose authRespose= new AuthRespose();
		authRespose.setJwt(jwt);
		authRespose.setMsg("Register success");
		authRespose.setRole(saveUser.getRole());
		System.out.println(saveUser.getRole());
		
		return new ResponseEntity<AuthRespose>(authRespose,HttpStatus.CREATED);
		
	}
	
	@PostMapping("/signin")
	
	public ResponseEntity<AuthRespose> signIn(@RequestBody LoginRequest loginRequest) throws Exception{
		
		
		String username= loginRequest.getEmail();
		String password=loginRequest.getPassword();
		Authentication authentication = Authenticate(username,password);
		
		Collection<?extends GrantedAuthority> authorities= authentication.getAuthorities();
	    System.out.println("Authorities: " + authorities);
		String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
	
		
		
		String jwt = jwtProvider.generateToken(authentication);
		AuthRespose authRespose= new AuthRespose();
//		authRespose.setPassword(password);
		authRespose.setJwt(jwt);
		authRespose.setMsg("login success");
		authRespose.setRole(User_Role.valueOf(role));
		
		

		
		return new ResponseEntity<AuthRespose>(authRespose,HttpStatus.OK);
		
	}

	private Authentication Authenticate(String username, String password) throws Exception {
	
		UserDetails userDetails= customerUserDetailsService.loadUserByUsername(username);
		if(userDetails==null) {
			throw new Exception("invalid userName");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("invlid Password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
	}
}
