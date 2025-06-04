package database;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 1) TemplateLine: 라벨+텍스트필드+삭제버튼을 하나로 묶은 커스텀 JPanel.
 *    - getTextFieldValue(): 텍스트필드의 값을 꺼낼 수 있는 메서드.
 *    - 삭제 버튼 클릭 시, 자신을 부모 컨테이너와 리스트에서 제거.
 */
class TemplateLine extends JPanel {
    private JLabel label;
    private JButton btnImgAdd;
    private String imgPath; // 이미지 경로를 저장할 변수 (필요시 사용)
    private JTextField tFName;
    private JTextField tFAnswer;
    private JButton deleteButton;

    // 부모 컨테이너와 관리 리스트 참조
    private JPanel parentContainer;
    private List<TemplateLine> lineList;

    public TemplateLine(JPanel parentContainer, List<TemplateLine> lineList, String labelText) {
        this.parentContainer = parentContainer;
        this.lineList = lineList;

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100)); // 높이 고정

        // 1) 레이블
        label = new JLabel(labelText);

        btnImgAdd = new JButton("이미지 추가");
        btnImgAdd.setPreferredSize(new Dimension(100, 100));
        btnImgAdd.addActionListener(e -> {
        	JFileChooser fc = new JFileChooser();
			File curDir = new File("../../../java project img resource\\champion");
			fc.setCurrentDirectory(curDir);
			
			fc.showOpenDialog(fc);
			File selectedFile = fc.getSelectedFile();
			
			imgPath = selectedFile.getPath();
			
			try {
				BufferedImage wimg = ImageIO.read(new File(selectedFile.getPath()));
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(wimg, "png", baos);
				byte[] pngBytes = baos.toByteArray();
				baos.close();
				
				ImageIcon icon = new ImageIcon(pngBytes);
				btnImgAdd.setIcon(icon);	
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        
        // 2) 텍스트필드
        tFName = new JTextField(10);
        tFName.setToolTipText("이름을 입력하세요");
        tFAnswer = new JTextField(10);
        tFAnswer.setToolTipText("정답을 입력하세요");

        // 3) 삭제 버튼
        deleteButton = new JButton("삭제");
        deleteButton.addActionListener(e -> removeThisLine());

        // 4) 구성 요소를 이 패널에 추가
        add(label);
        add(btnImgAdd);
        add(tFName);
        add(tFAnswer);
        add(deleteButton);
    }
    
    public String getImgPath() {
    	return imgPath;
    }

    // 텍스트필드에 입력된 값을 꺼내는 메서드
    public String gettFNameValue() {
        return tFName.getText().trim();
    }
    
    public String gettFAnswerValue() {
		return tFAnswer.getText().trim();
	}

    // 이 라인을 지우는 메서드: 부모와 리스트에서 제거 후 UI 갱신
    private void removeThisLine() {
        parentContainer.remove(this);
        lineList.remove(this);
        parentContainer.revalidate();
        parentContainer.repaint();
    }
}


/**
 * 2) 메인 클래스: TemplateLine을 동적으로 추가·제거하고,
 *    "저장" 버튼 클릭 시 리스트를 순회하며 각 TemplateLine의 텍스트 값을 가져와 서버로 전송하거나 처리.
 */
public class QuizMakerFrame {
    private JFrame frame;
    private JPanel container;                // TemplateLine들을 담을 패널
    private JButton addButton, saveButton;   // 상단 버튼들
    private JLabel cntLbl;

    // TemplateLine 인스턴스를 모아둘 리스트
    private List<TemplateLine> templateLines = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizMakerFrame().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("TemplateLine 클래스 활용 예제");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // 상단에 “추가” 버튼과 “저장” 버튼 배치
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButton = new JButton("추가");
        saveButton = new JButton("저장");
        topPanel.add(addButton);
        topPanel.add(saveButton);
        frame.add(topPanel, BorderLayout.NORTH);

        // 동적으로 TemplateLine을 추가할 컨테이너 (세로로 쌓이도록 BoxLayout)
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(container,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        frame.add(scrollPane, BorderLayout.CENTER);

        // 이벤트 리스너 등록
        addButton.addActionListener(e -> addTemplateLine());
        saveButton.addActionListener(e -> saveAllData());

        frame.setVisible(true);
    }

    // “추가” 버튼 클릭 시: TemplateLine을 새로 생성해서 컨테이너와 리스트에 추가
    private void addTemplateLine() {
        // labelText는 필요에 따라 바꿀 수 있음 (“이름:”, “주소:” 등)
        TemplateLine line = new TemplateLine(container, templateLines, "이름:");
        templateLines.add(line);            // 리스트에도 저장
        container.add(line);                // UI에도 추가
        container.revalidate();
        container.repaint();
        
        // 추가된 라인의 개수를 카운트해서 레이블에 표시
        if (cntLbl == null) {
			cntLbl = new JLabel("추가된 항목 수: 1");
			frame.add(cntLbl, BorderLayout.SOUTH);
		} else {
			int count = templateLines.size();
			cntLbl.setText("추가된 항목 수: " + count);
		}	
		cntLbl.revalidate();
		cntLbl.repaint();
    }

    // “저장” 버튼 클릭 시: 리스트 순회하며 각 TemplateLine의 값 가져오기
    private void saveAllData() {
        if (templateLines.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "추가된 항목이 없습니다.");
            return;
        }

        // 예시: 콘솔에 찍어서 확인
        try {
        	System.out.println("----- 저장할 데이터 목록 -----");
            for (int i = 0; i < templateLines.size(); i++) {
            	try {
            		String tFNameValue = templateLines.get(i).gettFNameValue();
            		String tFAnswerValue = templateLines.get(i).gettFAnswerValue();
            		Path imgPath = Path.of(templateLines.get(i).getImgPath());
            		byte[] imgBytes = Files.readAllBytes(imgPath);
            		String mimeType = Files.probeContentType(imgPath);
            		
            		ApiResponse apiRes = HttpConnecter.instance.createQuiz("lol", tFNameValue, tFAnswerValue, mimeType, imgBytes);
            		if(apiRes == null) {
    					JOptionPane.showMessageDialog(container, "이미지 데이터가 없습니다.");
    					continue;
            		}
            		if(apiRes.isSuccess()) {
    					JOptionPane.showMessageDialog(container, "퀴즈가 성공적으로 저장되었습니다.");
    					continue;
    				} else {
    					switch(apiRes.getError().getCode()) {
    						case "CREATE_QUIZ_FAILED":
    							JOptionPane.showMessageDialog(container, "퀴즈 이름이 중복되었습니다.");
    							continue;
    						case "SERVER_ERROR":
    							JOptionPane.showMessageDialog(container, "서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
    							continue;
    					}
    				}
            	} catch (IOException e) {
            		throw new RuntimeException("이미지 파일을 읽는 중 오류 발생: " + e.getMessage());
            	}   
            }
        } catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "데이터 저장 중 오류가 발생했습니다: " + e.getMessage());
			return;
            
        }
        System.out.println("-----------------------------");
        JOptionPane.showMessageDialog(frame, "저장이 완료되었습니다.");
    }
}
