package com.sainsburys.technicalTest.controller;

import org.apache.log4j.Logger;
import org.json.JSONException;

/**
 * Entry point for scraping the page
 * 
 * @author Manoj Dhore
 *
 */
public class ScrapeController {

	private final static Logger log = Logger.getLogger(ScrapeController.class);
	
	/**
	 * @param args
	 * @throws JSONException 
	 */
	public static void main(String[] args) {
		log.info("Start scraping of page");
		new Launcher().run(args);
	}	

}
