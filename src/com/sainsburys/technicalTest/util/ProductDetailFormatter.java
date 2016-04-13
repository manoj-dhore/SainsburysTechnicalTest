package com.sainsburys.technicalTest.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sainsburys.technicalTest.constants.TechnicalTestConstants;
import com.sainsburys.technicalTest.data.ProductData;

/**
 * Parses the xml document and retrieves product related info.
 * 
 * @author Manoj Dhore
 *
 */
public class ProductDetailFormatter {

	private final static Logger log = Logger.getLogger(ProductDetailFormatter.class);

	private static BigDecimal totalProductPrice = new BigDecimal("0.00");
	
	public ProductDetailFormatter(){}
	
	public ProductData getProductDetails(Element element) {

		Element firstLink = element.select("a").first();

		ProductData productData = new ProductData();
		productData.setUrl(firstLink.attr("href"));
		productData.setTitle(firstLink.text());

		Element priceElement = element.select("p.pricePerUnit").first();
		String price = priceElement.text();
		price = price.replaceAll("[^\\d.]", "");
		productData.setUnitPrice(Double.parseDouble(price));

		calculateTotalProductPrice(price);
		
		try {
			Document productDoc = Jsoup.connect(productData.getUrl()).get();
			
			Elements productElements = productDoc.getElementsByAttributeValue(TechnicalTestConstants.ITEM_PROP, TechnicalTestConstants.DESCRIPTION);
			productData.setDescription(productElements.first().text());
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		productData.setPageSize(getFileSize(productData.getUrl()));
		return productData;
	}
	
	
	/**
	 * Retrieves the file size for product details page.
	 * 
	 * @param url
	 * @return content length if available other wise 'Not available' string
	 */
	public String getFileSize(String url) {
		
		String fileSize = null;
   	 	HttpURLConnection conn = null;
	   	try {
	   		   URL obj = new URL(url);
	           conn = (HttpURLConnection)obj.openConnection();
	           conn.setRequestMethod(TechnicalTestConstants.HEAD);
	           Map<String, List<String>> map = conn.getHeaderFields();
	           
	           
	           List<String> contentLength = map.get(TechnicalTestConstants.CONTENT_LENGTH);
	
	           if (contentLength == null) {
	               fileSize =  TechnicalTestConstants.NOT_AVAILABLE;
	           } else {
	               fileSize = String.valueOf(Double.parseDouble(contentLength.get(0))/1000) + TechnicalTestConstants.KB;
	           }
	       } catch (Exception e) {
	    	   log.error("Error retriving size : " + e.getMessage());
	    	   e.printStackTrace();
	       }
	       return fileSize;
   }
	
	/**
	 * Add the individual product price.
	 * 
	 * @param productPrice
	 */
	public void calculateTotalProductPrice(String productPrice){
		 if(productPrice != null){
			 totalProductPrice = totalProductPrice.add(BigDecimal.valueOf(Double.parseDouble(productPrice)));
		 }
	}

	public BigDecimal getTotalProductPrice() {
		return totalProductPrice;
	}
}
