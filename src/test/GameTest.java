package test;

import game.Dice;
import game.DiceValue;
import game.Game;
import game.Player;

import static org.mockito.Mockito.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GameTest
{

	Game gameOneOrNoMatches;
	Game gameTwoMatches;
	Game gameThreeMatches;
	Player player;
	DiceValue pickAnchor;
	DiceValue pickSpade;
	Dice dieSpade;
	Dice dieHeart;
	Dice dieDiamond;
	final static int START_BALANCE = 100;

	public GameTest()
	{
		dieSpade = mock(Dice.class);
		dieHeart = mock(Dice.class);
		dieDiamond = mock(Dice.class);

		pickAnchor = DiceValue.ANCHOR;
		pickSpade = DiceValue.SPADE;

		player = new Player("Arthur", START_BALANCE);

		when(dieSpade.roll()).thenReturn(DiceValue.SPADE);
		when(dieHeart.roll()).thenReturn(DiceValue.HEART);
		when(dieDiamond.roll()).thenReturn(DiceValue.DIAMOND);

		when(dieSpade.getValue()).thenReturn(DiceValue.SPADE);
		when(dieHeart.getValue()).thenReturn(DiceValue.HEART);
		when(dieDiamond.getValue()).thenReturn(DiceValue.DIAMOND);

		gameOneOrNoMatches = new Game(dieSpade, dieHeart, dieDiamond);
		gameTwoMatches = new Game(dieSpade, dieSpade, dieDiamond);
		gameThreeMatches = new Game(dieSpade, dieSpade, dieSpade);
	}

	@Test
	public void TestPlayRoundNoMatch()
	{
		gameOneOrNoMatches.playRound(player, pickAnchor, 0);
		assertEquals(START_BALANCE, player.getBalance()); //if he bet zero, should have no change in balance

		int bet = START_BALANCE/4; //small bet
		gameOneOrNoMatches.playRound(player, pickAnchor, bet);
		assertEquals(START_BALANCE - bet, player.getBalance()); //should lose his bet amount

		player.setBalance(START_BALANCE); //put back to how it was to start with
	}

	@Test
	public void TestPlayRoundOneMatch()
	{
		int bet = START_BALANCE/4; //small bet
		gameOneOrNoMatches.playRound(player, pickSpade, bet);
		assertEquals(START_BALANCE + bet, player.getBalance()); //should get one times his bet back

		player.setBalance(START_BALANCE); //put back to how it was to start with
	}

	@Test
	public void TestPlayRoundTwoMatches()
	{
		int bet = START_BALANCE/4; //small bet
		gameTwoMatches.playRound(player, pickSpade, bet);
		assertEquals(START_BALANCE + bet + bet, player.getBalance()); //should get two times his bet back

		player.setBalance(START_BALANCE); //put back to how it was to start with
	}

	@Test
	public void TestPlayRoundThreeMatches()
	{
		int bet = START_BALANCE/4; //small bet
		gameThreeMatches.playRound(player, pickSpade, bet);
		assertEquals(START_BALANCE + bet + bet + bet, player.getBalance()); //should get three times his bet back

		player.setBalance(START_BALANCE); //put back to how it was to start with
	}
}
