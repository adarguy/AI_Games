public class StateNim extends State {
	
    public int coins;
	
    public StateNim() {
        player = 1;
		coins = 13;
    }
    
    public StateNim(StateNim state) {
        this.coins = state.coins;    
        player = state.player;
    }
    
    public String toString() {
		return String.valueOf(coins);
    }
}
