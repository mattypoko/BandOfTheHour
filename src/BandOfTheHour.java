import java.util.Scanner;

public class BandOfTheHour {
    private static Scanner scnr = new Scanner(System.in);

    // Constants for max rows and max positions in a row.
    static final int MAX_ROWS = 10, MAX_POSITIONS = 8;

    public static void main(String[] args) {

        // Variables for rows, positions in a row, and the user input.
        int rows, positions;
        char userInput;

        System.out.println("Welcome to the Band of the Hour");
        System.out.println("-------------------------------");

        // User enters number of rows, and a band array is created with that number of rows.
        System.out.print("Please enter number of rows: ");
        rows = scnr.nextInt();
        while ((rows < 1) || (rows > MAX_ROWS)) {
            System.out.print("ERROR: Out of range, try again: ");
            rows = scnr.nextInt();
        }
        double[][] bandArray = new double[rows][MAX_POSITIONS];

        // User enters number of positions in each row. As 3D arrays cannot vary in row lengths, each
        // row will always have the max number of positions (8), with unused positions reading -1.0
        for (int r = 0; r < rows; r++) {
            System.out.print("Please enter number of positions in row " + (char)(r + 'A') + ": ");
            positions = scnr.nextInt();
            while ((positions < 1) || (positions > MAX_POSITIONS)) {
                System.out.print("ERROR: Out of range, try again: ");
                positions = scnr.nextInt();
            }
            for (int p = 0; p < MAX_POSITIONS; p++) {
                if ((p + 1) <= positions) {
                    bandArray[r][p] = 0.0;
                } else {
                    bandArray[r][p] = -1.0;
                }
            }
        }

        do {
            // User selects whether they want to Add, Remove, Print or Exit.
            System.out.print("\n(A)dd, (R)emove, (P)rint, or (E)xit: ");
            userInput = Character.toUpperCase(scnr.next().charAt(0));
            while ((userInput != 'A') && (userInput != 'R') && (userInput != 'P') && (userInput != 'E')) {
                System.out.print("ERROR: Invalid option, try again: ");
                userInput = Character.toUpperCase(scnr.next().charAt(0));
            }

            // Variables for various user inputs used within the options.
            int userInputRow, userInputPosition;
            double userInputWeight;

            if (userInput == 'A') {

                // User enters a row's letter, which is converted into an array index.
                System.out.print("Please enter row letter: ");
                userInputRow = ((int) Character.toUpperCase(scnr.next().charAt(0))) - 'A';
                while ((userInputRow < 0) || (userInputRow >= bandArray.length)) {
                    System.out.print("ERROR: Invalid row, try again: ");
                    userInputRow = ((int) Character.toUpperCase(scnr.next().charAt(0))) - 'A';
                }
                // User enters an open position of that row (AKA any position with a weight of 0.0)
                System.out.print("Please enter position number: ");
                userInputPosition = scnr.nextInt() - 1;
                while ((userInputPosition < 0) || (userInputPosition >= bandArray[userInputRow].length) || (bandArray[userInputRow][userInputPosition] != 0.0)) {
                    System.out.print("ERROR: Invalid position, try again: ");
                    userInputPosition = scnr.nextInt() - 1;
                }
                // User enters a weight between 45kg and 200kg, and tests if a weight maximum was reached.
                System.out.print("Please enter weight (45.0 to 200.0): ");
                userInputWeight = scnr.nextDouble();
                double weightChange = 0 - bandArray[userInputRow][userInputPosition] + userInputWeight;
                while ((userInputWeight < 45.0) || (userInputWeight > 200.0) || testWeight(bandArray, userInputRow, weightChange)) {
                    System.out.print("ERROR: Invalid weight, try again: ");
                    userInputWeight = scnr.nextDouble();
                    weightChange = 0 - bandArray[userInputRow][userInputPosition] + userInputWeight;
                }
                // If everything checks out, the musician is added to the band array.
                bandArray[userInputRow][userInputPosition] = userInputWeight;
                System.out.print(">>>>> Musician added!\n");
            }
            else if (userInput == 'R') {

                // User enters a row's letter, which is converted into an array index.
                System.out.print("Please enter row letter: ");
                userInputRow = ((int) Character.toUpperCase(scnr.next().charAt(0))) - 'A';
                while ((userInputRow < 0) || (userInputRow >= bandArray.length)) {
                    System.out.print("ERROR: Invalid row, try again: ");
                    userInputRow = ((int) Character.toUpperCase(scnr.next().charAt(0))) - 'A';
                }
                // User enters a taken position of that row (AKA any position over a weight of 0.0)
                System.out.print("Please enter position number: ");
                userInputPosition = scnr.nextInt() - 1;
                while ((userInputPosition < 0) || (userInputPosition >= bandArray[userInputRow].length) || (bandArray[userInputRow][userInputPosition] <= 0.0)) {
                    System.out.print("ERROR: Invalid position, try again: ");
                    userInputPosition = scnr.nextInt() - 1;
                }
                // If everything checks out, the musician is removed from the band array.
                bandArray[userInputRow][userInputPosition] = 0.0;
                System.out.print(">>>>> Musician removed!\n");
            }
            else if (userInput == 'P') {

                // Prints the band array's data to the user, including each row's total and average weight.
                System.out.println(" ");
                for (int i = 0; i < rows; i++) {
                    System.out.print((char)(i + 'A') + ":   ");
                    for (int j = 0; j < MAX_POSITIONS; j++) {
                        if (bandArray[i][j] >= 0.0) {
                            System.out.print(bandArray[i][j] + "    ");
                        }
                    } System.out.println("[" + getTotalRowWeight(bandArray, i) + ", " + getAverageRowWeight(bandArray, i) + "]");
                }
            }
        } while (userInput != 'E');

        // Breaks from the loop of selecting options, and exits the program.
        System.out.print(">>>>> Exiting...\n");
    }

    // Calculates the total weight of all positions in a row, then returns that total.
    static double getTotalRowWeight(double[][] inputArray, int inputRow) {
        double totalRowWeight = 0.0;
        for (int t = 0; t < inputArray[inputRow].length; t++) {
            if (inputArray[inputRow][t] >= 0.0) {
                totalRowWeight += inputArray[inputRow][t];
            }
        } return totalRowWeight;
    }
    // Calculates the average weight of all positions in a row, then returns that average.
    static double getAverageRowWeight(double[][] inputArray, int inputRow) {
        double totalRowWeight = 0.0;
        int totalPositions = 0;
        for (int t = 0; t < inputArray[inputRow].length; t++) {
            if (inputArray[inputRow][t] >= 0.0) {
                totalRowWeight += inputArray[inputRow][t];
                totalPositions += 1;
            }
        } return (totalRowWeight / totalPositions);
    }
    // Calculates a row's new total weight, and returns whether it goes over the weight maximum.
    static boolean testWeight(double[][] inputArray, int inputRow, double changeInWeight) {
        double totalRowWeight = 0.0;
        int totalPositions = 0;
        for (int t = 0; t < inputArray[inputRow].length; t++) {
            if (inputArray[inputRow][t] >= 0.0) {
                totalRowWeight += inputArray[inputRow][t];
                totalPositions += 1;
            }
        } return ((totalRowWeight + changeInWeight) > (totalPositions * 100.0));
    }
}