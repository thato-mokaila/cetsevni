package com.investec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntBinaryOperator;

public class AppTest {

    private final App app = new App();
    private Euclidean euclideanMock;

    @Before
    public void setUp() throws Exception {
        //  create mock
        euclideanMock = mock(Euclidean.class);
    }

    @Test
    public void reducerTest() {

        // Going back to my doc for this reducer logic:
        // gcd(a,b,c) = gcd(gcd(a,b),c) = gcd(a,gcd(b,c))
        //
        // One way to test this function is to validate
        // the number of invocations on our lambda function.
        // The function is already covered by the other test
        // but to drive the point home, we will verify these
        // invocations separately.

        List<Integer> source = new ArrayList<>();
        source.add(2);
        source.add(4);
        source.add(6);
        // source.add(8); // this should fail the test

        // train our mock
        when(euclideanMock.apply(2, 4)).thenReturn(2);
        when(euclideanMock.apply(2, 6)).thenReturn(2);

        // use the mock
        assertEquals( 2, app.reducer( source, euclideanMock));

        // verify expectations
        verify(euclideanMock, atLeastOnce()).apply(2, 4);
        verify(euclideanMock, atLeastOnce()).apply(2, 6);
    }

    @Test
    public void euclidTest() {
        Euclidean euclideanMock = new Euclidean();
        assertEquals( 2, euclideanMock.apply(2, 0) );
        assertEquals( 4, euclideanMock.apply(4, 8) );
    }

    @Test
    public void highestCommonFactorWithASingleElement() {
        assertEquals( 2, app.highestCommonFactor(App.getArray(2)) );
    }

    @Test
    public void highestCommonFactorWithTwoElements() {
        assertEquals( 6, app.highestCommonFactor(App.getArray(54, 24)) );
    }

    @Test
    public void highestCommonFactorWithMultipleElements() {
        assertEquals( 2, app.highestCommonFactor(App.getArray(30, 40, 36)) );
    }
}
