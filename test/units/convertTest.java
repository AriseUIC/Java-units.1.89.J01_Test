package units;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JOptionPane;

import static org.junit.Assert.*;
import units.*;
import units.Env.Writer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class convertTest {
	
	ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
	
	@Test
	public void test() {
	try {
		
		System.setOut(new PrintStream(systemOut));
		
		String[] argv={"5 yards * (4 feet + 3 in) * 7 in * 1 kg/liter","pounds"};
		convert.main(argv);
		String result=systemOut.toString();
		Assert.assertEquals("\t* 2321.5398\n\t/ 0.00043074859\n",result);
		systemOut.close();
	}
	catch (Exception ex) {
		
		fail("Unexpected"+ex.getStackTrace());
	}
}
	

@Test
public void test2() {
try {
	
	System.setOut(new PrintStream(systemOut));
	String[] argv={"c/144MHz", "m"};
	convert.main(argv);
	String result=systemOut.toString();
	Assert.assertEquals("\t* 2.0818921\n\t/ 0.4803323\n",result);
	systemOut.close();
}
catch (Exception ex) {
	
	fail("Unexpected"+ex.getStackTrace());
		}
	}

@Test
public void test3() {
try {
	
	System.setOut(new PrintStream(systemOut));
	String[] argv={"8 liters per 100 km","miles per gallon"};
	convert.main(argv);
	String result=systemOut.toString();
	Assert.assertEquals("\treciprocal conversion\n\t* 29.401823\n\t/ 0.034011497\n",result);
	systemOut.close();
}
catch (Exception ex) {
	
	fail("Unexpected"+ex.getStackTrace());
		}
	}

@Test
public void test4() {
try {
	
	System.setOut(new PrintStream(systemOut));
	String[] argv={"2 liters","quarts"};
	convert.main(argv);
	String result=systemOut.toString();
	Assert.assertEquals("\t* 2.1133764\n\t/ 0.47317647\n",result);
	systemOut.close();
}
catch (Exception ex) {
	
	fail("Unexpected"+ex.getStackTrace());
		}
	}

@Test
public void test5() {
try {
	
	System.setOut(new PrintStream(systemOut));
	String[] argv={"2 hours + 23 minutes - 32 seconds","seconds"};
	convert.main(argv);
	String result=systemOut.toString();
	Assert.assertEquals("\t* 8548\n\t/ 0.00011698643\n",result);
	systemOut.close();
}
catch (Exception ex) {
	
	fail("Unexpected"+ex.getStackTrace());
		}
	}

@Test
public void test_optionVerbose() {
try {
	
	System.setOut(new PrintStream(systemOut));
	String[] argv={"-v","kmph","mph"};
	convert.main(argv);
	String result=systemOut.toString();
	Assert.assertEquals("\tkmph = 1000 mph\n\tkmph = (1 / 0.001) mph\n",result);
	systemOut.close();
}
catch (Exception ex) {
	
	fail("Unexpected"+ex.getStackTrace());
		}
	}

@Test
public void test_printReciprocal() {
try {
	
	System.setOut(new PrintStream(systemOut));
	String[] argv={"grains","pounds"};
	convert.main(argv);
	String result=systemOut.toString();
	Assert.assertEquals("\t* 0.00014285714\n\t/ 7000\n",result);
	systemOut.close();
}
catch (Exception ex) {
	
	fail("Unexpected"+ex.getStackTrace());
		}
	}

@Test
public void test_printReciprocalConversion() {
try {
	
	System.setOut(new PrintStream(systemOut));
	String[] argv={"6 ohms","siemens"};
	convert.main(argv);
	String result=systemOut.toString();
	Assert.assertEquals("\treciprocal conversion\n\t* 0.16666667\n\t/ 6\n",result);
	systemOut.close();
}
catch (Exception ex) {
	
	fail("Unexpected"+ex.getStackTrace());
		}
	}

@Test
public void test_optionCompact() {
try {
	
	System.setOut(new PrintStream(systemOut));
	String[] argv={"-c","meter","yard"};
	convert.main(argv);
	String result=systemOut.toString();
	Assert.assertEquals("1.0936133\n0.9144\n",result);
	systemOut.close();
}
catch (Exception ex) {
	
	fail("Unexpected"+ex.getStackTrace());
		}
	}

@Test
public void test_mixedUnits() {
try {
	
	System.setOut(new PrintStream(systemOut));
	String[] argv={"-c","meter","yard;ft;in"};
	convert.main(argv);
	String result=systemOut.toString();
	Assert.assertEquals("1;0;3.3700787\n",result);
	systemOut.close();
}
catch (Exception ex) {
	
	fail("Unexpected"+ex.getStackTrace());
		}
	}

@Test
public void test_optionVerbose_and_oneline() {
try {
	
	System.setOut(new PrintStream(systemOut));
	String[] argv={"-v","-1", "5 yards * (4 feet + 3 in) * 7 in * 1 kg/liter","pounds"};
	convert.main(argv);
	String result=systemOut.toString();
	Assert.assertEquals("\t5 yards * (4 feet + 3 in) * 7 in * 1 kg/liter = 2321.5398 pounds\n",result);
	systemOut.close();
}
catch (Exception ex) {
	
	fail("Unexpected"+ex.getStackTrace());
		}
	}


@Test
public void division_test() {
	try {
		
		System.setOut(new PrintStream(systemOut));
		String[] argv={"-c","-v","1|2 meters","meters"};
		convert.main(argv);
		String result=systemOut.toString();
		Assert.assertEquals("0.5\n2\n",result);
		systemOut.close();
	}
	catch (Exception ex) {
		
		fail("Unexpected"+ex.getStackTrace());
			}
		}

@Test
public void multiplication_test() {
	try {
		
		System.setOut(new PrintStream(systemOut));
		String[] argv={"-c","-v","10cm 15cm 1m","liters"};
		convert.main(argv);
		String result=systemOut.toString();
		Assert.assertEquals("15\n0.066666667\n",result);
		systemOut.close();
	}
	catch (Exception ex) {
		
		fail("Unexpected"+ex.getStackTrace());
			}
		}

@Test
public void add_sub_test() {
	try {
		
		System.setOut(new PrintStream(systemOut));
		String[] argv={"-c","-v","2 hours + 23 minutes - 32 seconds","seconds"};
		convert.main(argv);
		String result=systemOut.toString();
		Assert.assertEquals("8548\n0.00011698643\n",result);
		systemOut.close();
	}
	catch (Exception ex) {
		
		fail("Unexpected"+ex.getStackTrace());
			}
		}

@Test
public void shorthand_test_1() {
	try {
		
		System.setOut(new PrintStream(systemOut));
		String[] argv={"-c","-v","anomalisticyear","time"};
		convert.main(argv);
		String result=systemOut.toString();
		Assert.assertEquals("1;0;0;25;3.4653216\n",result);
		systemOut.close();
	}
	catch (Exception ex) {
		
		fail("Unexpected"+ex.getStackTrace());
			}
		}

@Test
public void shorthand_test_2() {
	try {
		
		System.setOut(new PrintStream(systemOut));
		String[] argv={"-c","-v","1|6 cup","usvol"};
		convert.main(argv);
		String result=systemOut.toString();
		Assert.assertEquals("0;0;0;0;0;0;2;2;0;0;0\n",result);
		systemOut.close();
	}
	catch (Exception ex) {
		
		fail("Unexpected"+ex.getStackTrace());
			}
		}

@Test
public void dimensionless_units_test() {
	try {
		
		System.setOut(new PrintStream(systemOut));
		String[] argv={"-c","-v","-1","(14 ft lbf) (12 radians/sec)","watts"};
		convert.main(argv);
		String result=systemOut.toString();
		Assert.assertEquals("227.77742\n",result);
		systemOut.close();
	}
	catch (Exception ex) {
		
		fail("Unexpected"+ex.getStackTrace());
			}
		}

@Test
public void electron_flow_test() {
	try {
		
		System.setOut(new PrintStream(systemOut));
		String[] argv={"-c","-v","-1","5 mA","e/sec"};
		convert.main(argv);
		String result=systemOut.toString();
		Assert.assertEquals("3.1207548e16\n",result);
		systemOut.close();
	}
	catch (Exception ex) {
		
		fail("Unexpected"+ex.getStackTrace());
			}
		}

@Test
public void baking_test() {
	try {
		
		System.setOut(new PrintStream(systemOut));
		String[] argv={"-c","-v","-1","2 cups flour_sifted","g"};
		convert.main(argv);
		String result=systemOut.toString();
		Assert.assertEquals("226.79619\n",result);
		systemOut.close();
	}
	catch (Exception ex) {
		
		fail("Unexpected"+ex.getStackTrace());
			}
		}

@Test
public void comformability_error_test() {
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
}
