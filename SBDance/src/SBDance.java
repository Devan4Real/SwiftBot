import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.File;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.imageio.ImageIO;

import swiftbot.Button;
import swiftbot.ImageSize;
import swiftbot.SwiftBotAPI;

public class SBDance {
	static SwiftBotAPI swiftBot;
	static ArrayList<String> hexValues = new ArrayList<>();
	static String hexInput = "";
	static String binaryResult;
	static int decimalResult;
	static int octalEquivalent;
	static int speed;
	static int pictureCount;
	static boolean buttonEnabled = false;
	AtomicBoolean continueLoop = new AtomicBoolean(true);

	public static int performDance(String binaryResult, String hexInput, int speed) {
		int picturecount = 0;
		int moveDuration = (hexInput.length() == 1) ? 1000 : 500;

		for (int i = binaryResult.length() - 1; i >= 0; i--) {
			if (binaryResult.charAt(i) == '1') {
				try {
					swiftBot.move(speed, speed, moveDuration);
					BufferedImage img = swiftBot.takeStill(ImageSize.SQUARE_1080x1080);
					ImageIO.write(img, "jpg", new File("/home/pi/Documents/testImage"+i+".jpg"));
					picturecount++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					swiftBot.move(100,-100,1429);  // Spin (left wheel forward, right wheel backward) for 1 second
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
		pictureCount = picturecount + pictureCount;
		swiftBot.disableButton(Button.Y);
		swiftBot.disableButton(Button.X);
		swiftBot.disableAllButtons();
		swiftBot.disableUnderlights();
		System.out.println("\n \u001b[32mDance Complete!\u001b[0m, Press button \u001b[34m(y)\u001b[0m to enter another hex number or Press any other button to exit.");
		pressButton2();
		while (buttonEnabled = true) {

		}
		buttonEnabled = false;
		return pictureCount;
	}

	private static void playMusic() {
		swiftBot.disableButton(Button.Y);
		swiftBot.disableButton(Button.X);
		swiftBot.disableButton(Button.A);
		swiftBot.disableButton(Button.B);

		System.out.println("\n Press \u001b[34m(y)\u001b[0m button if you want to play music while Swiftbot dances or Press \u001b[36m(X)\u001b[0m button if you do not want music.");
        MusicButtons();
		while (buttonEnabled = true) {
		
		}
		buttonEnabled = false;
		swiftBot.disableButton(Button.Y);
		swiftBot.disableButton(Button.X);
		performDance(binaryResult, hexInput, speed);
	}

	private static void MusicButtons() {
		swiftBot.enableButton(Button.Y, () -> {
			System.out.println("\n Playing music....");
		try {
			Music();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		performDance(binaryResult, hexInput, speed);
		});
		swiftBot.enableButton(Button.X, () -> {
		System.out.println("\n No music....");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		performDance(binaryResult, hexInput, speed);
		});
		buttonEnabled = true;
	}

	private static void Music() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		 File file = new File("Test.wav.wav");
    	 AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
		 Clip clip = AudioSystem.getClip();
		 clip.open(audioInput);
		 clip.start();
         Thread.sleep(30000);
	}

	public static void ConvertAndPrint(String hexInput) {
		swiftBot.disableButton(Button.Y);
		swiftBot.disableButton(Button.X);
		swiftBot.disableButton(Button.A);
		swiftBot.disableButton(Button.B);
		if (hexInput.length() == 1 || hexInput.length() == 2) {
			System.out.println("\n\u001b[31mInvalid input\u001b[0m. Press \u001b[34m(y)\u001b[0m to try again");
			pressButton1();
		} else {
			hexValues.add(hexInput);
		}
		binaryResult = hexToBinary(hexInput);
		decimalResult = hexToDecimal(binaryResult);
		octalEquivalent = hexToOctal(decimalResult);
		int redValue = decimalResult;
		int greenValue = decimalResult % 80 * 3;
		int blueValue = Math.max(redValue, greenValue);
		SetRGB(redValue, greenValue, greenValue);
		speed = determineSpeed(octalEquivalent);
		System.out.println("\n \u001b[32mScan successful!\u001b[0m");
		System.out.println("\nHexadecimal value given: \u001b[35m" + hexInput + "\u001b[0m\n" +
                "Octal equivalent: \u001b[33m" + octalEquivalent + "\u001b[0m\n" +
                "Decimal equivalent: \u001b[33m" + decimalResult + "\u001b[0m\n" +
                "Binary equivalent: \u001b[33m" + binaryResult + "\u001b[0m\n" +
                "Speed: \u001b[36m" + speed + "\u001b[0m\n" +
                "LED color (red \u001b[31m" + redValue + "\u001b[0m, green \u001b[32m" + greenValue + "\u001b[0m, blue \u001b[34m" + blueValue + "\u001b[0m)");
		playMusic();
		
	}

	public static String ScanQRcode() {
		hexInput ="";
		for (int i = 0; i < 25; ++i) {
			try {
				BufferedImage img = swiftBot.getQRImage();
				String decodedText = swiftBot.decodeQRImage(img);

				if (!decodedText.isEmpty()) {
					hexInput = decodedText;
					ConvertAndPrint(hexInput);
					break;
				} else {
					System.out.println("Please bring the QR code closer...");
				}
			} catch (Exception e) {
				System.out.println("Error encountered = " + e.getMessage());
			}
		}
		System.out.println("\n\u001b[31mScan Failed\u001b[0m, please press button \u001b[34m(y)\u001b[0m to try again...\n");
		//swiftBot.disableButton(Button.Y);
		return hexInput;
	}

	public static void pressButton1() {
		swiftBot.enableButton(Button.Y, SBDance::ScanQRcode);


		swiftBot.enableButton(Button.X, () -> {
			System.out.println("\u001b[31mError\u001b[0m: Invalid input, press button \u001b[34m(y)\u001b[0m to start.");
		});
		swiftBot.enableButton(Button.A, () -> {
			System.out.println("\u001b[31mError\u001b[0m: Invalid input, press button \u001b[34m(y)\u001b[0m to start.");
		});
		swiftBot.enableButton(Button.B, () -> {
			System.out.println("\u001b[31mError\u001b[0m: Invalid input, press button \u001b[34m(y)\u001b[0m to start.");
		});
		
		buttonEnabled = true;
	}
	// MAIN

	public static void main(String[] args) throws Exception {
		
		System.out.println("************************************\n"
                + "*  _____         _                 *\r\n"
                + "* |_   _|_ _ ___| | __             *\r\n"
                + "*   | |/ _` / __| |/ /             *\r\n"
                + "*   | | (_| \\__ \\   <              *\r\n"
                + "*  _|_|\\__,_|___/_|\\_\\ ____ _____  *\r\n"
                + "* |  _ \\  / \\  | \\ | |/ ___| ____| *\r\n"
                + "* | | | |/ _ \\ |  \\| | |   |  _|   *\r\n"
                + "* | |_| / ___ \\| |\\  | |___| |___  *\r\n"
                + "* |____/_/   \\_\\_| \\_|\\____|_____| *\n"
                + "************************************\n");
		swiftBot = new SwiftBotAPI();
		new ArrayList<>();

		System.out.println("Welcome to Swiftbot Dance!\n"
		        + "\n"
		        + "Press the button \u001b[34m(y)\u001b[0m to start.\n"
		        + "");

		pressButton1();
		while (buttonEnabled = true) {
			
		}
		buttonEnabled = false; 
		
		
	}

	public static void pressButton2() {
		swiftBot.enableButton(Button.Y, SBDance::ScanQRcode);

		swiftBot.enableButton(Button.X, () -> {
			try {
				Terminate();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		swiftBot.enableButton(Button.A, () -> {
			try {
				Terminate();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		swiftBot.enableButton(Button.B, () -> {
			try {
				Terminate();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		buttonEnabled = true;
	}

	public static void Terminate() throws IOException {
		System.out.println("\n Sorting and writing to file...");
		Collections.sort(hexValues);
		System.out.println("Sorted Hex Values: \u001b[32m" + hexValues + "\u001b[0m");
		System.out.println("Number of images taken: \u001b[31m" + pictureCount + "\u001b[0m");
		System.out.println("Folder location: \u001b[35m/home/pi/Documents\u001b[0m");
		writeToFile(hexValues);

		swiftBot.disableAllButtons();
		
		System.exit(0);
	}

	public static void SetRGB(int redValue, int blueValue, int greenValue) {
		int[] colourToLightUp = { redValue, greenValue, blueValue };
		try {
			swiftBot.fillUnderlights(colourToLightUp);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public static boolean isValidHexInput(String hexInput) {
		return hexInput.length() == 1 || hexInput.length() == 2;
	}

	public static int hexToOctal(int decimalNumber) {
		if (decimalNumber == 0) {
			return 0;
		}

		StringBuilder octalBuilder = new StringBuilder();

		while (decimalNumber > 0) {
			int remainder = decimalNumber % 8;
			octalBuilder.append(remainder); // Append remainder to the end
			decimalNumber /= 8; // Update the quotient for the next iteration
		}

		// Reverse the StringBuilder to get the correct octal number
		return Integer.parseInt(octalBuilder.reverse().toString());
	}

	public static int hexToDecimal(String binaryResult) {
		int decimalResult = 0;

		for (int i = binaryResult.length() - 1; i >= 0; i--) {
			char bit = binaryResult.charAt(i);
			int bitValue = (bit == '1') ? 1 : 0;
			decimalResult += bitValue * Math.pow(2, binaryResult.length() - 1 - i);
		}

		return decimalResult;
	}

	public static String hexToBinary(String hex) {
		// Defines a dictionary to map each hex digit to its 4-bit binary representation
		Map<Character, String> hexToBinaryMap = new HashMap<>();
		hexToBinaryMap.put('0', "0000");
		hexToBinaryMap.put('1', "0001");
		hexToBinaryMap.put('2', "0010");
		hexToBinaryMap.put('3', "0011");
		hexToBinaryMap.put('4', "0100");
		hexToBinaryMap.put('5', "0101");
		hexToBinaryMap.put('6', "0110");
		hexToBinaryMap.put('7', "0111");
		hexToBinaryMap.put('8', "1000");
		hexToBinaryMap.put('9', "1001");
		hexToBinaryMap.put('A', "1010");
		hexToBinaryMap.put('B', "1011");
		hexToBinaryMap.put('C', "1100");
		hexToBinaryMap.put('D', "1101");
		hexToBinaryMap.put('E', "1110");
		hexToBinaryMap.put('F', "1111");

		StringBuilder binaryStringBuilder = new StringBuilder();

		for (int i = 0; i < hex.length(); i++) {
			char hexDigit = hex.charAt(i);
			String binaryResult = hexToBinaryMap.get(hexDigit);
			if (binaryResult != null) {
				binaryStringBuilder.append(binaryResult);
			}
		}

		return binaryStringBuilder.toString();
	}

	public static int determineSpeed(int octal) {
		int speed = octal + 50;
		return Math.min(speed, 100); // returns smaller one
	}

	private static void writeToFile(ArrayList<String> hexValues) throws IOException {
		
		FileWriter writehandle = new FileWriter("c:\\temp\\file.txt");
	    BufferedWriter bw = new BufferedWriter(writehandle);

	    // Write each hex value to the file
	    for (String hexValue : hexValues) {
	        bw.write(hexValue);
	    }

	    // Write the picture count to the file
	    bw.write("Number of images taken: " + pictureCount);

	    bw.close();
	    writehandle.close();
	    System.out.println("\n \u001b[31mProgram Terminated!\u001b[0m");
	    System.out.println("See you soon....\n");
	    System.out.println("⢿⣿⣿⣿⣭⠹⠛⠛⠛⢿⣿⣿⣿⣿⡿⣿⠷⠶⠿⢻⣿⣛⣦⣙⠻⣿\r\n"
	    		+ "⣿⣿⢿⣿⠏⠀⠀⡀⠀⠈⣿⢛⣽⣜⠯⣽⠀⠀⠀⠀⠙⢿⣷⣻⡀⢿\r\n"
	    		+ "⠐⠛⢿⣾⣖⣤⡀⠀⢀⡰⠿⢷⣶⣿⡇⠻⣖⣒⣒⣶⣿⣿⡟⢙⣶⣮\r\n"
	    		+ "⣤⠀⠀⠛⠻⠗⠿⠿⣯⡆⣿⣛⣿⡿⠿⠮⡶⠼⠟⠙⠊⠁⠀⠸⢣⣿\r\n"
	    		+ "⣿⣷⡀⠀⠀⠀⠀⠠⠭⣍⡉⢩⣥⡤⠥⣤⡶⣒⠀⠀⠀⠀⠀⢰⣿⣿\r\n"
	    		+ "⣿⣿⡽⡄⠀⠀⠀⢿⣿⣆⣿⣧⢡⣾⣿⡇⣾⣿⡇⠀⠀⠀⠀⣿⡇⠃\r\n"
	    		+ "⣿⣿⣷⣻⣆⢄⠀⠈⠉⠉⠛⠛⠘⠛⠛⠛⠙⠛⠁⠀⠀⠀⠀⣿⡇⢸\r\n"
	    		+ "⢞⣿⣿⣷⣝⣷⣝⠦⡀⠀⠀⠀⠀⠀⠀⠀⡀⢀⠀⠀⠀⠀⠀⠛⣿⠈\r\n"
	    		+ "⣦⡑⠛⣟⢿⡿⣿⣷⣝⢧⡀⠀⠀⣶⣸⡇⣿⢸⣧⠀⠀⠀⠀⢸⡿⡆\r\n"
	    		+ "⣿⣿⣷⣮⣭⣍⡛⠻⢿⣷⠿⣶⣶⣬⣬⣁⣉⣀⣀⣁⡤⢴⣺⣾⣽⡇");
	}
}
