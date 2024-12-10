package mainFrame;

import valueObject.VGangjwa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class TimetableHTMLGenerator {

    private static final String outputPath = "lms_client_final/timeTable/timetable.html";

    public static void generateHTML(Vector<VGangjwa> courses) {
        StringBuilder html = new StringBuilder();
        html.append("""
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>강의 시간표</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    margin: 20px;
                }
                .timetable {
                    display: grid;
                    grid-template-columns: 120px repeat(5, 1fr); /* 시간 열 고정, 요일 열은 균등 분할 */
                    grid-template-rows: 50px repeat(12, 60px); /* 시간대별 높이 */
                    gap: 1px;
                    background-color: #ccc;
                }
                .time, .day, .class {
                    background-color: white;
                    text-align: center;
                    border: 1px solid #ddd;
                }
                .time {
                    background-color: #f0f0f0;
                    font-weight: bold;
                }
                .day {
                    background-color: #0078d4;
                    color: white;
                    font-weight: bold;
                }
                .class {
                    background-color: #d0f0d0;
                    text-align: center;
                }
                .class span {
                    display: block;
                }
                .header {
                    grid-column: 1 / span 6;
                    text-align: center;
                    background-color: #005bb5;
                    color: white;
                    padding: 10px;
                    font-size: 1.2em;
                }
            </style>
        </head>
        <body>
            <div class="header">강의 시간표</div>
            <div class="timetable">
                <div class="time"></div>
                <div class="day">월요일</div>
                <div class="day">화요일</div>
                <div class="day">수요일</div>
                <div class="day">목요일</div>
                <div class="day">금요일</div>
    """);

        String[] timeLabels = {
                "09:00", "10:00", "11:00", "12:00",
                "13:00", "14:00", "15:00", "16:00",
                "17:00", "18:00", "19:00"
        };

        for (String label : timeLabels) {
            html.append("<div class=\"time\">").append(label).append("</div>\n");
            for (int i = 0; i < 5; i++) {
                html.append("<div></div>\n");
            }
        }

        for (VGangjwa course : courses) {
            String courseName = course.getName();
            String lecturer = course.getLecturer();
            String time = course.getTime();

            String days = time.substring(0, 2);
            String timeRange = time.substring(2);

            String[] timeParts = timeRange.split("-");
            int startHour = Integer.parseInt(timeParts[0].substring(0, 2));
            int startMinute = Integer.parseInt(timeParts[0].substring(2));
            int endHour = Integer.parseInt(timeParts[1].substring(0, 2));
            int endMinute = Integer.parseInt(timeParts[1].substring(2));

            int startRow = (startHour - 9) * 2 + (startMinute >= 30 ? 1 : 0) + 2;
            int endRow = (endHour - 9) * 2 + (endMinute >= 30 ? 1 : 0) + 2;

            for (char day : days.toCharArray()) {
                int column = switch (day) {
                    case '월' -> 2;
                    case '화' -> 3;
                    case '수' -> 4;
                    case '목' -> 5;
                    case '금' -> 6;
                    default -> -1;
                };

                if (column != -1) {
                    html.append("<div class=\"class\" style=\"grid-row: ")
                            .append(startRow).append(" / ").append(endRow)
                            .append("; grid-column: ").append(column).append(";\">")
                            .append("<span>").append(courseName).append("</span>")
                            .append("<span>").append(lecturer).append("</span>")
                            .append("</div>\n");
                }
            }
        }

        html.append("""
            </div>
        </body>
        </html>
        """);

        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(html.toString());
            File file = new File(outputPath);
            if (file.exists()) {
                java.awt.Desktop.getDesktop().browse(file.toURI());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

