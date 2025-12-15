package uno;

public class HumanPlayer extends Player {

    public HumanPlayer() {
        super();
    }

    @Override
    public void nameSelf() {
        CLI cli = new CLI();
        this.name = cli.inputName();
        System.out.println("I am " + name + " (Human Player)");
    }

    @Override
    public Card takeTurn(Card topCard) {
        CLI cli = new CLI();
        cli.displayMessage("\n" + name + "'s turn:");

        int cardIndex = cli.selectCard(hand, topCard);

        if (cardIndex == -1) {
            // 沒有可出的牌
            return null;
        } else {
            // 出牌
            Card playedCard = hand.removeCard(cardIndex);
            cli.displayMessage("Played: " + playedCard);
            return playedCard;
        }
    }
}
