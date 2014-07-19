package com.example.aatg.tc.test;

import android.content.Context;
import android.test.AndroidTestCase;

import com.example.aatg.tc.EditNumber;


public class EditNumberTest extends AndroidTestCase {

	private EditNumber mEditNumber;
	//private Context mContext;

	public EditNumberTest() {
		this("EditNumberTests");
	}
	public EditNumberTest(String name) {
		setName(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		// mContext mock context
		mEditNumber =  new EditNumber(mContext);
		mEditNumber.setFocusable(true);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}



	public final void testClear() {
		//fail("Not yet implemented");
		String value = "123.45";
		mEditNumber.setText(value);
		mEditNumber.clear();
		String expectedString = "";
		String actualString = mEditNumber.getText().toString();
		assertEquals(expectedString, actualString);;
	}

	public final void testSetNumber() {
		mEditNumber.setNumber(123.45);
		String expected = "123.45";
		String actual = mEditNumber.getText().toString();
		assertEquals(expected, actual);
	}

	public final void testGetNumber() {
		mEditNumber.setNumber(123.45);
		double expected = 123.45;
		double actual = mEditNumber.getNumber();
		assertEquals(expected, actual);
	}

}
