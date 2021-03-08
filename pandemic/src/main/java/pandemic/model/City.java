package pandemic.model;

// A city is functionally a circle.
public class City implements ICity {

    private final int X_POS;
    private final int Y_POS;
    private final double size;

    public City(int X_POS, int Y_POS, double size) {
        this.X_POS = X_POS;
        this.Y_POS = Y_POS;
        this.size = size;
    }

    public City(int X_POS, int Y_POS) {
        this.X_POS = X_POS;
        this.Y_POS = Y_POS;
        this.size = 20;
    }

    @Override
    public int getX(){
        return X_POS;
    }

    @Override
    public int getY(){
        return Y_POS;
    }

    @Override
    public double getSize() {
        return this.size;
    }

    @Override
    public String toString() {
        return "("+ X_POS +", "+ Y_POS +")";
    }




}
