package ru.stqa.geometry.figures.figures;


import static java.lang.Math.sqrt;

public record Triangle(double a,
                       double b,
                       double c) {

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
