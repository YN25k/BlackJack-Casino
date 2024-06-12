public class Card {
    public enum Rank { TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE }

    private Rank rank;


    public Card(Rank rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return rank + " ";
    }

    public Rank getRank() {
        return rank;
    }
}
