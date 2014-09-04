package edu.byu.auth.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 09/03/2014
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 09/03/2014
 */
@RunWith(JUnit4.class)
public class ByteArrayTest {

	private static final Logger LOG = LogManager.getLogger(ByteArrayTest.class);

	private static final int PREP_COUNT = 10000;
	private static final int TEST_COUNT = 1000000;

	@Test
	public void test() {
		prep();
		doTest();
	}

	private void prep() {
		for (int i = 0; i < PREP_COUNT; i++) {
			doOneIterationMethodA();
		}
		for (int i = 0; i < PREP_COUNT; i++) {
			doOneIterationMethodB();
		}
	}

	private void doTest() {
		final long a = System.currentTimeMillis();
		for (int i = 0; i < TEST_COUNT; i++) {
			doOneIterationMethodA();
		}
		final long b = System.currentTimeMillis();
		LOG.info("Time for bit method: " + (b - a) + " ms");

		final long c = System.currentTimeMillis();
		for (int i = 0; i < TEST_COUNT; i++) {
			doOneIterationMethodB();
		}
		final long d = System.currentTimeMillis();
		LOG.info("Time for buf method: " + (d - c) + " ms");
	}

	private void doOneIterationMethodA() {
		for (final int[] ints : intList) {
			final byte[] bytes = new byte[ints.length * 4];
			for (int i = 0; i < ints.length; i++) {
				bytes[4 * i + 3] = (byte) (ints[i]  & 0xff);
				bytes[4 * i + 2] = (byte) ((ints[i] >> 8)  & 0xff);
				bytes[4 * i + 1] = (byte) ((ints[i] >> 16)  & 0xff);
				bytes[4 * i + 0] = (byte) ((ints[i] >> 24)  & 0xff);
			}
		}
	}

	private void doOneIterationMethodB() {
		for (final int[] ints : intList) {
			final ByteBuffer buf = ByteBuffer.allocate(ints.length * 4);
			for (final int i : ints) {
				buf.putInt(i);
			}
			final byte[] bytes = buf.array();
		}
	}

	private static final List<int[]> intList = createIntArrays();

	private static List<int[]> createIntArrays() {
		final List<int[]> list = new ArrayList<int[]>(8);
		final int[] vals = new int[] {0xffffffff, 0xeeeeeeee, 0x88888888, 0xbbbbbbbb, 0x12121212, 0x98989898, 0x12345678, 0xfedcba98};
		final int s = vals.length;
		int pos = 0;
		for (int i = 0; i < 16; i++) {
			final int[] arr = new int[i];
			for (int j = 0; j < i; j++) {
				arr[j] = pos++ % s;
			}
			list.add(arr);
		}
		return list;
	}
}
