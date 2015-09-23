package com.akx2.skifreeze;

public enum PlayerPose {
    LEFT_90 (0) {
        @Override
        public PlayerPose previous() {
            return this;    // this is as far as the player may turn left
        };
    },
    LEFT_60 (1),
    LEFT_30 (2),
    STRAIGHT (3),
    RIGHT_30 (4),
    RIGHT_60 (5),
    RIGHT_90 (6) {
        @Override
        public PlayerPose next() {
            return this;        // this is as far as the play may turn right
        };
    },
    BAIL (7) {
        @Override
        public PlayerPose next() {
            return this;        // N/A
        };
        @Override
        public PlayerPose previous() {
            return this;    // N/A
        };
    },
    JUMP (8) {
        @Override
        public PlayerPose next() {
            return this;        // N/A
        };
        @Override
        public PlayerPose previous() {
            return this;    // N/A
        };
    };

    public final int code;

    PlayerPose(int poseCode) {
        code = poseCode;
    }

    public PlayerPose next() {
        // No bounds checking required here, because the last instance overrides
        return values()[ordinal() + 1];
    }

    public PlayerPose previous() {
        // No bounds checking required here, because the last instance overrides
        return values()[ordinal() - 1];
    }

    public int getSpeed ()
    {
        switch (this)
        {
            case LEFT_90:
                return SlopeSpeed.STOP.code;
            case LEFT_60:
                return SlopeSpeed.SLOW.code;
            case LEFT_30:
                return SlopeSpeed.FAST.code;
            case STRAIGHT:
                return SlopeSpeed.FASTEST.code;
            case RIGHT_30:
                return SlopeSpeed.FAST.code;
            case RIGHT_60:
                return SlopeSpeed.SLOW.code;
            case RIGHT_90:
                return SlopeSpeed.STOP.code;
            case BAIL:
                return SlopeSpeed.SLOWEST.code;
            case JUMP:
                return SlopeSpeed.FASTEST.code;
        }

        return 0;
    }

    public int getShift ()
    {
        switch (this)
        {
            case LEFT_90:
                return SlopeSpeed.SLOW.code;
            case LEFT_60:
                return SlopeSpeed.FAST.code;
            case LEFT_30:
                return SlopeSpeed.FAST.code;
            case STRAIGHT:
                return 0;
            case RIGHT_30:
                return -SlopeSpeed.FAST.code;
            case RIGHT_60:
                return -SlopeSpeed.FAST.code;
            case RIGHT_90:
                return -SlopeSpeed.SLOW.code;
            case BAIL:
                return 0;
            case JUMP:
                return 0;
        }

        return 0;
    }
}
