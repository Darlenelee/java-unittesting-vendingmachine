package com.teamtreehouse.vending;

import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class VendingMachineTest {
    public VendingMachine machine;

    public class NotifierSub implements Notifier{
        @Override
        public void onSale(Item item) {
            return;
        }
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Notifier notifier = new NotifierSub();
        machine = new VendingMachine(notifier, 10, 10,100);
        machine.restock("A1","Apple",10,30,75);
        machine.restock("A2","Orange",10,30,75);
    }

    @Test
    public void vendSuccess() throws Exception{
        machine.addMoney(75);
        Item item = machine.vend("A1");
        assertEquals("Apple", item.getName());
    }

    @Test
    public void vendSuccessIncSales() throws Exception{
        machine.addMoney(75);
        Item item = machine.vend("A1");
        assertEquals(75, machine.getRunningSalesTotal());
    }

    @Test
    public void vendByNameSuccess() throws Exception{
        machine.addMoney(75);
        Item item = machine.vendByName("Apple");
        assertEquals(75, machine.getRunningSalesTotal());
    }

    @Test
    public void vendSeveralSuccessIncSales() throws Exception{
        machine.addMoney(150);
        machine.vendSeveral("A2", 2);
        assertEquals(150, machine.getRunningSalesTotal());
    }

    @Test
    public void vendSeveralByNameSuccessIncSales() throws Exception{
        machine.addMoney(150);
        machine.vendSeveralByName("Orange", 2);
        assertEquals(150, machine.getRunningSalesTotal());
    }

    @Test
    public void vendAllSuccessIncSales() throws Exception{
        machine.addMoney(750);
        machine.vendAll("A2");
        assertEquals(750, machine.getRunningSalesTotal());
    }

    @Test
    public void vendAllByNameSuccessIncSales() throws Exception{
        machine.addMoney(750);
        machine.vendAllByName("Orange");
        assertEquals(750, machine.getRunningSalesTotal());
    }

    @Test(expected = NotEnoughFundsException.class)
    public void vendNotEnoughFundFail() throws Exception{
        machine.addMoney(10);
        Item item = machine.vend("A1");
    }

    @Test(expected = InvalidLocationException.class)
    public void vendInvalidLocationFail() throws Exception{
        machine.addMoney(75);
        Item item = machine.vend("**");
    }

    @Test(expected = InvalidLocationException.class)
    public void vendEmptyNameFail() throws Exception{
        machine.addMoney(75);
        Item item = machine.vendByName("**");
    }

    @Test(expected = NullPointerException.class)
    public void vendNotEnoughAmountFail() throws Exception{
        machine.addMoney(1000);
        machine.vendSeveral("A2", 20);
    }

    @Test
    public void adding() throws Exception{
        machine.addMoney(10);

        int fund = machine.checkBalance();

        assertEquals(10, fund);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addingNegative() throws Exception{
        machine.addMoney(-10);
    }

    @Test
    public void refunding() throws Exception{
        machine.addMoney(10);

        int ref = machine.refundMoney();
        int fund = machine.checkBalance();

        assertEquals(10, ref);
        assertEquals(0, fund);
    }

    @Test
    public void restockNewSuccess() throws Exception{
        machine.restock("A3","Banana",10,30,75);
        machine.addMoney(75);
        Item item = machine.vend("A3");
        assertEquals("Banana", item.getName());
    }

    @Test
    public void restockOldSuccess() throws Exception{
        machine.restock("A1","Apple",10,30,75);
        machine.addMoney(1500);
        machine.vendSeveral("A1", 20);
        assertEquals(1500, machine.getRunningSalesTotal());
    }

    @Test(expected = IllegalArgumentException.class)
    public void restockNegativeFail() throws Exception{
        machine.restock("A1","Apple",-10,30,75);
    }

    @Test(expected = IllegalArgumentException.class)
    public void restockDifferentFail() throws Exception{
        machine.restock("A1","Banana",10,30,75);
    }

    @Test(expected = IllegalArgumentException.class)
    public void restockOverwhelmFail() throws Exception{
        machine.restock("A1","Apple",100,30,75);
    }

    @Test(expected = InvalidLocationException.class)
    public void restockInvalidLocationFail() throws Exception{
        machine.restock("**","Banana",10,30,75);
    }

    @Test
    public void getAllBinsTest() throws Exception{
        String status = machine.getAllBins();
        String s = "Name: Apple;Position: A1\n" +
                "Name: Orange;Position: A2\n";
        assertEquals(s, status);
    }

    @Test
    public void getPositionByNameTest() throws Exception{
        String[] position = machine.getPositionByName("Orange");
        String s = "A2";
        assertEquals(s, position[0]);
    }

    @Test
    public void getNameByPositionTest() throws Exception{
        String name = machine.getNameByPosition("A2");
        String s = "Orange";
        assertEquals(s, name);
    }
}