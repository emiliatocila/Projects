package main.java.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private int id;
    private int idClient;
    private String type;
    private int amount;
    private String dateOfCreation;

    public Account(){
    }

    public Account(int id, int idClient, String type, int amount, String dateOfCreation) {
        super();
        this.id = id;
        this.idClient = idClient;
        this.type = type;
        this.amount = amount;
        this.dateOfCreation = dateOfCreation;
    }

    public List<String> generateBillContent(String utility, int sum, String date, String firstName, String lastName){
        List<String> lines = new ArrayList<String>();
        lines.add("UTILITY BILL");
        lines.add(firstName + " " + lastName);
        lines.add(date);
        lines.add(utility + ".................." + sum + " lei");
        lines.add("Current balance: " + this.getAmount() + " lei");
        return lines;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", idClient=" + idClient +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", dateOfCreation='" + dateOfCreation + '\'' +
                '}';
    }
}