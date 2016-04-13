package com.sainsburys.technicalTest.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sainsburys.technicalTest.constants.TechnicalTestConstants;

public class ProductDetailFormatterTest {

	private ProductDetailFormatter productDetailFormatter;
	
	@Before
	public void init(){
		productDetailFormatter = new ProductDetailFormatter();
	}
	
	@Test
	public void testCalculateTotalProductPriceForTotalPrice() {
		
		productDetailFormatter.calculateTotalProductPrice("10");
		productDetailFormatter.calculateTotalProductPrice("20");
		
		Assert.assertNotNull(productDetailFormatter.getTotalProductPrice().toString());
		Assert.assertEquals("30.00", productDetailFormatter.getTotalProductPrice().toString());
	}
	
	@Test
	public void testGetFileSize(){
		
		Assert.assertEquals(TechnicalTestConstants.NOT_AVAILABLE, productDetailFormatter.getFileSize("http://wwww.google.com"));
		
		Assert.assertNotNull(productDetailFormatter.getFileSize(TechnicalTestConstants.PRODUCT_LIST_URL));
	}
}
