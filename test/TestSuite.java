

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import units.FunctionTest;
import units.convertTest;
@RunWith(Suite.class)
@Suite.SuiteClasses({
	convertTest.class,
	FunctionTest.class
})
public class TestSuite {

}