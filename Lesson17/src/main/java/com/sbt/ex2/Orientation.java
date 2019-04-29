package com.sbt.ex2;

public enum Orientation {
    NORTH {
        Point getOffset() {
            return new Point(0, 1);
        }

        Orientation getClockwise() {
            return EAST;
        }
    },
    WEST {
        Point getOffset() {
            return new Point(-1, 0);
        }

        Orientation getClockwise() {
            return NORTH;
        }

    }, SOUTH {
        Point getOffset() {
            return new Point(0, -1);
        }

        Orientation getClockwise() {
            return WEST;
        }

    }, EAST {
        Point getOffset() {
            return new Point(1, 0);
        }

        Orientation getClockwise() {
            return SOUTH;
        }
    };

    abstract Point getOffset();
    abstract Orientation getClockwise();
}
