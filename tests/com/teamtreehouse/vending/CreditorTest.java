package com.teamtreehouse.vending;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditorTest {

    private Creditor creditor;

    @Before
    public void setUp() throws Exception {
        creditor = new Creditor();

    }

    @Test
    public void addFunds() {

        creditor.addFunds(25);
        creditor.addFunds(25);

        assertEquals(50, creditor.getAvailableFunds());
    }

    @Test(expected = NotEnoughFundsException.class)
    public void deduct() throws NotEnoughFundsException {
        creditor.deduct(10);
    }

    @Test
    public void refund() {

        creditor.addFunds(10);
        int refund = creditor.refund();

        assertEquals(10, refund);

        checkZero();
    }

    @Test
    public void getAvailableFunds() {
        creditor.addFunds(10);

        assertEquals(10, creditor.getAvailableFunds());

    }

    @Test
    public void checkZero() {
        assertEquals(0, creditor.getAvailableFunds());
    }

}