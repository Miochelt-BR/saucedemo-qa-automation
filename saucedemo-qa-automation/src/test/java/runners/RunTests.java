package runners;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import tests.CartTest;
import tests.CheckoutTest;
import tests.LoginTest;
import tests.ProductTest;

@Suite
@SelectClasses({
        LoginTest.class,
        ProductTest.class,
        CartTest.class,
        CheckoutTest.class
})
public class RunTests {

}