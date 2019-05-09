package com.teamtreehouse.vending;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class AlphaNumericChooserTest {

    private AlphaNumericChooser chooser;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        chooser = new AlphaNumericChooser(26, 10);
    }

    @Test
    public void locationFromInput() throws Exception  {
        AlphaNumericChooser.Location loc = chooser.locationFromInput("B4");

        assertEquals("proper row",1, loc.getRow());
        assertEquals("proper column", 3, loc.getColumn());
    }

    @Test
    public void locationFromInputFail() throws Exception  {
        thrown.expect(InvalidLocationException.class);
        AlphaNumericChooser.Location loc = chooser.locationFromInput(" 4");

    }
}