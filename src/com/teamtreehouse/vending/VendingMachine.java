package com.teamtreehouse.vending;


import java.util.HashMap;
import java.util.Map;

public class VendingMachine {
    private final Notifier notifier;
    private final AbstractChooser chooser;
    private final Creditor creditor;
    private final Bin[][] bins;
    private int runningSalesTotal;
    private Map<String, String> PositionToNameMap = new HashMap<>();

    public VendingMachine(Notifier notifier, int rowCount, int columnCount, int maxItemsPerBin) {
        this.notifier = notifier;
        chooser = new AlphaNumericChooser(rowCount, columnCount);
        creditor = new Creditor();
        runningSalesTotal = 0;
        bins = new Bin[rowCount][columnCount];
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                bins[row][col] = new Bin(maxItemsPerBin);
            }
        }
    }

    public void addMoney(int money) {
        if(money < 0){
            throw new IllegalArgumentException("Money can't be negative");
        }
        else {
            creditor.addFunds(money);
        }
    }

    public int refundMoney() {
        return creditor.refund();
    }

    public int checkBalance() {
        return creditor.getAvailableFunds();
    }

    public Item vend(String input) throws InvalidLocationException, NotEnoughFundsException {
        Bin bin = binByInput(input);
        int price = bin.getItemPrice();
        creditor.deduct(price);
        runningSalesTotal += price;
        Item item = bin.release();
        if(bin.isEmpty()){
            PositionToNameMap.remove(input);
        }
        notifier.onSale(item);
        return item;
    }

    public Item[] vendSeveral(String input, int num) throws InvalidLocationException, NotEnoughFundsException {
        Item[] items = new Item[100];
        for(int i = 0; i < num; i++) {
            Item item = vend(input);
            items[i] = item;
            notifier.onSale(item);
            System.out.println(i);
        }
        return items;
    }

    public Item[] vendAll(String input) throws InvalidLocationException, NotEnoughFundsException {
        Item[] items = new Item[100];
        try {
            for (int i = 0; i < 10; i++) {
                Item item = vend(input);
                items[i] = item;
                notifier.onSale(item);
                System.out.println(i);
            }
        }
        catch (Exception e){}
        return items;
    }

    public Item[] vendAllByName(String name) throws InvalidLocationException, NotEnoughFundsException {
        Item[] items = new Item[]{};
        for(String key : PositionToNameMap.keySet()){
            if(PositionToNameMap.get(key).equals(name)){
                concat(items,vendAll(key));
            }
        }
        return items;
    }

    public Item vendByName(String name) throws InvalidLocationException, NotEnoughFundsException{
        Item item;
        for(String key : PositionToNameMap.keySet()){
            if(PositionToNameMap.get(key).equals(name)){
                item = vend(key);
                return item;
            }
        }
        return vend("NULL");
    }

    public Item[] vendSeveralByName(String name, int num) throws InvalidLocationException, NotEnoughFundsException{
        Item[] items = new Item[]{};
        for(String key : PositionToNameMap.keySet()){
            if(PositionToNameMap.get(key).equals(name)){
                concat(items,vendSeveral(key, num));
                break;
            }
        }
        return items;
    }

    public int getRunningSalesTotal() {
        return runningSalesTotal;
    }

    public void restock(String input, String name, int amount, int wholesalePrice, int retailPrice) throws InvalidLocationException {
        Bin bin = binByInput(input);
        bin.restock(name, amount, wholesalePrice, retailPrice);
        PositionToNameMap.put(input, name);
    }

    public String getAllBins(){
        String res = "";
        for (String key : PositionToNameMap.keySet()){
            res += "Name: ";
            res += PositionToNameMap.get(key);
            res += ";";
            res += "Position: ";
            res += key;
            res += "\n";
        }
        return res;
    }

    public String[] getPositionByName(String name){
        String[] positions = new String[100];
        int i = 0;
        for(String key : PositionToNameMap.keySet()){
            if(PositionToNameMap.get(key).equals(name)){
                positions[i] = key;
                i++;
            }
        }
        return positions;
    }

    public String getNameByPosition(String position){
        String name = PositionToNameMap.get(position);
        return name;
    }

    private Bin binByInput(String input) throws InvalidLocationException {
        AbstractChooser.Location location = chooser.locationFromInput(input);
        return bins[location.getRow()][location.getColumn()];
    }

    private static Item[] concat(Item[] a, Item[] b) {
        Item[] c= new Item[a.length+b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

}