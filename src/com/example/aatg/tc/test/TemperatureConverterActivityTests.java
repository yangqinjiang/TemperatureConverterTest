package com.example.aatg.tc.test;

import static android.test.ViewAsserts.assertLeftAligned;
import static android.test.ViewAsserts.assertOnScreen;
import static android.test.ViewAsserts.assertRightAligned;

import com.example.aatg.tc.EditNumber;
import com.example.aatg.tc.TemperatureConverterActivity;
import com.example.aatg.tc.utils.TemperatureConverter;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TemperatureConverterActivityTests extends
		ActivityInstrumentationTestCase2<TemperatureConverterActivity> {

	private TemperatureConverterActivity mActivity;
	private EditNumber mCelsius;
	private EditNumber mFahrenheit;

	private TextView mCelsiusLabel;
	private TextView mFahrenheitLabel;

	public TemperatureConverterActivityTests() {
		this("TemperatureConverterActivityTests");
	}

	public TemperatureConverterActivityTests(String name) {
		super(TemperatureConverterActivity.class);
		setName(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		// creating the fixture
		mActivity = getActivity();
		// creating the user interface
		mCelsius = (EditNumber) mActivity
				.findViewById(com.example.aatg.tc.R.id.celsius);
		mFahrenheit = (EditNumber) mActivity
				.findViewById(com.example.aatg.tc.R.id.fahrenheit);
		mCelsiusLabel = (TextView) mActivity
				.findViewById(com.example.aatg.tc.R.id.celsius_label);
		mFahrenheitLabel = (TextView) mActivity
				.findViewById(com.example.aatg.tc.R.id.fahrenheit_label);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		mCelsius = null;
		mFahrenheit = null;
		mActivity = null;

	}

	public void testPreconditions() {
		assertNotNull(mActivity);

	}

	public void testHasInputFields() {// �Ƿ�������������

		assertNotNull(mCelsius);
		assertNotNull(mFahrenheit);
		assertNotNull(mCelsiusLabel);
		assertNotNull(mFahrenheitLabel);

	}

	public void testFieldsShouldStartEmpty() {// ����Ƿ�Ϊ��ʼ��ʱ,�����ֵΪ��
		assertEquals("", mCelsius.getText().toString());
		assertEquals("", mFahrenheit.getText().toString());
	}

	public void testFieldsOnScreen() {// ����Ƿ���Screen��
		Window window = mActivity.getWindow();
		View origin = window.getDecorView();// ��ȡ������
		assertOnScreen(origin, mCelsius);
		assertOnScreen(origin, mFahrenheit);
	}

	public void testAlignment() {// ������
		assertLeftAligned(mCelsiusLabel, mCelsius);
		assertLeftAligned(mFahrenheitLabel, mFahrenheit);
		assertLeftAligned(mFahrenheit, mCelsius);
		assertRightAligned(mFahrenheit, mCelsius);

	}

	// �������
	public void testCelsiusInputFieldCoverEntireScreen() {
		int expected = LayoutParams.MATCH_PARENT;
		LayoutParams lp = mCelsius.getLayoutParams();
		assertEquals("mCelsius layout width is not MATCH_PARENT", expected,
				lp.width);

		expected = LayoutParams.WRAP_CONTENT;
		assertEquals("mCelsius layout width is not WRAP_CONTENT", expected,
				lp.height);
	}

	// �������
	public void testFahrenheitInputFieldCoverEntireScreen() {
		int expected = LayoutParams.MATCH_PARENT;
		LayoutParams lp = mFahrenheit.getLayoutParams();
		assertEquals("mFahrenheit layout width is not MATCH_PARENT", expected,
				lp.width);

		expected = LayoutParams.WRAP_CONTENT;
		assertEquals("mFahrenheit layout width is not WRAP_CONTENT", expected,
				lp.height);
	}

	// �������,��res/values/dimens.xml�¼���<dimen name="label_text_size">24px</dimen>
	//��celsius_label��ǩ�¼���android:textSize="@dimen/label_text_size"
	public void testFontSizes() {
		float expected = 24.0f;
		assertEquals(expected, mCelsiusLabel.getTextSize());
		assertEquals(expected, mFahrenheitLabel.getTextSize());
	}
	//���߾�
	public void testMargins(){
		LinearLayout.LayoutParams lp;
		int expected = 6;
		lp = (LinearLayout.LayoutParams)mCelsius.getLayoutParams();
		assertEquals(expected, lp.leftMargin);
		assertEquals(expected, lp.rightMargin);

		lp = (LinearLayout.LayoutParams)mFahrenheit.getLayoutParams();
		assertEquals(expected, lp.leftMargin);
		assertEquals(expected, lp.rightMargin);
	}
	public void testJustification(){//�����������Ҷ���
		//��,��ֱ����
		int expected = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
		int actual = mCelsius.getGravity();
		assertEquals(String.format("Expected 0x%02x but was 0x%02x",expected,actual),expected, actual);
		actual = mFahrenheit.getGravity();
		assertEquals(String.format("Expected 0x%02x but was 0x%02x",expected,actual),expected, actual);
	}
	//�����������ʱ,���ᵲס������������
	public void testVirtualKeyboardSpaceReserved(){
		int expected = 380;
		int actual = mFahrenheit.getBottom();
		assertTrue(actual <=expected);
	}
	//�������������
	@UiThreadTest
	public void testFahrenheitToCelsiusConversion(){
		mCelsius.clear();
		mFahrenheit.clear();
		double f = 32.5;
		mFahrenheit.requestFocus();
		mFahrenheit.setNumber(f);
		assertEquals(f, mFahrenheit.getNumber());
		
		mCelsius.requestFocus();
		double expectedC = TemperatureConverter.fahrenheitToCelsius(f);
		double actualC = mCelsius.getNumber();
		double delta = Math.abs(expectedC - actualC);
		String msg = ""+f+"F -> "+expectedC+"C but was "+actualC+"C (delta "+delta+")";
		assertTrue(msg,delta<0.005);
	}
	public void testInputFilter() throws Throwable{
		runTestOnUiThread(new Runnable(){
			@Override
			public void run() {
				mCelsius.requestFocus();
			}
		});
		Double n = -1.234d;
		sendKeys("MINUS 1 PERIOD 2 PERIOD 3 PERIOD 4");
		Object nr = null;
		try{
			nr = mCelsius.getNumber();
		}catch(NumberFormatException e){
			nr = mCelsius.getText();
		}
		String msg = "-1.2.3.4 should be filtered to "+n+"but is "+nr;
		assertEquals(msg,n, nr);
	}

}
