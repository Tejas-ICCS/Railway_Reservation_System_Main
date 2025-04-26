package Payment;

public class Passenger {
    private String name;
    private int age;
    private String gender;

    public Passenger(String name, int age, String gender ) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
}

