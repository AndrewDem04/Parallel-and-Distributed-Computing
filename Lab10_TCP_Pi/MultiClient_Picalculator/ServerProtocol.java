import java.net.*;
import java.io.*;

public class ServerProtocol {

	private CalcP pCalculator;

	public ServerProtocol (CalcP PCalculator) {
		pCalculator = PCalculator;
    }

	public String processRequest(String theInput) {
		return String.valueOf(pCalculator.CalcP(Integer.parseInt(theInput)));
	}
}
