package com.teamtreehouse.vending;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class BinTest {

    private Bin bin;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        bin =new Bin(10);
    }


    @Test
    public void restockFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot restock Apple with Banana");

        bin.restock("Apple", 1, 100,50);
        bin.restock("Banana",2,200,100);
    }

    @Test(expected = NullPointerException.class)
    public void emptyBinChargeZero() throws Exception{
        assertEquals(0, bin.getItemPrice());
    }

    @Test
    public void emptyBinNullName() throws Exception{
        assertNull(bin.getItemName());
    }

    @Test
    public void overStock() throws Exception{
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("There are only 10 spots left");

        bin.restock("7up", 100, 100, 50);
    }

    @Test
    public void largerThanAlphabet() throws Exception{
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Maximum rows supported is 26");

        new AlphaNumericChooser(27,10);
    }
}