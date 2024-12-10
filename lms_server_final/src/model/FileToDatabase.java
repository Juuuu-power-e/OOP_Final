package model;

import aspect.LogManager;
import constants.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class FileToDatabase {

    private static final String URL = Configuration.loadConfig("URL");
    private static final String USER = Configuration.loadConfig("USER");
    private static final String PASSWORD = Configuration.loadConfig("PASSWORD");

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void loadFileToDatabase(String filePath) {
        File file = new File(filePath);
        String tableName = file.getName().replace(".txt", "").replace(" ", "_"); // 공백을 _로 대체
        try (Connection conn = getConnection()) {
            // 첫 번째 줄로 필드 정보 추출
            String[] fields = extractFields(file);

            // 테이블 생성
            createTableIfNotExists(conn, tableName, fields);

            // 데이터 삽입
            insertDataFromFile(conn, file, tableName, fields);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] extractFields(File file) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim(); // 공백 제거
                if (!line.isEmpty()) { // 빈 줄이 아닌 경우만 처리
                    return line.split(" ");
                }
            }
        }
        throw new FileNotFoundException("파일이 비어 있습니다.");
    }

    private void createTableIfNotExists(Connection conn, String tableName, String[] fields) throws SQLException {
        StringBuilder createTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (");
        for (int i = 0; i < fields.length; i++) {
            createTableSQL.append("field").append(i + 1).append(" VARCHAR(255)");
            if (i < fields.length - 1) {
                createTableSQL.append(", ");
            }
        }
        createTableSQL.append(")");
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL.toString());
        } catch (SQLSyntaxErrorException e) {
            LogManager.getInstance().log("Error creating table for: " + tableName);
        }
    }

    private void insertDataFromFile(Connection conn, File file, String tableName, String[] fields) throws FileNotFoundException, SQLException {
        StringBuilder insertSQL = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
        for (int i = 0; i < fields.length; i++) {
            insertSQL.append("?");
            if (i < fields.length - 1) {
                insertSQL.append(", ");
            }
        }
        insertSQL.append(")");

        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL.toString());
             Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim(); // 공백 제거
                if (!line.isEmpty()) { // 빈 줄이 아닌 경우만 처리
                    String[] tokens = line.split(" ");
                    for (int i = 0; i < fields.length; i++) {
                        pstmt.setString(i + 1, i < tokens.length ? tokens[i] : null); // 값이 없으면 NULL 처리
                    }
                    pstmt.addBatch();
                }
            }
            pstmt.executeBatch();
        }
    }

    public static void main(String[] args) {
        FileToDatabase loader = new FileToDatabase();
        File dataDir = new File(System.getProperty("user.dir") + "/lms_server_final/data");

        if (dataDir.isDirectory()) {
            File[] txtFiles = dataDir.listFiles((dir, name) -> name.endsWith(".txt"));
            if (txtFiles != null) {
                for (File file : txtFiles) {
                    loader.loadFileToDatabase(file.getAbsolutePath());
                    System.out.println("Loaded file: " + file.getName().replace(" ", "_"));
                }
            } else {
                System.out.println("No .txt files found in the directory.");
            }
        } else {
            System.out.println("The specified path is not a directory.");
        }
    }
}
