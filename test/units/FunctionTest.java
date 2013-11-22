package units;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Test;

public class FunctionTest {

	ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
	
	@Test
	public void pH_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"pH(6)","micromol/gallon"};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("\t* 3.7854118\n\t/ 0.26417205\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	
	@Test
	public void function_with_no_arguement_test1() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"30 cm^2","circlearea"};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("\t0.030901936 m\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	
	@Test
	public void function_with_no_arguement_test2() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"300 K","tempF"};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("\t80.33\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	
	@Test
	public void exponent_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","cm^3","gallon"};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("0.00026417205\n3785.4118\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	
	@Test
	public void wiregauge_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","wiregauge(11)","mm"};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("2.3048468\n0.43386831\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	
	@Test
	public void tempF_inverse_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","-1","~tempF(309.26111 K)",""};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("96.999998\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	
	@Test
	public void conversion_celsius_to_Farenheit_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","-1","tempC(36)","tempF"};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("96.8\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	
	@Test
	public void degC_to_degF_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","-1","36 degC","degF"};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("64.8\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	
	@Test
	public void wiregauge_inverse_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","-1","~wiregauge(2.3048468 mm)",""};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("11\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	
	@Test
	public void pH_inverse_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","-1","~pH(1.0E-8 mol/liter)",""};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("8\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	
	@Test
	public void constant_pi_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","-1","3pi",""};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("9.424778\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}


@Test
	public void sine_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","-1","sin(30)",""};
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("-0.98803162\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	

	@Test
	public void cosine_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","-1","cos(30)",""};	//radians
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("0.15425145\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	
	@Test
	public void tangent_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","-1","tan(30)",""};	//radians
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("-6.4053312\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
			}
	
	@Test
	public void square_root_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","-1","sqrt(9)",""};	//radians
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("3\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
		}
	
	@Test
	public void rounding_off_test() {
		try {
			
			System.setOut(new PrintStream(systemOut));
			String[] argv={"-c","-v","-1","2 m","ft;in;1|8 in;;"};	//radians
			convert.main(argv);
			String result=systemOut.toString();
			Assert.assertEquals("6;6;6;-\n",result);
			systemOut.close();
		}
		catch (Exception ex) {
			
			fail("Unexpected"+ex.getStackTrace());
				}
		}
	}


