package attendance;

public class Attendance {

    private int id;
    private String personType;
    private int personId;
    private String attendanceDate;
    private String status;

    public Attendance(int id, String personType, int personId,
                      String attendanceDate, String status) {

        this.id = id;
        this.personType = personType;
        this.personId = personId;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getPersonType() {
        return personType;
    }

    public int getPersonId() {
        return personId;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public String getStatus() {
        return status;
    }
}