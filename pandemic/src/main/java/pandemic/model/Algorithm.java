package java.pandemic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// This class handles the logic of the model.
public class Algorithm implements IAlgorithm {

    private final IWorld world;
    private final double selectionPressure;
    private final double mutationRate;
    private final double reproductionRate;
    private final double fitnessPenalty;
    private List<ISolution> solutions;
    private final Random rnd = new Random();

    public Algorithm(IWorld world, int startingPop, double selectionPressure, double mutationRate, double reproductionRate) {
        this.world = world;
        this.solutions = generateSolutions(startingPop);
        this.selectionPressure = selectionPressure;
        this.mutationRate = mutationRate;
        this.reproductionRate = reproductionRate;
        this.fitnessPenalty = Math.hypot(world.getX(), world.getY());
    }

    @Override
    // Sequentially calls the components of the algorithm.
    public void run(){
        mutation();
        fitness();
        selection();
        crossover();
    }

    // Creates number of solutions equal to the starting population.
    private List<ISolution> generateSolutions(int pop) {
        List<ISolution> solutions = new ArrayList<>();
        for (int i = 0; i < pop; i++) {
            solutions.add(new Solution(world.getCities()));
        }
        return solutions;
    }

    // Creates a random double. If this is below the mutation rate specified, a random city will be replaced.
    private void mutation () {
        for (ISolution solution: solutions) {
            double threshold = rnd.nextDouble();
            if(threshold < mutationRate) {
                solution.replaceCity(rnd.nextInt(solution.getSize()), world.getCities());
            }
        }
    }

    // Calculates and sets the fitness for all solutions.
    private void fitness () {
        double fitness = 0;
        for (ISolution solution: solutions) {
            fitness += visitedCites(solution);
            fitness += cityDistance(solution);
            solution.setFitness(fitness);
            fitness = 0;
        }
    }

    // Sorts the solutions according to fitness and removes the worst. The number removed depends on selection pressure.
    private void selection() {
        Collections.sort(solutions);
        solutions = new ArrayList<>(solutions.subList(0,(int) Math.round(solutions.size()*(1-selectionPressure))));
    }

    // Iterates all solutions and creates new solutions as a combination of this and another, random solution.
    private void crossover() {
        List<ISolution> newPop = new ArrayList<>(solutions);
        for(ISolution solution: solutions) {
            newPop.addAll(produceOffspring(solution,solutions.get(rnd.nextInt(solutions.size()))));
        }
        solutions = newPop;
    }

    // If a city in the world is not in the solution a penalty will be given.
    private double visitedCites(ISolution solution) {
        double fitness = 0;
        for(ICity city: world.getCities()) {
            if (! solution.getCities().contains(city))
                fitness += fitnessPenalty;
        }
        return fitness;
    }

    // Calculates the sum of the hypotenuses between the cities in the solution.
    private double cityDistance(ISolution solution) {
        double fitness = 0;
        int d0 = 0;
        int d1 = 0;
        for(int i = 0; i < solution.getSize(); i++) {
            d0 = i % solution.getSize();
            d1 = (i + 1) % solution.getSize();
            fitness += Math.hypot(solution.getCity(d1).getX() - solution.getCity(d0).getX(),
                    solution.getCity(d1).getY() - solution.getCity(d0).getY());
        }
        return fitness;
    }

    // Helper function for crossover.
    // Creates a number of new solutions as a combination of two old solutions.
    // The number of new solutions is dependent on reproduction rate.
    private List<Solution> produceOffspring(ISolution sol1, ISolution sol2) {
        ArrayList<Solution> offspring = new ArrayList<>();
        for (int i = 0; i < 10*reproductionRate; i++) {
            offspring.add(combineSolutions(sol1, sol2));
        }
        return offspring;
    }

    // Helper function for produceOffspring.
    // Combines two solutions by taking the beginning of the first solution of random length
    // and appending the end of the other solution of the remaining length.
    private Solution combineSolutions(ISolution sol1, ISolution sol2){
        int cutOff = rnd.nextInt(world.getCities().size());
        List<ICity> combinedCities = new ArrayList<>(sol1.getCities().subList(0,cutOff));
        combinedCities.addAll(sol2.getCities().subList(cutOff, sol2.getSize()));
        return new Solution(combinedCities);
    }

    @Override
    public List<ICity> getCities() {
        return this.world.getCities();
    }

    @Override
    // Returns the best solution. Used for UI.
    public List<ICity> getSolution() {
        return this.solutions.get(0).getCities();
    }

    @Override
    public int getPopulationSize() {
        return solutions.size();
    }

    @Override
    // Returns fitness of the best solution. Used for UI.
    public int getFitness() {
        return (int) Math.round(solutions.get(0).getFitness());
    }

}
