package athlete;

public class Athlete {

    private int id;
    private String name;
    private String sport;
    private String enrollmentDate;
    private int coachId;

    public Athlete(
            int id,
            String name,
            String sport,
            String enrollmentDate,
            int coachId
    ) {

        this.id = id;
        this.name = name;
        this.sport = sport;
        this.enrollmentDate = enrollmentDate;
        this.coachId = coachId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSport() {
        return sport;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public int getCoachId() {
        return coachId;
    }
}