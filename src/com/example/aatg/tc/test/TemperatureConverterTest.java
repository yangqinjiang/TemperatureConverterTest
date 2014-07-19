package com.example.aatg.tc.test;

import java.util.HashMap;

import com.example.aatg.tc.exception.InvalidTemperatureException;
import com.example.aatg.tc.utils.TemperatureConverter;

import junit.framework.TestCase;

public class TemperatureConverterTest extends TestCase {

	public TemperatureConverterTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testFahrenheitToCelsius() {
		for(double c:conversonTableDouble.keySet()){
			double f = conversonTableDouble.get(c);
			double ca = TemperatureConverter.fahrenheitToCelsius(f);
			double delta = Math.abs(ca-c);
			String msg = ""+f+"F -> "+c+"C but was "+ca+"C (delta "+delta+")";
			assertTrue(msg,delta<0.0001);
		}
	}
	public final void testCelsiusToFahrenheit() {
		for(double c:conversonTableDouble.keySet()){
			double f = conversonTableDouble.get(c);
			double fa = TemperatureConverter.celsiusToFahrenheit(c);
			double delta = Math.abs(fa-f);
			String msg = ""+c+"C -> "+f+"F but was "+fa+"F (delta "+delta+")";
			assertTrue(msg,delta<0.0001);
		}
	}
	public void testExceptionForLessThanAbsoluteZeroF(){
		try{
			TemperatureConverter.fahrenheitToCelsius(TemperatureConverter.ABSOLUTE_ZERO_F - 1);
			fail();
		}catch(InvalidTemperatureException ex){
			//do nothing
		}
	}
	public void testExceptionForLessThanAbsoluteZeroC(){
		try{
			TemperatureConverter.celsiusToFahrenheit(TemperatureConverter.ABSOLUTE_ZERO_C - 1);
			fail();
		}catch(InvalidTemperatureException ex){
			//do nothing
		}
	}
	private static final HashMap<Double,Double> 
		conversonTableDouble = new HashMap<Double,Double>();
	static{
		//init (c,f) pairs
		conversonTableDouble.put(0.0, 32.0);
		conversonTableDouble.put(100.0, 212.0);
		conversonTableDouble.put(-1.0, 30.20);
		conversonTableDouble.put(-100.0,-148.0);
		conversonTableDouble.put(32.0,89.60);
		conversonTableDouble.put(-40.0,-40.0);
		conversonTableDouble.put(-273.0,-459.40);
	}

}
