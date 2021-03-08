package java.pandemic.model;

import java.util.List;

public interface ISolution extends Comparable<ISolution> {

    void replaceCity(int idx, List<ICity> cities);

    void setFitness(double fitness);

    List<ICity> getCities();

    int getSize();

    ICity getCity(int idx);

    double getFitness();
}
