package mainFrame;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import valueObject.VGangjwa;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import java.awt.*;

public class PDFPrinter {

    public void print(Vector<VGangjwa> data) {
        // Step 1: PDF 생성
        File pdfFile = createPDF(data);
        if (pdfFile == null) {
            JOptionPane.showMessageDialog(null, "PDF 파일 생성에 실패했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Step 2: PDF 파일 열기
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (pdfFile.exists()) {
                    desktop.open(pdfFile);
                } else {
                    JOptionPane.showMessageDialog(null, "PDF 파일을 찾을 수 없습니다.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "이 작업은 현재 플랫폼에서 지원되지 않습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "PDF 파일을 여는 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private File createPDF(Vector<VGangjwa> data) {
        // PDF 파일 생성 경로
        File pdfFile = new File("lms_client_final/print/output.pdf");

        try (PDDocument document = new PDDocument()) {
            // 새 페이지 추가
            PDPage page = new PDPage();
            document.addPage(page);

            // 한글 폰트 설정 (NotoSansCJK-Regular.ttf를 사용)
            PDType0Font font = PDType0Font.load(document, new File("lms_client_final/print/NotoSansKR-Regular.ttf"));

            // PDF에 내용 쓰기
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(font, 12);
                contentStream.beginText();
                contentStream.setLeading(14.5f); // 줄 간격 설정
                contentStream.newLineAtOffset(50, 750); // 시작 위치 설정

                for (VGangjwa line : data) {
                    contentStream.showText(line.toString());
                    contentStream.newLine();
                }

                contentStream.endText();
            }

            // PDF 저장
            document.save(pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return pdfFile;
    }
}

