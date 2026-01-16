
public class Treasure extends MapObject{
    private State state;
    
    public Treasure() {
        super('x');
        this.state = generateTreasureType();
    }

    /**
     * 隨機產生寶物類型
     * @return
     */
    private State generateTreasureType() {
        double random = Math.random();
        
        if (random < 0.1) {
            return State.INVINCIBLE;
        } else if (random < 0.35) { 
            return State.POISONED;  
        } else if (random < 0.55) { 
            return State.ACCELERATED;
        } else if (random < 0.70) { 
            return State.HEALING; 
        } else if (random < 0.80) { 
            return State.ORDERLESS; 
        } else if (random < 0.90) {
            return State.STOCKPILE; 
        } else {
            return State.TELEPORT; 
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}

