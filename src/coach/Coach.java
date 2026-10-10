package coach;

public class Coach {

    private int id;
    private String name;
    private String specialization;
    private String phone;
    private String email;

    public Coach(
            int id,
            String name,
            String specialization,
            String phone,
            String email
    ) {

        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}