package constants;

public class Constants {
	
	public enum EConfigurations {
		miridamgiFilePostfix("M"),
		singcheongFilePostfix("S");
		
		private String text;
		private EConfigurations(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
		public int getInt() {
			return Integer.parseInt(text);
		}
	}
	
	public enum EPResultPanel {
		gangjwaNo("강좌번호"),
		gangjwaName("강좌명");
		
		private String text;
		private EPResultPanel(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
		public int getInt() {
			return Integer.parseInt(text);
		}
	}
	
	public enum EPHeaderPanel {
		greetings("안녕하세요");
		
		private String text;
		private EPHeaderPanel(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}

	public enum EPGangjwaSelectionPanel {
		gangjwaNo("강좌번호"),
		gangjwaName("강좌명"),
		damdangGyosu("담당교수"),
		hakjeom("학점"),
		time("시간");
		
		private String text;
		private EPGangjwaSelectionPanel(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
		public int getInt() {
			return Integer.parseInt(text);
		}		
	}
	
	public enum EPHakgwaSelectionPanel {
		rootFileName("root"),
		campus("캠퍼스"),
		college("대학"),
		hakgwa("학과");
		
		private String text;
		private EPHakgwaSelectionPanel(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
		public int getInt() {
			return Integer.parseInt(text);
		}		
	}
	
	public enum ELoginDialog {
		width("300"),
		height("200"),
		nameLabel(" 사용자ID   "),
		sizeNameText("10"),
		passwordLabel("비밀번호"),
		sizePasswordText("10"),
		okButtonLabel("ok"),
		cancelButtonLabel("cancel"),
		loginFailed("잘못 입력하였습니다.");
		
		private String text;
		private ELoginDialog(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
		public int getInt() {
			return Integer.parseInt(text);
		}
	}

	public enum EMainFrame {
		width("1000"),
		height("600");
		
		private String text;
		private EMainFrame(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
		public int getInt() {
			return Integer.parseInt(text);
		}
	}
	
	public enum EMenuBar {
		eFile("파일"),
		eEdit("편집");
		
		String text;
		EMenuBar(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
	
	public enum EFileMenu {
		eNew("새로만들기"),
		eOpen("열기"),
		eSave("저장"),
		eSaveAs("다른이름으로"),
		ePrint("프린트"),
		eExit("종료");
		
		String text;
		EFileMenu(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
	
	public enum EEditMenu {
		eCopy("복사"),
		eCut("잘라내기"),
		ePaste("븦여넣기"),
		eDelete("삭제");
		
		String text;
		EEditMenu(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
}
