package com.revature.ers.web;

import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;

@Retention(CLASS)
public @interface WebServlet {

	String urlPatterns();

}
