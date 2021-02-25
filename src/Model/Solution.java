package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// A Solution contains a list of cities (ICity) and the fitness of that solution.
public class Solution implements ISolution  {

    private final List<ICity> cities;
    private double fitness;

    private final Random rnd = new Random();

    // Solution created by adding random cities.
    public Solution(List<ICity> cities) {
        this.cities = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            this.cities.add(cities.get(rnd.nextInt(cities.size())));
        }
    }

    @Override
    public void replaceCity(int index, List<ICity> citiesInWorld){
        cities.remove(index);
        cities.add(index,citiesInWorld.get(rnd.nextInt(citiesInWorld.size())));
    }

    @Override
    public void setFitness(double newFitness) {
        this.fitness = newFitness;
    }

     @Override
    public List<ICity> getCities(){
        return cities;
    }

    @Override
    public int getSize(){
        return cities.size();
    }

    @Override
    public ICity getCity(int idx) {
        return cities.get(idx);
    }

    @Override
    public double getFitness(){
        return fitness;
    }

    @Override
    public String toString(){
        return cities.toString();
    }

    @Override
    // Compares the fitness of different solutions.
    public int compareTo(ISolution o) {
        return Double.compare(this.fitness, o.getFitness());
    }


}
