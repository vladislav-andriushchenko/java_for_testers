package ru.stqa.geometry.figures.figures;


import static java.lang.Math.sqrt;

public record Triangle(double a,
                       double b,
                       double c) {

    public Triangle {
        if (a < 0 || b < 0 || c < 0) {
            throw new IllegalArgumentException("Triangle's side should be non-negative");

        } else if (a + b < c || a + c < b || b + c < a) {
            throw new IllegalArgumentException("The sum of two sides should not be more than third side");
        }
    }

    public double perimeter() {
        return this.a + this.b + this.c;
    }

    public double area() {
        var p = perimeter()/2;
        return sqrt(
                (p * (p - this.a) * (p - this.b) * (p - this.c))
        );
    }

}
