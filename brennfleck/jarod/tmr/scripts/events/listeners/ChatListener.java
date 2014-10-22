package brennfleck.jarod.tmr.scripts.events.listeners;

import java.util.Observable;
import java.util.Observer;

import brennfleck.jarod.tmr.scripts.events.TMRListener;
import brennfleck.jarod.tmr.scripts.events.chat.ChatEvent;

public interface ChatListener extends TMRListener {
	public void onChatEvent(ChatEvent e);
}