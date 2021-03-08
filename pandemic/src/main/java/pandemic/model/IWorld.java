package pandemic.model;

import java.util.List;

public interface IWorld {

    int getX();

    int getY();

    List<ICity> getCities();
}
