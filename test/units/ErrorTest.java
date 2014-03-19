package units;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Test;

public class ErrorTest {
	ByteArrayOutputStream systemOut = new ByteArrayOutputStream();

	@Test
	public void error_invalid_pipe_input_character_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"meter","yard;ft|in"};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("Invalid unit list. Element 2 is not valid. After 'ft': expected more name or ^ or ** or factor or * or / or 'per' or + or - or end of input\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	@Test
	public void error_invalid_unary_input_character_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"3e+2C","4"};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("Conformability error\n\t300 A s\n\t4\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	
	@Test
	public void dimensionless_error_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","-1","10 gallons","circlearea(5cm)"};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("Conformability error\n0.037854118 m^3\n0.0078539816 m^2\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	
	@Test
	public void invalid_unitlist_conformability_error_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","-1","meter","ft;kg"};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("Invalid unit list. Conformability error:\n\tft = 0.3048 m\n\tkg = kg\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	
	@Test
	public void invalid_unit_conformability_error_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","-1","meter","lb;oz"};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("Conformability error\n\tmeter = m\n\tlb = 0.45359237 kg\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}

	@Test
	public void semicolon_error_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","-1","meter","lb;;;;;oz"};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("Invalid unit list. Element 2 is empty.\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	
	@Test
	public void invalid_delimiter_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","-1","meter","lb/oz"};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("Conformability error\nm\n16\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	

@Test
public void divide_by_zero_error_test() {
	try {
		
		System.setOut(new PrintStream(systemOut));
		String[] argv={"4/0","1"};
		convert.main(argv);
		String result=systemOut.toString();
		Assert.assertEquals("3.1207548e16\n",result);
		systemOut.close();
	}
	catch (Exception ex) {
		
		fail("Unexpected"+ex.getStackTrace());
			}
		}



}
