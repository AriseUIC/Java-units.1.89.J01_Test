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
}
