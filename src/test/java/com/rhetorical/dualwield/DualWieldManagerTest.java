package com.rhetorical.dualwield;

import org.junit.Test;

import static org.junit.Assert.fail;

public class DualWieldManagerTest {

	@Test
	public void createTest() {
		try {
			DualWieldManager.create();
		} catch(DualWieldManagerAlreadyExistsException e) {
			e.printStackTrace();
			fail("DualWieldManager.create() threw an unexpected exception.");
			return;
		}

		try {
			DualWieldManager.create();
		} catch(DualWieldManagerAlreadyExistsException e) {
			//Expected exception
			assert (true);
		}
	}
}