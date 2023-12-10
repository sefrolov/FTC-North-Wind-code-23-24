package org.firstinspires.ftc.teamcode.maths;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import org.opencv.core.Mat;

public class vec2 {
    double X, Y;

    public vec2(double a) {
        X = a;
        Y = a;
    }

    public vec2(double x, double y) {
        X = x;
        Y = y;
    }

    public vec2 set(double a) {
        X = a;
        Y = a;

        return this;
    }

    public vec2 set(vec2 a) {
        X = a.X;
        Y = a.Y;

        return this;
    }

    public vec2 set(double x, double y) {
        X = x;
        Y = y;

        return this;
    }

    public vec2 add(double a, double b) {
        X += a;
        Y += b;

        return this;
    }

    public vec2 add(vec2 a) {
        this.add(a.X, a.Y);

        return this;
    }

    public vec2 minus(vec2 a){
        this.add(a.invert());
        return this;
    }

    public vec2 minus(double X, double Y){
        this.minus(new vec2(X,Y));
        return this;
    }

    public vec2 plus(double a, double b) {
        return new vec2(X + a, Y + b);
    }

    public vec2 plus(vec2 a) {
        return plus(a.X, a.Y);
    }

    public vec2 neg() {
        return new vec2(-X, -Y);
    }

    public vec2 invert(){
        X = -X;
        Y = -Y;

        return this;
    }

    public vec2 mul(double a) {
        this.X *= a;
        this.Y *= a;
        return this;
    }
    public double scalMul(vec2 b){
        return (this.X * b.X + this.Y * b.Y);
    }

    public double vecMul(vec2 b){
        return (this.X * b.Y - this.Y * b.X);
    }

    public vec2 abs() {
        return new vec2(Math.abs(X), Math.abs(Y));
    }

    public double max() {
        return Math.max(X, Y);
    }

    public double min() {
        return Math.min(X, Y);
    }

    public double len2() {
        return X * X + Y * Y;
    }

    public double len() {
        return sqrt(len2());
    }

    public vec2 turn(double ang){
        double newX = this.X * cos(ang) - this.Y * sin(ang);
        Y = this.X * sin(ang) + this.Y * cos(ang);
        X = newX;
        return new vec2(X, Y);
    }

    public vec2 turned(double ang){
        return new vec2(this.X * cos(ang) - this.Y * sin(ang), this.X * sin(ang) + this.Y * cos(ang));
    }
    public double radToDeg(double ang){
        return ang * 180 / Math.PI;
    }

    public double DegToRad(double ang){
        return ang * Math.PI / 180;
    }

    public double getX(){
        return this.X;
    }

    public double getY(){
        return this.Y;
    }

    public vec2 normalize(){
        double c;

        if ((c = Math.max(Math.abs(this.X), Math.abs(this.Y))) > 1){
            this.X /= c;
            this.Y /= c;
        }
        return this;
    }

    public double getDirection(){
        return atan2(Y, X);
    }
}
