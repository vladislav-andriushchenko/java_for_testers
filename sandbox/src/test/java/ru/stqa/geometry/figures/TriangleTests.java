package ru.stqa.geometry.figures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.geometry.figures.figures.Triangle;

public class TriangleTests {

    @Test
    void testCheckPerimeter() {
        var triangle = new Triangle(13, 14, 15);
        var result = triangle.perimeter();

        Assertions.assertEquals(42, result);
    }

    @Test
    void testCheckArea() {
        var triangle = new Triangle(13, 14, 15);
        var result = triangle.area();

        Assertions.assertEquals(84, result);
    }

    @Test
    void testNotPossibleToCreateTriangleWithNegativeSide() {
        try {
            new Triangle(12, 14, -15);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            //OK
        }
    }

    @Test
    void testNotPossibleToCreateTriangleWithIncorrectSumOfTwoSides() {
        try {
            new Triangle(1, 15, 10);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            //OK
        }
    }

    @Test
    void testEquality() {
        var t1 = new Triangle(3, 4, 5);
        var t2 = new Triangle(4, 5, 3);
        Assertions.assertEquals(t1, t2);
    }
}
