package com.KrishanRoy.krishanroy.bookswappers;

import com.KrishanRoy.krishanroy.bookswappers.ui.GenerateRandomNumber;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private GenerateRandomNumber generateRandomNumber;
    @Before
    public void setUp(){
        generateRandomNumber = new GenerateRandomNumber();
    }
    @Test
    public void random_number_test(){
        int a = generateRandomNumber.randomNumber();
        int result = a;
        assertEquals(result, a);
    }
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

}