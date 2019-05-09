package com.teamtreehouse.vending;

public class Main {
    public static void main(String[] args) {
        Notifier notifier = new Notifier() {
            @Override
            public void onSale(Item item) {
                System.out.printf("Sold item %s for %s\r\n",
                        item.getName(),
                        item.getRetailPrice());
            }
        };
        VendingMachine machine = new VendingMachine(notifier, 10, 10, 10);
        Restock(machine, "A1", "Twinkies", 1, 30, 75);
        AddMoney(machine, 75);
        Vending(machine, "A1");
        Restock(machine,"A1", "Twinkies", 100, 30, 75);
        AddMoney(machine, -10);
        Vending(machine, "A1");
        Vending(machine, "");
        Vending(machine, "A2");
    }

    private static void Restock(VendingMachine machine, String input, String name, int amount, int wholesalePrice, int retailPrice){
        try {
            System.out.println("Restocking");
            machine.restock(input, name, amount, wholesalePrice, retailPrice);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void AddMoney(VendingMachine machine, int money){
        try {
            System.out.println("Adding money");
            machine.addMoney(money);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void Vending(VendingMachine machine, String input){
        try {
            System.out.println("Vending");
            machine.vend(input);
        } catch (InvalidLocationException ile) {
            System.out.println(ile);
        } catch (NotEnoughFundsException nefe) {
            System.out.println(nefe);
        }
        catch (NullPointerException nue) {
            System.out.println(nue);
        }
    }

}
