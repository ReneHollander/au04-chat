package at.hollanderpoecher.chat;

import at.hollanderpoecher.chat.gui.ChatWindow;

public class Chat {

	public static void main(String[] args) {
		ChatWindow.launch(ChatWindow.class, args);
		ChatWindow chatWindow = ChatWindow.getInstance();
	}

}
