package chucknorris;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final String ENCODED_FIRST_BLOCK_ONE = "0";
        final String ENCODED_FIRST_BLOCK_ZERO = "00";
        final String ENCODED_SECOND_BLOCK = "0";

        String option;
        do {
            option = getUserInput("Please input operation (encode/decode/exit):");

            switch (option) {
                case "encode":
                    encode(ENCODED_FIRST_BLOCK_ZERO, ENCODED_FIRST_BLOCK_ONE, ENCODED_SECOND_BLOCK);
                    break;
                case "decode":
                    decode(ENCODED_FIRST_BLOCK_ZERO, ENCODED_FIRST_BLOCK_ONE, ENCODED_SECOND_BLOCK);
                    break;
                case "exit":
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("There is no '" + option + "' operation");
                    break;
            }
        } while (!option.equals("exit"));
    }

    public static void encode(String ENCODED_FIRST_BLOCK_ZERO, String ENCODED_FIRST_BLOCK_ONE, String ENCODED_SECOND_BLOCK) {
        String userInput = getUserInput("Input string:");
        String inputBinary = convertToAsciiBinary(userInput);

        StringBuilder encodedString = new StringBuilder();

        int i = 0;
        char currentBit;
        while (i < inputBinary.length()) {
            encodedString.append(inputBinary.charAt(i) == '1' ? ENCODED_FIRST_BLOCK_ONE : ENCODED_FIRST_BLOCK_ZERO);
            encodedString.append(" ");

            currentBit = inputBinary.charAt(i);
            while (inputBinary.charAt(i) == currentBit) {
                encodedString.append(ENCODED_SECOND_BLOCK);
                i++;
                if (i == inputBinary.length()) break;
            }

            if (i < inputBinary.length()) encodedString.append(" ");
        }

        System.out.println("Encoded string:");
        System.out.println(encodedString);
    }

    public static void decode(String ENCODED_FIRST_BLOCK_ZERO, String ENCODED_FIRST_BLOCK_ONE, String ENCODED_SECOND_BLOCK) {
        String userInput = getUserInput("Input encoded string:");
        String[] inputEncoded = userInput.split(" ");

        if (!isNumberOfEncodedBlocksEven(inputEncoded) ||
                !(isEncodedFirstBlockValid(inputEncoded, ENCODED_FIRST_BLOCK_ZERO, ENCODED_FIRST_BLOCK_ONE) ||
                        !isEncodedSecondBlockValid(inputEncoded, ENCODED_SECOND_BLOCK))) {
            System.out.println("Encoded string is not valid.");
        } else {
            StringBuilder binaryString = new StringBuilder();
            for (int i = 0; i < inputEncoded.length; i += 2) {
                binaryString.append((inputEncoded[i].equals(ENCODED_FIRST_BLOCK_ZERO) ? "0" : "1").repeat(inputEncoded[i + 1].length()));
            }

            if (!isBinaryStringMultipleOfSeven(binaryString.toString())) {
                System.out.println("Encoded string is not valid.");
            } else {
                String[] binaryArray = binaryString.toString().split("(?<=\\G.{7})");
                String decodedString = convertToAsciiChar(binaryArray);

                System.out.println("Decoded string:");
                System.out.println(decodedString);
            }
        }
    }

    public static String getUserInput(String userInputMessage) {
        System.out.println();
        System.out.println(userInputMessage);
        return new Scanner(System.in).nextLine();
    }

    public static String convertToAsciiBinary(char inputChar) {
        return String.format("%7s", Integer.toBinaryString(inputChar)).replace(" ","0");
    }

    public static String convertToAsciiBinary(String inputString) {
        StringBuilder binaryString = new StringBuilder();
        char[] charArray = inputString.toCharArray();
        for (char character : charArray) {
            binaryString.append(convertToAsciiBinary(character));
        }
        return binaryString.toString();
    }

    public static char convertToAsciiChar(String binary) {
        return (char) Integer.parseInt(binary, 2);
    }

    public static String convertToAsciiChar(String[] binaryArray) {
        StringBuilder decodedString = new StringBuilder();
        for (String binary : binaryArray) {
            decodedString.append(convertToAsciiChar(binary));
        }

        return decodedString.toString();
    }

    public static boolean isNumberOfEncodedBlocksEven(String[] inputEncoded) {
        return inputEncoded.length % 2 == 0;
    }

    public static boolean isEncodedFirstBlockValid(String[] inputEncoded, String ENCODED_FIRST_BLOCK_ZERO, String ENCODED_FIRST_BLOCK_ONE) {
        for (int i = 0; i < inputEncoded.length; i += 2) {
            if (!(inputEncoded[i].equals(ENCODED_FIRST_BLOCK_ZERO) || inputEncoded[i].equals(ENCODED_FIRST_BLOCK_ONE))) return false;
        }
        return true;
    }

    public static boolean isEncodedSecondBlockValid(String[] inputEncoded, String ENCODED_SECOND_BLOCK) {
        for (int i = 1; i < inputEncoded.length; i += 2) {
            if (!inputEncoded[i].matches("^" + ENCODED_SECOND_BLOCK + "+$")) return false;
        }
        return true;
    }

    public static boolean isBinaryStringMultipleOfSeven(String binaryString) {
        return binaryString.length() % 7 == 0;
    }
}