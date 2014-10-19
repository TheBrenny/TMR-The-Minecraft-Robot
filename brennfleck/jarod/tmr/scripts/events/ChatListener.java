package brennfleck.jarod.tmr.scripts.events;

import java.util.Observable;
import java.util.Observer;

public interface ChatListener extends TMRListener {
	public void onChatEvent(ChatEvent e);
}