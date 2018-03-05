package pl.lebiedzinski;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RastamenTest {
    @Test
    public void testFindObject() {
        Rastaman high = new Rastaman();
        assertNotNull(high);
    }

}