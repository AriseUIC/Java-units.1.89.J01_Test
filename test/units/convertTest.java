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
		//JOptionPane.showMessageDialog(null, "Done.");
		//String[] argv = {"1", "+", "1"};
		String[] argv={"5 yards * (4 feet + 3 in) * 7 in * 1 kg/liter","pounds"};
		//String[] argv={"1mile", "km"};//<-- this works
		convert.main(argv);
		//JOptionPane.showMessageDialog(null, "Done.");
		String result=systemOut.toString();
		//System.out.println(result);
		
		//Env.out = new myOut();
		//Env.out.redirectSysout();
		Assert.assertEquals("\t* 2321.5398\n\t/ 0.00043074859\n",result);
		systemOut.close();
	}
	catch (Exception ex) {
		
		fail("Unexpected"+ex.getStackTrace());
	}
}
}
