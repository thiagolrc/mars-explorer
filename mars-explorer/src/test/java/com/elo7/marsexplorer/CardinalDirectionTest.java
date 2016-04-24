package com.elo7.marsexplorer;

import static com.elo7.marsexplorer.CardinalDirection.E;
import static com.elo7.marsexplorer.CardinalDirection.N;
import static com.elo7.marsexplorer.CardinalDirection.S;
import static com.elo7.marsexplorer.CardinalDirection.W;

import org.junit.Assert;
import org.junit.Test;

/** Testes para {@link CardinalDirection} */
public class CardinalDirectionTest {

	@Test
	public void northRotatingLeftShouldBecomeWest() {
		Assert.assertEquals(W, N.rotateLeft());
	}

	@Test
	public void northRotatingRighShouldBecomeEast() {
		Assert.assertEquals(E, N.rotateRight());
	}

	@Test
	public void eastRotatingLeftShouldBecomeNorth() {
		Assert.assertEquals(N, E.rotateLeft());
	}

	@Test
	public void eastRotatingRighShouldBecomeSouth() {
		Assert.assertEquals(S, E.rotateRight());
	}

	@Test
	public void southtRotatingLeftShouldBecomeEast() {
		Assert.assertEquals(E, S.rotateLeft());
	}

	@Test
	public void southRotatingRighShouldBecomeWest() {
		Assert.assertEquals(W, S.rotateRight());
	}

	@Test
	public void westRotatingLeftShouldBecomeSouth() {
		Assert.assertEquals(S, W.rotateLeft());
	}

	@Test
	public void westRotatingRighShouldBecomeNorth() {
		Assert.assertEquals(N, W.rotateRight());
	}
}
