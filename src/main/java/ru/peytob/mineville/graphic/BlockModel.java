package ru.peytob.mineville.graphic;

public class BlockModel {
    protected final float[] northSide;
    protected final float[] southSide;
    protected final float[] westSide;
    protected final float[] eastSide;
    protected final float[] topSide;
    protected final float[] bottomSide;

    public BlockModel(float[] northSide, float[] southSide, float[] westSide, float[] eastSide,
                      float[] topSide, float[] bottomSide) {
        this.northSide = northSide;
        this.southSide = southSide;
        this.westSide = westSide;
        this.eastSide = eastSide;
        this.topSide = topSide;
        this.bottomSide = bottomSide;
    }

    public float[] getNorthSide() {
        return northSide;
    }

    public float[] getSouthSide() {
        return southSide;
    }

    public float[] getWestSide() {
        return westSide;
    }

    public float[] getEastSide() {
        return eastSide;
    }

    public float[] getTopSide() {
        return topSide;
    }

    public float[] getBottomSide() {
        return bottomSide;
    }
}
