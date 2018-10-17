package backend.processor;

import commons.constants.Constants;

public class CharProcessing {

	/**
	 * Check whether a char is a punctuation or not
	 * @param charToCheck
	 * @return
	 */
	public static boolean checkPunctuation(char charToCheck) {
		if (Constants.punctuationMarks.contains(charToCheck + "")) {
			return true;
		} else {
			
			return false;
		}
		
	}

	/**
	 * Calculate the distance between 2 Char in the "QWERTY" keyboard
	 * @param letter1
	 * @param letter2
	 * @return
	 */
	public double enKeyboarDistance(char letter1, char letter2) {

		double distance;

		String L1 = "" + letter1;
		String L2 = "" + letter2;
		//System.out.println(L1 + "\t" + L2);

		String keyboard[][] = {
				{"q", "w", "e", "r", "t", "y", "u", "i", "o", "p"},
				{"a", "s", "d", "f", "g", "h", "j", "k", "l", "#"},
				{"z", "x", "c", "v", "b", "n", "m", "#", "#", "#"}
		};

		int coord1[] = {-1000, -1000};
		int coord2[] = {-1000, -1000};

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 10; j++) {
				if (L1.toLowerCase().equals(keyboard[i][j])) {
					//System.out.println(i + "\t" + j);
					coord1[0] = j;
					coord1[1] = i;
				}
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 10; j++) {
				if (L2.toLowerCase().equals(keyboard[i][j])) {
					//System.out.println(i + "\t" + j);
					coord2[0] = j;
					coord2[1] = i;
				}
			}
		}

		distance = Math.sqrt((coord1[0] - coord2[0]) * (coord1[0] - coord2[0]) + (coord1[1] - coord2[1]) * (coord1[1] - coord2[1]));

		return distance;

	}

}
