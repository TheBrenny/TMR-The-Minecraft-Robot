package brennfleck.jarod.tmr.gui;

public enum GuiTmrButton {
	NONE("TMR Error"),
	SETTINGS("TMR Settings"),
	RECOLLECT("Recollect Scripts"),
	SCRIPTS("TMR Scripts"),
	SCRIPT_START("Start Script"),
	SCRIPT_PAUSE("Pause Script"),
	SCRIPT_UNPAUSE("Unpause Script"),
	SCRIPT_STOP("Stop Script");
	
	public final String title;
	
	GuiTmrButton(String title) {
		this.title = title;
	}
	
	public final String getTitle() {
		return title;
	}
}