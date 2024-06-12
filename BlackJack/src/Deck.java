import java.util.*;

public class Deck {
    private Stack<Card> cards;

    public Deck() {
        cards = new Stack<>();
        for (int i=0;i<4;i++) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.push(new Card(rank));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        return cards.pop();
    }
}