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
}
