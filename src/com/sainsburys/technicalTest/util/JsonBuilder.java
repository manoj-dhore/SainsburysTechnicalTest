package com.sainsburys.technicalTest.util;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.sainsburys.technicalTest.constants.TechnicalTestConstants;
import com.sainsburys.technicalTest.data.ProductData;

/**
 * Constructs the JSON object parsing product data list.
 * 
 * @author Manoj Dhore
 *
 */
public class JsonBuilder {

	public JsonBuilder(){}

	public String constructJson(List<ProductData> products) throws JSONException, IOException{
		
		final JSONObject productJsonElement = new JSONObject();
		
		ProductDetailFormatter productDetailFormatter = new ProductDetailFormatter();
		
		for(ProductData product :products){
			
			final JSONObject productInfoJson = new JSONObject();
			 productInfoJson.put(TechnicalTestConstants.TITLE, product.getTitle());
			 productInfoJson.put(TechnicalTestConstants.SIZE, product.getPageSize());
			 productInfoJson.put(TechnicalTestConstants.DESCRIPTION,product.getDescription());
			 productInfoJson.put(TechnicalTestConstants.UNIT_PRICE,product.getUnitPrice());
			 
			 productJsonElement.append(TechnicalTestConstants.RESULT, productInfoJson);
		}
		
		final JSONObject result = new JSONObject(productJsonElement.toString());
		result.put(TechnicalTestConstants.TOTAL, productDetailFormatter.getTotalProductPrice());
		
        return result.toString();
	}
	
}
