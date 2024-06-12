public abstract class Participant {
    protected Hand hand;

    public Participant() {
        hand = new Hand();
    }

    public void hit(Card card) {
        hand.addCard(card);
    }

    public int getHandValue() {
        return hand.calculateValue();
    }

    public boolean isBusted() {
        return getHandValue() > 21;
    }

    public Hand getHand() {
        return hand;
    }
}

