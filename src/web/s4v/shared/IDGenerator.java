package web.s4v.shared;

import java.util.Random;

/**
 * Generates Random ID
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since May 2023
 */
public class IDGenerator {
    private static final int ID_LENGTH = 10; // Desired length of the generated ID

    /**
     * Generates Random ID
     * @return ID
     */
    public static String generateID() {
        long timestamp = System.currentTimeMillis(); // Get the current timestamp
        Random random = new Random(); // Create a random number generator

        // Generate a random number with the desired length
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            sb.append(random.nextInt(10)); // Append a random digit (0-9)
        }

        return timestamp + sb.toString(); // Combine timestamp and random number to create the ID
    }
}