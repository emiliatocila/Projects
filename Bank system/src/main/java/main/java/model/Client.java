package main.java.model;

public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private String idCardNr;
    private String cnp;
    private String address;

    public Client(){
    }

    public Client(String firstName, String lastName, String idCardNr, String cnp, String address, int id) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.idCardNr = idCardNr;
        this.cnp = cnp;
        this.address = address;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdCardNr() {
        return idCardNr;
    }

    public void setIdCardNr(String idCardNr) {
        this.idCardNr = idCardNr;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", idCardNr=" + idCardNr + '\'' +
                ", cnp=" + cnp + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
