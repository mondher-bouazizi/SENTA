package commons.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {


	public static void logSarcasmDetectionParameters(int minLength, int maxLength, double alpha, double gamma, int minOccurrence, double[] coef) {
		String output = "Output\\SarcasmDetection\\parameters.txt";

		BufferedWriter BW = null;

		try {
			File outputFile = new File(output);
			if (!outputFile.exists())
				outputFile.createNewFile();
			BW = new BufferedWriter(new FileWriter(outputFile));

			BW.write("Minimum length:\t" + minLength);
			BW.newLine();
			BW.write("Maximum length:\t" + maxLength);
			BW.newLine();

			BW.write("alpha:\t" + alpha);
			BW.newLine();
			BW.write("gamma:\t" + gamma);
			BW.newLine();

			BW.write("Min Occurences:\t" + minOccurrence);
			BW.newLine();

			BW.write("coefficients:\t[");
			for (int i = 0; i < coef.length; i++) {
				if (i != coef.length - 1)
					BW.write(coef[i] + ", ");
				else
					BW.write("" + coef[i]);
			}
			BW.write("]");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (BW != null) {
				try {
					BW.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		////////////////////////////////////
	}

}
