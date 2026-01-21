package runners;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import tests.LoginTest;
import tests.ProductTest;

@Suite
@SelectClasses({
        LoginTest.class,
        ProductTest.class
})
public class RunTests {

}