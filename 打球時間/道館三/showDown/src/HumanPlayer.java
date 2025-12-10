public class HumanPlayer extends Player {

    public HumanPlayer(){
        super();
    }

    @Override
    public Card takeTurn() {
        CLI cli = new CLI();

        cli.showHand(this.hand);
        int cardIndex = cli.chooseCard();
        
        return hand.removeCard(cardIndex);
    }
}
