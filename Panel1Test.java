

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class Panel1Test.
 *
 * @author  (AirbnbYeet)
 * @version (a version number or a date)
 */
public class Panel1Test
{
    private Panel1 panel11;

    /**
     * Default constructor for test class Panel1Test
     */
    public Panel1Test()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        panel11 = new Panel1();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testGetPriceTo()
    {
        assertEquals(0, panel11.getToPrice());
    }

    @Test
    public void testGetPriceFrom()
    {
        assertEquals(0, panel11.getFromPrice());
    }

    @Test
    public void testSetPriceTo()
    {
        panel11.setPriceTo("25");
        assertEquals(25, panel11.getToPrice());
    }

    @Test
    public void testSetPriceFrom()
    {
        panel11.setPriceFrom("0");
        assertEquals(0, panel11.getFromPrice());
    }

    @Test
    public void testErrorMsg()
    {
        assertNotNull(panel11.getErrorMessage());
    }

    @Test
    public void testDateIn()
    {
        assertNotNull(panel11.getPickerIn());
    }

    @Test
    public void testDateOut()
    {
        assertNotNull(panel11.getPickerOut());
    }
}










