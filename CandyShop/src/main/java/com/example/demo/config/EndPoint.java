package com.example.demo.config;

public class EndPoint {

	public static String[] ALLOWED_ORIGINS = { "*" };
	public static String[] ALLOWED_METHODS = { "GET", "POST", "PUT", "DELETE", "PATCH" };
	public static String[] PUBLIC_ENDPOINT_GET = {"/swagger-ui/**", "/v3/api-docs/**"};
	public static String[] PUBLIC_ENDPOINT_POST = { "/api/auth/login", "/api/auth/register" };

}
