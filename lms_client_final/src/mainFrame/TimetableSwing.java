package mainFrame;

import valueObject.VGangjwa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class TimetableSwing {

    public static void displayTimetable(Vector<VGangjwa> courses) {
        String[] columns = {"시간", "월요일", "화요일", "수요일", "목요일", "금요일"};
        String[] timeSlots = {
                "09:00 ~ 09:30", "09:30 ~ 10:00", "10:00 ~ 10:30", "10:30 ~ 11:00", "11:00 ~ 11:30",
                "11:30 ~ 12:00", "12:00 ~ 12:30", "12:30 ~ 13:00", "13:00 ~ 13:30", "13:30 ~ 14:00",
                "14:00 ~ 14:30", "14:30 ~ 15:00", "15:00 ~ 15:30", "15:30 ~ 16:00", "16:00 ~ 16:30",
                "16:30 ~ 17:00", "17:00 ~ 17:30", "17:30 ~ 18:00", "18:00 ~ 18:30", "18:30 ~ 19:00"
        };

        DefaultTableModel model = new DefaultTableModel(columns, timeSlots.length);
        JTable table = new JTable(model);

        // 시간 열 채우기
        for (int i = 0; i < timeSlots.length; i++) {
            model.setValueAt(timeSlots[i], i, 0);
        }

        // 강의 데이터를 시간표에 배치
        for (VGangjwa course : courses) {
            String[] dayTimes = course.getTime().split(",");
            for (String dayTime : dayTimes) {
                String day = dayTime.replaceAll("[^가-힣]", ""); // 요일 추출
                String time = dayTime.replaceAll("[^0-9\\-]", ""); // 시간 추출
                String[] timeRange = time.split("-");

                if (timeRange.length < 2) {
                    System.err.println("Invalid time format for course: " + course);
                    continue; // 시간 포맷이 잘못된 경우 스킵
                }

                int startSlot = getSlotIndex(timeRange[0], timeSlots);
                int endSlot = getSlotIndex(timeRange[1], timeSlots);
                int dayIndex = switch (day) {
                    case "월" -> 1;
                    case "화" -> 2;
                    case "수" -> 3;
                    case "목" -> 4;
                    case "금" -> 5;
                    default -> -1;
                };

                if (startSlot == -1 || endSlot == -1 || dayIndex == -1) {
                    System.err.println("Failed to map course: " + course);
                    continue; // 매핑 실패 시 스킵
                }

                for (int i = startSlot; i <= endSlot; i++) {
                    model.setValueAt(course.getName() + " (" + course.getLecturer() + ")", i, dayIndex);
                }
            }
        }

        // JTable 설정
        table.setRowHeight(30);
        JFrame frame = new JFrame("시간표");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static int getSlotIndex(String time, String[] timeSlots) {
        int inputHour = Integer.parseInt(time.substring(0, 2));
        int inputMinute = Integer.parseInt(time.substring(2, 4));
        for (int i = 0; i < timeSlots.length; i++) {
            String[] range = timeSlots[i].split(" ~ ");
            String[] start = range[0].split(":");
            int startHour = Integer.parseInt(start[0]);
            int startMinute = Integer.parseInt(start[1]);

            // 시작 시간이 time과 일치하면 해당 인덱스를 반환
            if (startHour == inputHour && startMinute == inputMinute) {
                return i;
            }
        }
        return -1; // 매칭 실패 시
    }

}
