package gui;
import aspect.LogManager;
import constants.Configuration;
import constants.LanguageManager;
import constants.TextObserver;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Content;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class MenuManager implements TextObserver {
    private JFrame parent;
    private JTextArea logArea;
    private ServerControl serverControl;
    private LanguageManager languageManager;
    private JMenu settingsMenu;
    private JMenuItem changePortItem;
    private JMenuItem changeSerialItem;
    private JMenu logMenu;
    private JMenuItem clearLogItem;
    private JMenuItem saveLogItem;
    private JMenu displayMenu;
    private JMenuItem changeLanguageItem;
    private JMenuItem changeTimeFormatItem;

    private String[] supportedLanguages = { "English", "한국어", "Español", "Français", "Deutsch" };
    private String[] locales = { "en", "ko", "es", "fr", "de" };



    public MenuManager(JFrame parent, JTextArea logArea, ServerControl serverControl) {
        this.parent = parent;
        this.logArea = logArea;
        this.serverControl = serverControl;
        this.languageManager = LanguageManager.getInstance();
        LanguageManager.getInstance().registerObserver(this);
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // 설정 메뉴
        settingsMenu = new JMenu(languageManager.get("menu.settings"));
        changePortItem = new JMenuItem(languageManager.get("menu.changePort"));
        changePortItem.addActionListener(e -> changePort());
        changeSerialItem = new JMenuItem(languageManager.get("menu.changeSerial"));
        changeSerialItem.addActionListener(e -> changeSerialNumber());
        settingsMenu.add(changePortItem);
        settingsMenu.add(changeSerialItem);

        // 로그 메뉴
        logMenu = new JMenu(languageManager.get("menu.logs"));
        clearLogItem = new JMenuItem(languageManager.get("menu.clearLogs"));
        clearLogItem.addActionListener(e -> logArea.setText(""));
        saveLogItem = new JMenuItem(languageManager.get("menu.saveLogs"));
        saveLogItem.addActionListener(e -> saveLogToFile());
        logMenu.add(clearLogItem);
        logMenu.add(saveLogItem);





        // 표시 메뉴
        displayMenu = new JMenu(languageManager.get("menu.display"));
        changeLanguageItem = new JMenuItem(languageManager.get("menu.changeLanguage"));
        changeLanguageItem.addActionListener(e -> changeLanguage());
        changeTimeFormatItem = new JMenuItem(languageManager.get("menu.changeTimeFormat"));
        changeTimeFormatItem.addActionListener(e -> toggleTimeFormat());
        displayMenu.add(changeLanguageItem);
        displayMenu.add(changeTimeFormatItem);

        // 메뉴바 구성
        menuBar.add(settingsMenu);
        menuBar.add(logMenu);
        menuBar.add(displayMenu);

        return menuBar;
    }

    private void changePort() {
        if (serverControl.isRunning()) {
            JOptionPane.showMessageDialog(
                    parent,
                    languageManager.get("dialog.changePort.running"),
                    languageManager.get("dialog.changePort.title"),
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String newPort = JOptionPane.showInputDialog(
                parent,
                languageManager.get("dialog.changePort.message")
        );
        if (newPort == null || newPort.isEmpty()) return;

        try {
            int port = Integer.parseInt(newPort);
            if (port < 1 || port > 65535) {
                JOptionPane.showMessageDialog(
                        parent,
                        languageManager.get("dialog.changePort.invalid"),
                        languageManager.get("dialog.error.title"),
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            updateConfig("server.port", newPort);
            JOptionPane.showMessageDialog(
                    parent,
                    languageManager.get("dialog.changePort.success"),
                    languageManager.get("dialog.success.title"),
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    parent,
                    languageManager.get("dialog.changePort.error"),
                    languageManager.get("dialog.error.title"),
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void changeSerialNumber() {
        if (serverControl.isRunning()) {
            JOptionPane.showMessageDialog(
                    parent,
                    languageManager.get("dialog.changeSerial.running"),
                    languageManager.get("dialog.changeSerial.title"),
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String newSerial = JOptionPane.showInputDialog(
                parent,
                languageManager.get("dialog.changeSerial.message")
        );
        if (newSerial == null || newSerial.isEmpty()) return;

        updateConfig("server.serial", newSerial);
        JOptionPane.showMessageDialog(
                parent,
                languageManager.get("dialog.changeSerial.success"),
                languageManager.get("dialog.success.title"),
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void updateConfig(String key, String value) {
        Properties properties = new Properties();

        // 기존 설정 파일의 내용을 먼저 읽어오기
        try (FileInputStream fis = new FileInputStream(Configuration.CONFIG_FILE_PATH)) {
            properties.load(fis);
        } catch (IOException ex) {
            System.err.println("기존 설정 파일을 읽는 중 오류 발생. 새 파일을 생성합니다.");
        }

        // 변경된 값 업데이트
        properties.setProperty(key, value);

        // 업데이트된 내용을 파일에 저장
        try (FileOutputStream fos = new FileOutputStream(Configuration.CONFIG_FILE_PATH)) {
            properties.store(fos, "Updated " + key);
            JOptionPane.showMessageDialog(parent,
                    languageManager.get("dialog.config.success"),
                    languageManager.get("dialog.success.title"),
                    JOptionPane.INFORMATION_MESSAGE
            );
            LogManager.getInstance().log("Updated " + key + " to " + value);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent,
                    languageManager.get("dialog.config.error"),
                    languageManager.get("dialog.error.title"),
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void saveLogToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(languageManager.get("dialog.saveLogs.title"));

        // 기본 확장자를 txt로 설정
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                languageManager.get("dialog.saveLogs.filter"), "txt"));

        int option = fileChooser.showSaveDialog(parent);
        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                java.io.File file = fileChooser.getSelectedFile();

                // 파일 확장자가 .txt가 아니면 추가
                if (!file.getName().toLowerCase().endsWith(".txt")) {
                    file = new java.io.File(file.getAbsolutePath() + ".txt");
                }

                java.nio.file.Files.write(file.toPath(), logArea.getText().getBytes());
                JOptionPane.showMessageDialog(parent,
                        languageManager.get("dialog.saveLogs.success"),
                        languageManager.get("dialog.success.title"),
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent,
                        languageManager.get("dialog.saveLogs.error"),
                        languageManager.get("dialog.error.title"),
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void toggleTimeFormat() {
        boolean currentFormat = LogManager.getInstance().isShowSeconds();
        LogManager.getInstance().setShowSeconds(!currentFormat);
        JOptionPane.showMessageDialog(parent,
                languageManager.get("dialog.changeTimeFormat.success"),
                languageManager.get("dialog.success.title"),
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void changeLanguage() {



        // 드롭다운 메뉴 생성
        JComboBox<String> languageDropdown = new JComboBox<>(supportedLanguages);

        // 선택 다이얼로그
        int option = JOptionPane.showConfirmDialog(
                parent,
                languageDropdown,
                LanguageManager.getInstance().get("dialog.changeLanguage.message"),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            int selectedIndex = languageDropdown.getSelectedIndex();
            String selectedLocale = locales[selectedIndex];

            // 선택한 언어가 번역 리소스를 가지고 있는지 확인
            if (hasLanguagePack(selectedLocale)) {
                LanguageManager.getInstance().setLocale(new Locale(selectedLocale));
            } else {
                // 번역 옵션 다이얼로그
                handleMissingLanguagePack(selectedLocale, supportedLanguages[selectedIndex]);
            }
        }
    }

    // 언어팩 존재 여부 확인
    private boolean hasLanguagePack(String locale) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("messages", new Locale(locale));
            return bundle.getLocale().getLanguage().equals(locale);
        } catch (MissingResourceException e) {
            return false;
        }
    }

    // 언어팩이 없는 경우 처리
    private void handleMissingLanguagePack(String locale, String languageName) {
        String[] options = {
                LanguageManager.getInstance().get("dialog.missingLanguage.manual"),
                LanguageManager.getInstance().get("dialog.missingLanguage.auto"),
                LanguageManager.getInstance().get("dialog.missingLanguage.cancel")
        };

        SwingUtilities.invokeLater(() -> {
            int choice = JOptionPane.showOptionDialog(
                    this.parent,
                    LanguageManager.getInstance().get("dialog.missingLanguage.message").replace("{language}", languageName),
                    LanguageManager.getInstance().get("dialog.missingLanguage.title"),
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            handleChoice(choice, locale, languageName); // 선택에 따라 처리
        });

    }

    private void handleChoice(int choice, String locale, String languageName) {
        switch (choice) {
            case 0:
                manualTranslate(locale, languageName);
                break;
            case 1:
                autoTranslate(locale, languageName);
                break;
            case 2:
            default:
                // 취소 처리
                break;
        }
    }


    // 직접 번역
    private void manualTranslate(String targetLocale, String languageName) {
        JFrame translateFrame = new JFrame(LanguageManager.getInstance().get("dialog.manualTranslate.title"));
        translateFrame.setSize(600, 400);
        translateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        translateFrame.setLayout(new BorderLayout());

        // 드롭다운 (출발 언어, 도착 언어)
        JPanel dropdownPanel = new JPanel();
        JComboBox<String> sourceLanguageDropdown = new JComboBox<>(new String[]{"English", "한국어"});
//        JComboBox<String> targetLanguageDropdown = new JComboBox<>(new String[]{"Español", "Français", "Deutsch"});
        JLabel targetLanguageDropdown = new JLabel(languageName);
        dropdownPanel.add(new JLabel(LanguageManager.getInstance().get("dialog.manualTranslate.sourceLanguage")));
        dropdownPanel.add(sourceLanguageDropdown);
        dropdownPanel.add(new JLabel(LanguageManager.getInstance().get("dialog.manualTranslate.targetLanguage")));
        dropdownPanel.add(targetLanguageDropdown);

        // 테이블 설정
        String[] columnNames = {"Key", "Source Text", "Target Text"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // 데이터 로드
        JButton confirmButton = new JButton(LanguageManager.getInstance().get("button.confirm"));
        confirmButton.addActionListener(e -> loadTranslationData(sourceLanguageDropdown, tableModel));

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton(LanguageManager.getInstance().get("button.save"));
        JButton completeButton = new JButton(LanguageManager.getInstance().get("button.complete"));

        buttonPanel.add(confirmButton); // 확인 버튼
        buttonPanel.add(saveButton);   // 저장 버튼
        buttonPanel.add(completeButton); // 완료 버튼

        // 저장 버튼 동작
        saveButton.addActionListener(e -> saveTranslationData(tableModel, targetLocale, languageName));

        // 완료 버튼 동작
        completeButton.addActionListener(e -> translateFrame.dispose());

        // 프레임 구성
        translateFrame.add(dropdownPanel, BorderLayout.NORTH);
        translateFrame.add(scrollPane, BorderLayout.CENTER);
        translateFrame.add(buttonPanel, BorderLayout.SOUTH);

        translateFrame.setVisible(true);
    }


    private void loadTranslationData(JComboBox<String> sourceLanguageDropdown, DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // 기존 데이터 초기화

        String sourceLocale = sourceLanguageDropdown.getSelectedItem().toString().toLowerCase();
        ResourceBundle sourceBundle = ResourceBundle.getBundle("messages", new Locale(sourceLocale));
        ResourceBundle defaultBundle = ResourceBundle.getBundle("messages", Locale.KOREAN);

        // 데이터 로드
        sourceBundle.keySet().forEach(key -> {
            String sourceText = sourceBundle.getString(key);
            String defaultText = defaultBundle.containsKey(key) ? defaultBundle.getString(key) : "";
            tableModel.addRow(new Object[]{key, sourceText, defaultText});
        });
    }

    private void saveTranslationData(DefaultTableModel tableModel, String targetLocale, String targetLanguageName) {
        Properties properties = new Properties();

        // 테이블 데이터를 저장
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String key = tableModel.getValueAt(i, 0).toString();
            String targetText = tableModel.getValueAt(i, 2).toString();

            if (targetText == null || targetText.isEmpty()) {
                // 기본값으로 채우기
                targetText = tableModel.getValueAt(i, 1).toString();
            }

            properties.setProperty(key, targetText);
        }

        // 언어팩 파일 저장
        try (FileOutputStream fos = new FileOutputStream("lms_server_final/src/resources/messages_" + targetLocale + ".properties")) {
            properties.store(fos, "Generated by manualTranslate");
            JOptionPane.showMessageDialog(null, LanguageManager.getInstance().get("dialog.manualTranslate.success"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, LanguageManager.getInstance().get("dialog.manualTranslate.error"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    // 자동 번역
    private void autoTranslate(String locale, String languageName) {
        // 번역할 데이터 로드
        ResourceBundle sourceBundle = ResourceBundle.getBundle("messages", Locale.ENGLISH); // 기본 언어를 영어로 설정
        Properties translatedProperties = new Properties();

        // 번역 수행
        sourceBundle.keySet().forEach(key -> {
            String originalText = sourceBundle.getString(key);
            String translatedText = translateText(originalText, locale);
            translatedProperties.setProperty(key, translatedText);
        });

        // 번역 결과 저장
        try (FileOutputStream fos = new FileOutputStream("lms_server_final/src/resources/messages_" + locale + ".properties")) {
            translatedProperties.store(fos, "Generated by autoTranslate");
            JOptionPane.showMessageDialog(parent,
                    LanguageManager.getInstance().get("dialog.manualTranslate.success"),
                    LanguageManager.getInstance().get("dialog.success.title"),
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent,
                    LanguageManager.getInstance().get("dialog.manualTranslate.error"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // LibreTranslate API로 번역 수행
    private String translateText(String text, String targetLocale) {
        try {
            String apiUrl = "http://localhost:5000/translate";
            String jsonRequest = new JSONObject()
                    .put("q", text)
                    .put("source", "en") // 출발 언어: 영어
                    .put("target", targetLocale) // 도착 언어: 선택된 로케일
                    .put("format", "text")
                    .toString();

            Content response = Request.post(apiUrl)
                    .addHeader("Content-Type", "application/json")
                    .bodyString(jsonRequest, org.apache.hc.core5.http.ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent();

            JSONObject jsonResponse = new JSONObject(response.asString());
            return jsonResponse.getString("translatedText");
        } catch (Exception e) {
            e.printStackTrace();
            return text; // 번역 실패 시 원본 텍스트 반환
        }
    }


    // 텍스트 업데이트 메서드
    private void updateTexts() {
        // 메뉴와 버튼의 텍스트를 새 언어로 업데이트
        parent.setTitle(languageManager.get("window.title")); // 창 제목 업데이트
    }

    @Override
    public void onLanguageChanged() {
        // 메뉴 제목 업데이트
        settingsMenu.setText(LanguageManager.getInstance().get("menu.settings"));
        logMenu.setText(LanguageManager.getInstance().get("menu.logs"));
        displayMenu.setText(LanguageManager.getInstance().get("menu.display"));

        // 메뉴 아이템 텍스트 업데이트
        settingsMenu.getItem(0).setText(LanguageManager.getInstance().get("menu.changePort"));
        settingsMenu.getItem(1).setText(LanguageManager.getInstance().get("menu.changeSerial"));

        logMenu.getItem(0).setText(LanguageManager.getInstance().get("menu.clearLogs"));
        logMenu.getItem(1).setText(LanguageManager.getInstance().get("menu.saveLogs"));

        displayMenu.getItem(0).setText(LanguageManager.getInstance().get("menu.changeLanguage"));
        displayMenu.getItem(1).setText(LanguageManager.getInstance().get("menu.changeTimeFormat"));
    }

}
