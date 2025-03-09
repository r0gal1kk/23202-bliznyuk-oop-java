import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the 'Bulls and Cows' game!");
        System.out.println("Enter the number of attempts: ");
        Scanner scanner = new Scanner(System.in);
        int maxAttempts = 0;
        while (maxAttempts <= 0) {
            try {
                maxAttempts = Integer.parseInt(scanner.nextLine());
                if (maxAttempts < 0) {
                    System.out.println("Please enter a positive number!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a positive number!");
            }
        }
        Game game = new Game(maxAttempts);
        game.run();
    }
}
