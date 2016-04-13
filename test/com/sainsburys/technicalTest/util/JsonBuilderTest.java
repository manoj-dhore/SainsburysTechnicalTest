package com.sainsburys.technicalTest.util;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import com.sainsburys.technicalTest.constants.TechnicalTestConstants;
import com.sainsburys.technicalTest.data.ProductData;

import junit.framework.Assert;

public class JsonBuilderTest {

	private JsonBuilder jsonBuilder;
	private ProductData productData;
	
	private List<ProductData> productDataList = new ArrayList<ProductData>();
	
	@Before
	public void init(){
		jsonBuilder = new JsonBuilder();
		
		productData = new ProductData();
		productData.setDescription("Description");
		productData.setPageSize("10MB");
		productData.setTitle("title");
		productData.setUnitPrice(10);
		productData.setUrl("www.test.com");
		
		productDataList.add(productData);
	}

	@Test
	public void testConstructJsonForNotNull() throws Exception{
		
		String response = jsonBuilder.constructJson(new ArrayList<ProductData>());
		
		Assert.assertNotNull(response);
		
		//Should return atleast the total if no products are available
		Assert.assertEquals("{\"total\":0}", jsonBuilder.constructJson(new ArrayList<ProductData>()));
		
	}
	
	@Test
	public void testConstructJsonWithProduct() throws Exception{
		String response = jsonBuilder.constructJson(productDataList); 
		
		org.junit.Assert.assertThat(response, CoreMatchers.anything(TechnicalTestConstants.DESCRIPTION));
		org.junit.Assert.assertThat(response, CoreMatchers.anything(TechnicalTestConstants.SIZE));
		org.junit.Assert.assertThat(response, CoreMatchers.anything(TechnicalTestConstants.TITLE));
		org.junit.Assert.assertThat(response, CoreMatchers.anything(TechnicalTestConstants.UNIT_PRICE));
		org.junit.Assert.assertThat(response, CoreMatchers.anything(TechnicalTestConstants.TOTAL));
		
	}
}
