package test;

import aspect.ExceptionManager;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class TestExceptionFrame extends JFrame {

    public TestExceptionFrame() {
        setTitle("Test Exceptions");
        setSize(400, 200);
        setLocationRelativeTo(null);

        // 패널 설정
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        // 버튼 추가
        JButton nullPointerButton = new JButton("Null Pointer Exception");
        JButton arrayIndexButton = new JButton("Array Index Out of Bounds");
        JButton arithmeticButton = new JButton("Arithmetic Exception");
        JButton customExceptionButton = new JButton("Custom Exception");

        // 각 버튼에 이벤트 리스너 추가
        nullPointerButton.addActionListener(e -> handleException(new NullPointerException("Test Null Pointer Exception")));
        arrayIndexButton.addActionListener(e -> handleException(new ArrayIndexOutOfBoundsException("Test Array Index Out of Bounds")));
        arithmeticButton.addActionListener(e -> handleException(new ArithmeticException("Test Arithmetic Exception")));
        customExceptionButton.addActionListener(e -> handleException(new CustomTestException("Test Custom Exception")));

        // 버튼 패널에 추가
        buttonPanel.add(nullPointerButton);
        buttonPanel.add(arrayIndexButton);
        buttonPanel.add(arithmeticButton);
        buttonPanel.add(customExceptionButton);

        // 프레임에 패널 추가
        add(buttonPanel, BorderLayout.CENTER);

        // 보이기
        setVisible(true);
    }

    private void handleException(Exception e) {
        ExceptionManager exceptionManager = new ExceptionManager();
        exceptionManager.process(e);

        // 사용자에게 익셉션 발생을 알림
        JOptionPane.showMessageDialog(
                this,
                "An exception occurred:\n" + e.getMessage(),
                "Exception Occurred",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public void run() {
        SwingUtilities.invokeLater(TestExceptionFrame::new);
    }

    // 사용자 정의 예외
    static class CustomTestException extends RuntimeException {
        public CustomTestException(String message) {
            super(message);
        }
    }

}
