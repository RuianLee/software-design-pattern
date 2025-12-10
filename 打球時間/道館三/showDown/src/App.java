public class App {
    public static void main(String[] args) throws Exception {
        CLI cli = new CLI();

        Showdown game = new Showdown();

        game.addPlayer(new AI());
        game.addPlayer(new AI());
        game.addPlayer(new AI());
        game.addPlayer(new AI());

        game.start();

        cli.close();
    }
}
