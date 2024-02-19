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
        var a = 2;
        var b = 3;
        var c = 4;
        var t1 = new Triangle(a, b, c);
        var t2 = new Triangle(c, a, b);
        Assertions.assertEquals(t1, t2);
    }
}
