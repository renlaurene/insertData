package edu.npu.orderApp.tests.unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import edu.npu.orderApp.dao.jdbc.OrderDaoJdbcImpl;
import edu.npu.orderApp.domain.Order;
import edu.npu.orderApp.tests.config.OrderAppTestConfig;

// Note: These tests are configured with OrderAppTestConfig.java
// As an exercise, add tests for findOrdersByCustAndAmtGtrThan()  
@RunWith(SpringRunner.class)
@Import(OrderAppTestConfig.class)
public class JdbcOrderDaoTests {
	@Autowired
	JdbcTemplate dbtemplate;
	@Autowired
	OrderDaoJdbcImpl orderdao;

	@Test
	public void testFindAllOrders() {
		List<Order> orders = orderdao.findAllOrders();
		int orderCnt = orderdao.getOrderCount();
		assertTrue(orders.size() == orderCnt);
	}
	
	@Test
	public void testFindOrderById() {
		String expectedCusNum = "5B999";
		Order order = orderdao.findOrderById(1);
		String cusNum = order.getCusnum();
		assertEquals(cusNum, expectedCusNum);
	}
	
	@Test
	public void testInsertOrder() {
		Order orderFromDb;
		GregorianCalendar today = new GregorianCalendar();
		Date todayDate = today.getTime();
		
		int newOrderId;
		Order order = new Order(null, "6C779");
		order.setAmount(75.0F);
		order.setDate(todayDate);
		
		/* The id is set by the insert function */
		orderdao.insertOrder(order);
		newOrderId = order.getId();
		
		/* Verify that an order id was assigned */
		assertTrue(newOrderId > 0);
		orderFromDb = orderdao.findOrderById(newOrderId);
		assertEquals(order, orderFromDb);
	}

}
