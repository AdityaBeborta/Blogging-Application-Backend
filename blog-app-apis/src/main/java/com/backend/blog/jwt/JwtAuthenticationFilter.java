package com.backend.blog.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// trying to fetch token from header
		// 1.get token
		String requestToken = request.getHeader("Authorization");
		// it will be having bearer dbjbsdj
//		System.out.println(requestToken);
		// from this token we need to fetch username and actual JWTToken
		String username = null;
		String token = null;
		// before that we need to check wheather the request token is null and it starts
		// with bearer
		if (requestToken != null && requestToken.startsWith("Bearer")) {

			// we need to get the actual token from the request token
			// as the token is present in the request token after the bearer
			token = requestToken.substring(7);
			try {
				username = this.jwtTokenHelper.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				System.out.println("unbale to get Jwt token");
			} catch (ExpiredJwtException e) {
				System.out.println("jwt token has expired");
			} catch (MalformedJwtException e) {
				System.out.println("invalid JWT Token");
			}
		} else {
			System.out.println("JWT token doesnt begin with bearer");
		}
		// now we are having username and token from the header
		// once we get the token we can validate the token
		// we need to check like do we have any user in the authentication context
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// we need to use the validate method of jwt helper class
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			Boolean validateToken = this.jwtTokenHelper.validateToken(token, userDetails);
			if (validateToken) {
				// shi chal raha he
				// authentication set karna he
				// now we need to set authentication in SecurityContextHolder
				// but we need the object of authentication
				// in order to create Authentication object we need to use
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				// set user details
				// now in order to set user we need to build the user details
				WebAuthenticationDetails buildDetails = new WebAuthenticationDetailsSource().buildDetails(request);
				authenticationToken.setDetails(buildDetails);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			} else {
				System.out.println("invalid");
			}
		}

		else {
			System.out.println("Invalid user name or SecurityContext");
		}

		// inorder to filter the request further
		// if the user is not authorized then in SecurityContext there will be no user
		// then it will call the commence method which is present in
		// JwtAuthenticationEntryPoint

		filterChain.doFilter(request, response);

	}
}
