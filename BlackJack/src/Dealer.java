public class Dealer extends Participant {
    public void play(Deck deck) {
        while (getHandValue() < 17) {
            hit(deck.dealCard());
        }
    }
}

