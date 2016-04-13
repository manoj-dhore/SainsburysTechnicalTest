package com.sainsburys.technicalTest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sainsburys.technicalTest.data.ProductData;
import com.sainsburys.technicalTest.util.JsonBuilder;
import com.sainsburys.technicalTest.util.ProductDetailFormatter;

public class Launcher {

	private final static Logger log = Logger.getLogger(ScrapeController.class);
	
	public void run(String[] args){
				
		String urlString = "";
				
		if(args != null &&  args.length >0){
			urlString = args[0];
			log.info("urlString : " + urlString);
		}
		
		if(urlString.isEmpty()){
			log.error("No URL to scrap");
		} 
		
		ProductDetailFormatter productDetailFormatter = new ProductDetailFormatter();
		
		try {
			//Document for product list page.
			Document doc = Jsoup.connect(urlString).get();
		
			//Retrieve the product elements.
			Elements elements = doc.select("div.product");
			
			List<ProductData> products = new ArrayList<ProductData>();

			//Construct product data object here.
			if(elements != null && elements.size() >0){
				for (Iterator<Element> iterator = elements.iterator(); iterator.hasNext();) {
					products.add(productDetailFormatter.getProductDetails(iterator.next()));
				}
			}
			
			JsonBuilder jsonBuilder = new JsonBuilder();				
			System.out.println(jsonBuilder.constructJson(products));
			log.info(jsonBuilder.constructJson(products));
			
		} catch (IOException e) {
			log.error(e.getMessage());
			
		} catch (JSONException e) {
			log.error(e.getMessage());
			
		}
	}
}
