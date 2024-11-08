package com.example.demo.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.service.MyUserDetailsService;
import com.example.demo.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private MyUserDetailsService myUserDetailsService;
	private JwtUtil jwtUtil;

	public JwtRequestFilter(MyUserDetailsService userDetailsService, JwtUtil jwtUtil) {
		this.myUserDetailsService = userDetailsService;
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String requestURI = request.getRequestURI();
			
			if (Arrays.asList(EndPoint.PUBLIC_ENDPOINT_POST).contains(requestURI)) {
				filterChain.doFilter(request, response);
				return;
			}
			
			final String authorizationHeader = request.getHeader("Authorization");

			String username = null;
			String jwt = null;

			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				jwt = authorizationHeader.substring(7);
				username = jwtUtil.extractUsername(jwt);
			} else {
				throw new UnauthorizedException("Invalid Token or Token expired");
			}

			if (username != null) {
				UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
				if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				} else {
					throw new UnauthorizedException("Invalid Token or Token expired");
				}
			} else {
				throw new UnauthorizedException("Invalid Token or Token expired");
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			SecurityContextHolder.clearContext();
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			
			ApiResponseDTO<Void> apiResponse = new ApiResponseDTO<>();
			apiResponse.setStatus(401);
			apiResponse.setMessage("Unauthorized access");
			apiResponse.setData(null);
			
			response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
		}
	}

}
