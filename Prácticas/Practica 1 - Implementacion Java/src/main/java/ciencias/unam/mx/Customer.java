package ciencias.unam.mx;

public class Customer {
    private static int customerID;
    private String name;
    private String surname;
    private String rfc;
    private String mail;
    private Region region;

    public Customer(String name, String surname, String rfc, String mail, Region region) {
        this.name = name;
        this.surname = surname;
        this.rfc = rfc;
        this.mail = mail;
        this.region = region;
        customerID++;
    }

    public static int getCustomerId() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override public String toString() {
        return String.format("ID       : %s\n" +
                             "Nombre   : %s\n" +
                             "Apellido : %s\n" +
                             "RFC      : %s\n" +
                             "Email    : %s\n" +
                             "Region   : %s\n" ,
                             customerID, name, surname,
                             rfc, mail, region);
    }
}
