import java.net.*;
import java.io.*;

public class ServerProtocol {

	public String processRequest(String theInput) {
		// System.out.println("Received message from client: " + theInput);
		// String theOutput = theInput.toLowerCase();
		// System.out.println("Send message to client: " + theOutput);
		// return theOutput;

		System.out.println("Received message from client: " + theInput);
        
        String[] parts = theInput.split("\\|");
        String command = parts[0].toUpperCase();
        String message;
        int offset = 0;

		if (command.equals("ENCODE") || command.equals("DECODE")) {
            offset = Integer.parseInt(parts[1]);
            message = parts[2];
        } else {
            message = parts[1];
        }

		String result = "";

        switch (command) {
            case "LOWER":
                result = toLower(message);
                break;
            case "UPPER":
                result = toUpper(message);
                break;
            case "ENCODE":
                result = caesarEncode(message, offset);
                break;
            case "DECODE":
                result = caesarDecode(message, offset);
                break;
            default:
                result = "ERROR: Unknown command";
        }

		System.out.println("Send message to client: " + result);
        return result;
	}

	private String toLower(String message) {
        return message.toLowerCase();
    }

    private String toUpper(String message) {
        return message.toUpperCase();
    }

    private String caesarEncode(String message, int offset) {
        StringBuilder result = new StringBuilder();
        for (char character : message.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                int newChar = (character - base + offset) % 26 + base;
                result.append((char) newChar);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    private String caesarDecode(String message, int offset) {
        return caesarEncode(message, 26 - (offset % 26));
    }
}