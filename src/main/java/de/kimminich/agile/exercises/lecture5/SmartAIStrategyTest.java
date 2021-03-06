package de.kimminich.agile.exercises.lecture5;

import de.kimminich.agile.exercises.lecture2.Card;
import de.kimminich.agile.exercises.lecture2.Player;
import de.kimminich.agile.exercises.lecture3.ICardChooserStrategy;
import org.junit.Test;

import static de.kimminich.agile.exercises.lecture5.helper.PlayerHelper.*;
import static org.junit.Assert.assertEquals;

public class SmartAIStrategyTest {

    ICardChooserStrategy strategy = (player, opponent) -> null; // TODO Instantiate implementation to be tested instead

    @Test
    public void shouldNotKeepAnyZeroCardsInHand() {
        Player player = new Player();
        putCardsInHandOf(player, 0, 0, 1, 2, 3);

        Player opponent = new Player();

        Card cardToPlay;
        while ((cardToPlay = strategy.nextCardToPlay(player, opponent)) != null) {
            simulatePlayingCard(player, cardToPlay);
        }

        assertEquals(0, countOccurencesOfCardInHand(player, 0));
    }

    @Test
    public void shouldPreferToPlayMultipleLowCardsOverSingleHighCardToPreventOverload() {
        Player player = new Player();
        player.setMana(7);
        putCardsInHandOf(player, 1, 2, 4, 7, 6);

        Player opponent = new Player();

        Card cardToPlay;
        while ((cardToPlay = strategy.nextCardToPlay(player, opponent)) != null) {
            simulatePlayingCard(player, cardToPlay);
        }

        assertEquals(1, countOccurencesOfCardInHand(player, 7));
        assertEquals(1, countOccurencesOfCardInHand(player, 6));
        assertEquals(0, countTotalOccurencesOfCardsInHand(player, 1, 2, 4));
    }

}
