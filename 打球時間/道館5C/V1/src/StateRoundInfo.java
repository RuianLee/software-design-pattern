public class StateRoundInfo {
    private int rounds;
    private String currentState;
    private String nextState;

    public StateRoundInfo(int rounds, String currentState, String nextState) {
        this.rounds = rounds;
        this.currentState = currentState;
        this.nextState = nextState;
    }

    public int getRounds() {
        return rounds;
    }

    public String getCurrentState() {
        return currentState;
    }

    public String getNextState() {
        return nextState;
    }

    public void decreaseRound() {
        if (rounds > 0) {
            rounds--;
        }
    }

    public boolean isExpired() {
        return rounds <= 0;
    }
}
