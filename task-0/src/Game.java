public class Game {
    private boolean gameActive;
    private InputHandler inputHandler;
    private Player player;
    private NumberGenerator numberGenerator;

    public Game(Player player) {
        this.player = player;
        this.gameActive = false;
        this.numberGenerator = new NumberGenerator();
        this.inputHandler = new InputHandler();
    }

    public void run() {
        gameActive = true;
        while (gameActive) {
            String guess = inputHandler.getInput();
        }
    }
}
