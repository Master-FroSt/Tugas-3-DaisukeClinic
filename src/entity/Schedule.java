package entity;

import java.time.LocalTime;

public class Schedule {
    private int doctorId;
    private int day;
    private LocalTime beginTime;
    private LocalTime finishTime;

    public Schedule(int doctorId, int day, LocalTime beginTime, LocalTime finishTime) {
        this.doctorId = doctorId;
        this.day = day;
        this.beginTime = beginTime;
        this.finishTime = finishTime;
    }

    // ==================================== Getter ====================================
    public int getDoctorId() { return doctorId; }
    public int getDay() { return day; }
    public LocalTime getBeginTime() { return beginTime; }
    public LocalTime getFinishTime() { return finishTime; }

    // ============================== Setter ==============================
    public void getDoctorId(int doctorId) { this.doctorId = doctorId; }
    public void getDay(int day) { this.day = day; }
    public void getBeginTime(LocalTime beginTime) { this.beginTime = beginTime; }
    public void getFinishTime(LocalTime finishTime) { this.finishTime = finishTime; }
}
