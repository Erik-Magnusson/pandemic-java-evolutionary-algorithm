package java.pandemic.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// A World has a size (X/Y) and a list of cities (ICity).
public class World implements IWorld {

    private final int X_SIZE;
    private final int Y_SIZE;
    private final List<ICity> cities;

    public World(int X_SIZE, int Y_SIZE, int nrOfCities) {
        this.X_SIZE = X_SIZE;
        this.Y_SIZE = Y_SIZE;
        this.cities = new ArrayList<>(nrOfCities);
        initCities(nrOfCities);
    }

    // Randomly places the specified number of cities in the world.
    private void initCities(int nrOfCities){
        Random rnd = new Random();
        int i = 0;
        while(i < nrOfCities) {
            // Create a city at a random position.
            // Positions given by (BOUND-2r)+r, where r=radius to ensure the whole circumference is contained by the world.
            ICity city = new City(rnd.nextInt(X_SIZE-20)+10,rnd.nextInt(Y_SIZE-20)+10,20);
            // Check that no two cities overlap before adding, else create a new city.
            if (cities.stream().noneMatch(c ->
                    (Math.abs(c.getX()-city.getX()) <= (city.getSize() / 2) ||
                            Math.abs(c.getY()-city.getY()) <= (city.getSize() / 2 )))) {
                cities.add(city);
                i++;
            }
        }
    }

    /*

    Can be used to create a 9 cities in a square.

    private void testCities() {
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                ICity city = new City((i+1)*100,(j+1)*100);
                if(!(city.getX() == 200 && city.getY() == 200))
                    cities.add(city);
            }

        }
    }

     */

    @Override
    public int getX(){
        return X_SIZE;
    }

    @Override
    public int getY(){
        return Y_SIZE;
    }

    @Override
    public List<ICity> getCities(){
        return new ArrayList<>(cities);
    }
}
