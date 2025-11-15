package attestation3.enitities;

public class EmployeeRequest {
    private String city;
    private String name;
    private String position;
    private String surname;
    private String email;

    public EmployeeRequest(String city, String name, String position, String surname) {
        this.city = city;
        this.name = name;
        this.position = position;
        this.surname = surname;
    }
    public EmployeeRequest(String name, String position, String surname) {
        this.name = name;
        this.position = position;
        this.surname = surname;
    }
    // конструктор с лишним полем, которого нет в БД
    public EmployeeRequest(String city, String name, String position, String surname, String email) {
        this.city = city;
        this.name = name;
        this.position = position;
        this.surname = surname;
        this.email = email;
    }
    public EmployeeRequest(String city) {
        this.city = city;
    }
    public EmployeeRequest() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
