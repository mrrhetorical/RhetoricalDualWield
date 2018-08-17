package com.rhetorical.dualwield;

class RayCast {

    private Point origin;
    private float distance, pitch, yaw;

    RayCast(Point origin, float pitch, float yaw, float distance) {
        this.origin = origin;
        this.pitch = pitch;
        this.yaw = yaw;
        this.distance = distance;
    }

    HitboxBounds getHit() {
        float traveled = 0f;
        Point lastChecked = origin;
        Point check = lastChecked;

        float timeSinceLastCheck = 1f;

        while(traveled <= distance) {

            float x, y, z;

            y = distance * (float) Math.cos(pitch);
            z = distance * (float) Math.cos(yaw);
            x = ((distance * (float) Math.sin(pitch)) + (distance * (float) Math.sin(yaw))) / 2f;

            check.x += x * timeSinceLastCheck;
            check.y += y * timeSinceLastCheck;
            check.z += z * timeSinceLastCheck;

            traveled += Point.distance(lastChecked, check);

            if (!(HitboxManager.interceptsHitbox(check) instanceof Boolean)) {
                return (HitboxBounds) HitboxManager.interceptsHitbox(check);
            }
        }

        return null;
    }
}
