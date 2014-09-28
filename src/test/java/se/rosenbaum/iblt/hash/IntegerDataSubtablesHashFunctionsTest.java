package se.rosenbaum.iblt.hash;

import org.junit.Test;

import static se.rosenbaum.iblt.util.TestUtils.data;

/**
 * Created with IntelliJ IDEA.
 * User: kalle
 * Date: 9/26/14
 * Time: 9:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class IntegerDataSubtablesHashFunctionsTest {
    @Test
    public void test4and6() {
        IntegerDataSubtablesHashFunctions sut = new IntegerDataSubtablesHashFunctions(10, 2);
        int hash40 = sut.hash(0, data(4));
        int hash41 = sut.hash(1, data(4));

        int hash60 = sut.hash(0, data(6));
        int hash61 = sut.hash(1, data(6));

        int i = 0;
    }
}
