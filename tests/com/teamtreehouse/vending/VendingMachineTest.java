package com.teamtreehouse.vending;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendingMachineTest {
    public VendingMachine machine;

    public class NotifierSub implements Notifier{
        @Override
        public void onSale(Item item) {
            return;
        }
    }

    @Before
    public void setUp() throws Exception {
        Notifier notifier = new NotifierSub();
        machine = new VendingMachine(notifier, 10, 10,10);
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
    public void refunding() throws Exception{
        machine.addMoney(10);

        int ref = machine.refundMoney();

        assertEquals(10, ref);
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

    @Test
    public void vendSeveralByNameSuccessIncSales() throws Exception{
        machine.addMoney(150);
        machine.vendSeveralByName("Orange", 2);
        assertEquals(150, machine.getRunningSalesTotal());
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