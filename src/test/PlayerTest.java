package test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import game.Player;

public class PlayerTest
{

	Player player;

	public PlayerTest()
	{
		player = new Player("Inna", 20);
		assertEquals(0, player.getLimit());
		assertEquals(20, player.getBalance());
	}

	@Test
	public void TestReceiveWinnings()
	{
		int balance = player.getBalance();
		int winnings = 10;
		player.receiveWinnings(winnings);
		assertEquals(balance + winnings, player.getBalance());

		player.setBalance(balance);
	}

	@Test
	public void TestTakeBetNegative()
	{
		int bet = -1;
		int balance = player.getBalance();
		try
		{
			player.takeBet(bet);
			assertEquals(true, false); //if we get to this point, the exception was not thrown.
		}
		catch(IllegalArgumentException e)
		{
			assertEquals(true, e.getMessage().equals("Bet cannot be negative."));
		}
		finally
		{
			player.setBalance(balance);
			/*just in case it changed - in finally block so it gets put back
			 * whether or not an exception was thrown (so it won't affect the
			 * other tests)
			 */
		}
	}



	@Test
	public void TestTakeBetGood()
	{
		int balance = player.getBalance();
		assertEquals(true, balance > 2); //so we can make sure we get a positive bet that is smaller than balance
		int bet = balance - 1;

		boolean bThrewException = true;
		try
		{
			player.takeBet(bet);
			bThrewException = false; //only gets here if no exception thrown.
		}
		catch(IllegalArgumentException e)
		{
			assertEquals(true, false);//don't want an exception thrown
		}
		finally
		{
			assertEquals(false, bThrewException);
			player.setBalance(balance);
		}
	}

	@Test
	public void TestTakeBetBig()
	{
		int limit = player.getLimit();
		int balance = player.getBalance();
		player.setLimit(5);
		int bet = balance - player.getLimit() + 1;

		try
		{
			player.takeBet(bet);
			assertEquals(true, false); //if we get to this point, the exception was not thrown.
		}
		catch(IllegalArgumentException e)
		{
			assertEquals(true, e.getMessage().equals("Placing bet would go below limit."));
		}
		finally
		{
			player.setBalance(balance);
			player.setLimit(limit);
		}
	}
}
