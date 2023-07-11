package ca.wajahatali.commons.tools.random.generators;

import java.io.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * The UniqueNumberGenerator class generates unique numbers and saves them to a file.
 */
public class UniqueNumberGenerator {
    private Set<Integer> generatedNumbers; // Set to store generated numbers
    private Random random; // Random number generator
    private File numbersFile; // File to store generated numbers

    /**
     * Constructs a UniqueNumberGenerator object.
     *
     * @param filePath the path to the file where generated numbers are stored or will be stored
     */
    public UniqueNumberGenerator(String filePath) {
        generatedNumbers = new HashSet<>(); // Initialize the set of generated numbers
        random = new Random(); // Initialize the random number generator
        numbersFile = new File(filePath); // Initialize the file object

        loadGeneratedNumbers(); // Load previously generated numbers from the file
    }

    /**
     * Generates a unique number.
     *
     * @return a unique number
     */
    public int generateUniqueNumber() {
        int number;
        do {
            number = random.nextInt(90000000) + 10000000; // Generate a random number
        } while (!isUnique(number)); // Repeat until a unique number is generated

        generatedNumbers.add(number); // Add the generated number to the set
        saveGeneratedNumbers(); // Save the updated set to the file

        return number;
    }

    /**
     * Checks if a number is unique.
     *
     * @param number the number to check
     * @return true if the number is unique, false otherwise
     */
    private boolean isUnique(int number) {
        return !generatedNumbers.contains(number); // Check if the number is present in the set
    }

    /**
     * Loads previously generated numbers from the file.
     */
    private void loadGeneratedNumbers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(numbersFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int generatedNumber = Integer.parseInt(line); // Parse the number from the file
                generatedNumbers.add(generatedNumber); // Add the number to the set
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading the numbers file: " + e.getMessage());
        }
    }

    /**
     * Saves the generated numbers to the file, overwriting the existing content.
     */
    private void saveGeneratedNumbers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(numbersFile))) {
            for (int generatedNumber : generatedNumbers) {
                writer.write(String.valueOf(generatedNumber)); // Write the number to the file
                writer.newLine(); // Write a new line
            }
        } catch (IOException e) {
            System.out.println("Error occurred while writing to the numbers file: " + e.getMessage());
        }
    }

    /**
     * The main method of the program.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String filePath = "generated_numbers.txt"; // File path for storing generated numbers
        UniqueNumberGenerator numberGenerator = new UniqueNumberGenerator(filePath); // Create a number generator

        int uniqueNumber = numberGenerator.generateUniqueNumber(); // Generate a unique number
        System.out.println("Generated unique number: " + uniqueNumber); // Print the generated number
    }
}
