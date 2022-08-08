package com.rishabh.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.event.TreeWillExpandListener;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ch.qos.logback.core.joran.conditional.ThenAction;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

//This is the first filter our request will be intercepted at for all requests

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//1. Get Token
		// From the JSON request
		String requestToken = request.getHeader("Authorization");
		
		String username = null;
		String token = null;
		
		if(requestToken != null && requestToken.startsWith("Bearer")) {
			
			//assign
			token = requestToken.substring(7);
			
			try {
				//assign
				username = jwtTokenHelper.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has Expired");
			} catch (MalformedJwtException e) {
				System.out.println("Mal formed JWT");
			}
			
		}
		else { 
			System.out.println("Jwt token does not begin with bearer or is Null");
		}
		
		//Now Token is received, Now --->>> ((2. Validate Token))
		 if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			 
			 UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			 
			 if(this.jwtTokenHelper.validateToken(token, userDetails)) {
				 //Sab Sahi hai
				 //authentication karna hai
				 
//				 (3. Authentication karo)
//				 What is SecurtityContextHolder
//				 https://www.javacodegeeks.com/2018/02/securitycontext-securitycontextholder-spring-security.html#:~:text=The%20SecurityContext%20is%20used%20to,access%20to%20the%20security%20context.
				 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
						 new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				 usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				 
				 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				 
			 }
			 else {
				 System.out.println("Invalid JWT Token");
			 }
		 }
		 else {
			 System.out.println("Username is Null or Context is Not Null");
		 }
		
//		 4. Final Step, if some exception has occured Then JWTAuthenticationEntryPoint will run
		 filterChain.doFilter(request, response);
	}
	
}