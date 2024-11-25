package com.example.demo.config;

public class EndPoint {

	public static String[] ALLOWED_ORIGINS = { "*" };
	public static String[] ALLOWED_METHODS = { "GET", "POST", "PUT", "DELETE", "PATCH" };
	public static String[] PUBLIC_METHODS_POST = { "/api/auth/login", "/api/auth/register" };
	public static String[] PUBLIC_METHODS_GET = 
		{ 
				"/swagger-ui/**",
				"/v3/api-docs/**",
				
				"/api/categories", 
				"/api/categories/*", 
				"/api/categories/*/subcategories",
				
				"/api/districts/*",
				"/api/districts/*/wards",
				
				"/api/products",
				"/api/products/*",
				"/api/products/*/price-histories",
				
				"/api/provinces",
				"/api/provinces/*",
				"/api/provinces/*/districts",
				
				"/api/subcategories/*",
				
				"/api/wards/*"
		};

}