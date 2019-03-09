package edu.npu.orderApp.tests.unitTests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import edu.npu.orderApp.dao.ProductDAO;
import edu.npu.orderApp.domain.Product;
import edu.npu.orderApp.tests.config.OrderAppTestConfig;

@RunWith(SpringRunner.class)
@Import(OrderAppTestConfig.class)  /* So we use a different config than the production code */
public class JdbcProductDAOTests {
	@Autowired
	@Qualifier("productDaoJdbc")
	private ProductDAO productDAO;
	private static String prodId2_name = "X2200";  /* see create_cusorders.sql where this product should have been inserted */
	private static int prodX2200_expectedOrderTotal = 10;  /* see create_cusorders.sql where this product should have been inserted */

	@Test
	public void testProductCount() {
		int cnt = productDAO.getProductCount();
		System.out.println(cnt);
	}
	
	@Test
	public void testTotalOrdersByProdName() {
		int totalOrders = productDAO.findTotalOrdersByName(prodId2_name);
		assertEquals(totalOrders, prodX2200_expectedOrderTotal);
	}
	
	@Test
	public void testLessThanOrderCount() {
		List<String> prods = productDAO.findProdsWithLessThanTotalOrder(1);
		System.out.println(prods.size());
		System.out.println(prods);
	}
	
	@Test
	public void testFindProdNameById() {
		String expectedProdName = "X2200";
		String name = productDAO.findProdNameById(3);
		assertEquals(name, expectedProdName);
	}
	
	@Test
	public void testInsertProd() {
		Product newProd, prodFromDb; 
		String newProdName = "GH5678";
		double newProdPrice = 123.52;
		final double DELTA = 1e-10; // 
		int cnt;
		
		newProd = new Product();
		newProd.setName(newProdName);
		newProd.setPrice(newProdPrice);
		cnt = productDAO.insertNewProduct(newProd);
		assertEquals(1,cnt);
		
//		prodFromDb = productDAO.findProductByName(newProdName); //professional way
//		assertEquals(newProd.getName(), prodFromDb.getName());
//		assertEquals(newProd.getPrice(), prodFromDb.getPrice());//price floating never use equal on floating
//		assertEquals(0,prodFromDb.getTotalOrders()); 
//		
//		String name = productDAO.findProdNameById(3);
//		assertEquals(name, expectedProdName);
	}
}