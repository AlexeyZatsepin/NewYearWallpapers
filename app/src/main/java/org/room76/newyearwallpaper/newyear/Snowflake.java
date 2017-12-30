package org.room76.newyearwallpaper.newyear;

/**
 * Created by Alexey on 12/30/17.
 */

public class Snowflake {
    // Position/direction
    public float xpos;
    public float ypos;
    // Velocity/Direction, only goes down in y dir
    float vel;
    // Gravity
    float gravity;

    public Snowflake(float xpos, float ypos, float vel, float gravity) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.vel = vel;
        this.gravity = gravity;
    }

    @Override
    public String toString() {
        return "x=" + xpos +
                ", y=" + ypos +
                ", vel=" + vel +
                ", gravity=" + gravity +
                '}';
    }
}
