package main.java.model;

public class Activity {
    private int id;
    private int idEmployee;
    private String activity;
    private String involved;
    private String date;

    public Activity(){
    }

    public Activity(int id, int idEmployee, String activity, String involved, String date) {
        super();
        this.id = id;
        this.idEmployee = idEmployee;
        this.activity = activity;
        this.involved = involved;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getInvolved() {
        return involved;
    }

    public void setInvolved(String involved) {
        this.involved = involved;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", idEmployee=" + idEmployee +
                ", activity='" + activity + '\'' +
                ", involved='" + involved + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
