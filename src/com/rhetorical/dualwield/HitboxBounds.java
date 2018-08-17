package com.rhetorical.dualwield;

import org.bukkit.entity.Entity;

class Point{
    float x, y, z;

    Point() {
        x = 0;
        y = 0;
        z = 0;
    }

    Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    static float distance(Point a, Point b) {
        float dist = 0f;

        if (a == b) {
            return dist;
        }

        dist = (float) Math.sqrt(((b.getX() - a.getX()) * (b.getX() - a.getX())) + ((b.getY() - a.getY()) * (b.getY() - a.getY())) + ((b.getZ() - a.getZ()) * (b.getZ() - a.getZ())));
        return dist;
    }

    float getX() {
        return x;
    }

    float getY() {
        return y;
    }

    float getZ() {
        return z;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("x: " + x);
        sb.append("y: " + y);
        sb.append("z: " + z);
        return sb.toString();
    }
}

class Direction {

    private float pitch, yaw;

    Direction() {
        pitch = 0;
        yaw = 0;
    }

    Direction(float p, float y) {
        pitch = p;
        yaw = y;
    }
}

class HitboxBounds {

    private Entity parent; // Temporary: Will be entity

    private Point A;
    private Point B;

    private float length;
    private float width;
    private float height;

    HitboxBounds(Point a, Point b, Entity parent) throws IllegalArgumentException {
        this.parent  = parent;

        A = a;
        B = b;

        height = a.getY() - b.getY();
        width = b.getX() - a.getX();
        length = b.getZ() - a.getZ();

        if (height <= 0 || width <= 0 || length <= 0) {
            throw new IllegalArgumentException("The bounds given for the hitbox does not create a cube!");
        }
    }

    boolean contains(Point point) {

        if (!(point.getX() >= parent.getLocation().getX() + A.getX() && point.getX() <= parent.getLocation().getX() + B.getX())) {
            return false;
        }

        if (!(point.getY() >= parent.getLocation().getY() + B.getY() && point.getY() <= parent.getLocation().getY() + A.getY())) {
            return false;
        }

        return point.getZ() >= parent.getLocation().getZ() + A.getZ() && point.getZ() <= parent.getLocation().getZ() + B.getZ();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Upper bound: " + A.toString());
        sb.append(" Lower bound: " + B.toString());
        sb.append(" Height: " + height);
        sb.append(" Width: " + width);
        sb.append(" Length: " + length);

        return sb.toString();
    }
}
