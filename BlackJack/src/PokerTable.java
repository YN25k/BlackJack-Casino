import java.io.*;
import java.util.Scanner;

public class PokerTable {
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private static final String FILE_PATH = "players.txt";

    public PokerTable() {
        deck = new Deck();
        player = new Player();
        dealer = new Dealer();
        deck.shuffle();
    }

    public int start(int playerChip, int bet) {
        player.hit(deck.dealCard());
        player.hit(deck.dealCard());
        dealer.hit(deck.dealCard());
        dealer.hit(deck.dealCard());

        System.out.println("Dealer's Hand: " + dealer.getHand().getCards().get(0) + " and [Hidden]");
        System.out.println("Player's Hand: " + player.getHand());

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Do you want to hit or stand? (hit/stand): ");
            String action = scanner.nextLine();
            if (action.equalsIgnoreCase("hit")) {
                player.hit(deck.dealCard());
                System.out.println("Player's Hand: " + player.getHand());
                System.out.println("Current total: " + player.getHandValue());
                if (player.isBusted()) {
                    System.out.println("Player's Hand: " + player.getHand());
                    System.out.println("Current total: " + player.getHandValue());
                    System.out.println("Player busts! Dealer wins.");
                    return playerChip - bet;
                }
            } else if (action.equalsIgnoreCase("stand")) {
                break;
            } else {
                System.out.println("Invalid action. Please type 'hit' or 'stand'.");
            }
        }

        System.out.println("Dealer's Hand: " + dealer.getHand());
        dealer.play(deck);

        if (dealer.isBusted()) {
            System.out.println("Dealer's Hand: " + dealer.getHand());
            System.out.println("Dealer busts! Player wins.");
            return playerChip + bet;
        } else {
            // Determine winner
            int playerValue = player.getHandValue();
            int dealerValue = dealer.getHandValue();
            System.out.println("Player's Hand Value: " + playerValue);
            System.out.println("Dealer's Hand Value: " + dealerValue);

            if (playerValue > dealerValue) {
                System.out.println("Player wins!");
                return playerChip + bet;
            } else if (dealerValue > playerValue) {
                System.out.println("Dealer wins!");
                return playerChip - bet;
            } else {
                System.out.println("It's a tie!");
                return playerChip;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int playerChip = 100;
        int bet = 0;
        boolean keepPlaying = true;
        PokerTable game = new PokerTable();

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        playerChip = loadPlayerChips(username);

        System.out.println("You have " + playerChip + " PEPE coins to play with, try to out rank the others!");

        do {
            System.out.print("Place your bet: ");
            bet = scanner.nextInt();
            playerChip = game.start(playerChip, bet);
            System.out.println("You have: " + playerChip);
            System.out.print("Cashout? (y/n): ");
            String cashout = scanner.next();
            if (cashout.equalsIgnoreCase("y")) {
                keepPlaying = false;
                System.out.println("You cashed out with " + playerChip);
                System.out.println("You are " + (playerChip - 100) + " from the original amount.");
                savePlayerChips(username, playerChip);
            }
        } while (keepPlaying);
    }

    private static void savePlayerChips(String username, int chips) {
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_PATH))) {
            StringBuilder fileContent = new StringBuilder();
            String line;
            boolean found = false;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(username)) {
                    fileContent.append(username).append(":").append(chips).append("\n");
                    found = true;
                } else {
                    fileContent.append(line).append("\n");
                }
            }
            if (!found) {
                fileContent.append(username).append(":").append(chips).append("\n");
            }
            try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH))) {
                out.write(fileContent.toString());
            }
        } catch (IOException e) {
            System.err.println("Error saving player chips: " + e.getMessage());
        }
    }

    private static int loadPlayerChips(String username) {
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(username)) {
                    return Integer.parseInt(parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading player chips: " + e.getMessage());
        }
        System.out.println("Your new! Welcome to my website");
        return 100;
    }
}
