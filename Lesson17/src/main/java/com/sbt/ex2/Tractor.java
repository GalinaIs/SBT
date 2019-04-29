package com.sbt.ex2;

public class Tractor {
    private static final int INITIAL_X = 0;
    private static final int INITIAL_Y = 0;
    private static final int SIZE_FIELD_X = 5;
    private static final int SIZE_FIELD_Y = 5;

    private Point position = new Point(INITIAL_X, INITIAL_Y);
    private Orientation orientation = Orientation.NORTH;

    public void move(String command) {
        if (command.equalsIgnoreCase("F")) {
            moveForwards();
        } else if (command.equalsIgnoreCase("T")) {
            turnClockwise();
        }
    }

    private void moveForwards() {
        position.move(orientation.getOffset());

        if (position.getX() > SIZE_FIELD_X || position.getY() > SIZE_FIELD_Y) {
            throw new TractorInDitchException();
        }
    }

    private void turnClockwise() {
        orientation = orientation.getClockwise();
    }

    public int getPositionX() {
        return position.getX();
    }

    public int getPositionY() {
        return position.getY();
    }

    public Orientation getOrientation() {
        return orientation;
    }

}
