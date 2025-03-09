import java.util.Scanner;

public class InputHandler {
    private Scanner scanner;
    public InputHandler() {
        try {
            this.scanner = new Scanner(System.in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getInput() {
        return scanner.nextLine();
    }

    public boolean checkInput(String input) {
        return input.matches("\\d{4}");
    }
}
