import java.net.*;
import java.io.*;

public class ServerProtocol {

	private CalcP pCalculator;
	private PiDatabase pDatabase;

	public ServerProtocol (CalcP PCalculator , PiDatabase PDatabase) {
		pCalculator = PCalculator;
		pDatabase = PDatabase;
    }

	public String processRequest(String theInput) {
		int inputNumber = Integer.parseInt(theInput);
		if(pDatabase.contains(inputNumber)){
			return String.valueOf(pDatabase.get(inputNumber));
		}
		double output = pCalculator.CalcP(inputNumber);
		pDatabase.put(inputNumber, output);
		return String.valueOf(output);
	}
}
