package handler;

import datastructure.LinkedList;
import entity.Schedule;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;

public class ScheduleRecord {
    private static LinkedList<Schedule> scheduleList;

    public static void makeScheduleList() {
        scheduleList = new LinkedList<>();
    }

    public static void addSchedule(Schedule newSchedule) {
        scheduleList.addLast(newSchedule);
    }

    public static LinkedList<Schedule> getScheduleList() {
        return scheduleList;
    }

    // ====================================== TXT ==============================
    public static void loadScheduleTXT() {
        if (scheduleList == null) {
            makeScheduleList();
        } else {
            scheduleList.clear();
        }

        try (BufferedReader br = new BufferedReader(new FileReader("src/data/schedule.txt"))) {
            String line;
            boolean isFirstline = true;

            while ((line = br.readLine()) != null) {
                // Skip header
                if (isFirstline) {
                    isFirstline = false;
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length < 4) continue;

                int ID = Integer.parseInt(parts[0].trim());
                int days = Integer.parseInt(parts[1].trim());
                String begin = parts[2].trim();
                String finish = parts[3].trim();

                LocalTime beginTime = LocalTime.parse(begin);
                LocalTime finishTime = LocalTime.parse(finish);

                Schedule newSchedule = new Schedule(ID, days, beginTime, finishTime);
                addSchedule(newSchedule);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
