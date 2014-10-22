package brennfleck.jarod.tmr.scripts.events.chat;

/**
 * A Chat Event is an event that occurs whenever a chat message is sent.
 * Generally whenever something is dropped into the chat log of the client, a
 * chat even will be created.
 * 
 * @author Jarod Brennfleck
 */
public class ChatEvent {
	private String sender;
	private String message;
	
	/**
	 * Constructs a new chat event.
	 */
	public ChatEvent(String sender, String message) {
		this.sender = sender;
		this.message = message;
	}
	
	/**
	 * Returns the sender who caused this chat event to occur.
	 */
	public String getSender() {
		return this.sender;
	}
	
	/**
	 * Returns the message of this chat event.
	 */
	public String getMessage() {
		return this.message;
	}
}