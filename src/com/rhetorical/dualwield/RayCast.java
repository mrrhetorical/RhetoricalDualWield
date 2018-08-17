package com.rhetorical.dualwield;

class RayCast {

    private Point origin, direction;
    private float distance;

    RayCast(Point origin, Point direction, float distance) {
        this.origin = origin;
        this.direction = direction;
        this.distance = distance;
    }

    HitboxBounds getHit() {
        float traveled = 0f;
        Point lastChecked = origin;
        Point check = lastChecked;

        float timeSinceLastCheck = 1f;

        while(traveled <= distance) {
            check.x += direction.getX() * timeSinceLastCheck;
            check.y += direction.getY() * timeSinceLastCheck;
            check.z += direction.getZ() * timeSinceLastCheck;

            traveled += Point.distance(lastChecked, check);

            if (!(HitboxManager.interceptsHitbox(check) instanceof Boolean)) {
                return (HitboxBounds) HitboxManager.interceptsHitbox(check);
            }
        }

        return null;
    }
}
