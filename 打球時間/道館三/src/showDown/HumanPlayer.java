package showDown;

public class HumanPlayer extends Player {
    public HumanPlayer() {
        super();
    }

    @Override
    public void nameSelf() {
        CLI cli = new CLI();
        this.name = cli.inputName();
    }

    @Override
    public Card takeTurn() {
        CLI cli = new CLI();
        cli.displayMessage("\n" + name + "'s turn:");
        int cardIndex = cli.selectCard(this.hand);
        
        return this.hand.removeCard(cardIndex);
    }
}
