import java.util.ArrayList;
import java.util.List;

/**
 * Most important class in the entire simulation. Controls all simulation objects and map dimensions. All simulation objects interact with this class.
 * @author Berkant Bayraktar
 * @version 1.0
 */
public class SimulationController {
    /**
     * height of the simulation grid.
     */
    private final double height;

    /**
     * width of the simulation grid.
     */
    private final double width;
    /**
     * List of Zombies in the simulation.
     */
    private List<SimulationObject> zombies = new ArrayList();

    /**
     * List of Soldiers in the simulation.
     */
    private List<SimulationObject> soldiers = new ArrayList();

    /**
     * List of Bullets in the simulation.
     */
    private  List<SimulationObject> bullets = new ArrayList();

    /**
     * Gets the zombies in the simulation
     * @return the zombies in the simulation
     */

    public List<SimulationObject> getZombies() {
        return zombies;
    }

    /**
     * Gets the soldiers in the simulation
     * @return the soldiers in the simulation
     */

    public List<SimulationObject> getSoldiers() {
        return soldiers;
    }

    /**
     * Creates a Simulation Environment with the given parameters.
     * @param width width of the simulation grid.
     * @param height height of the simulation grid.
     */

    public SimulationController(double width, double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Checks whether the given position is in the bounds or not.
     * @param x latitude
     * @param y longitude
     * @return a Boolean which holds information about whether the given position is in the bounds or not.
     */

    public boolean isInTheRange(double x, double y){   // CHECKS THE GIVEN LOCATION IS IN THE GRID OR NOT ?
        return ( x > 0 && x < getWidth() && y > 0 && y < getHeight());

    }

    /**
     * Gets height of the simulation grid.
     * @return height of the simulation grid.
     */

    public double getHeight() {
        return height;
    }

    /**
     * Gets width of the simulation grid.
     * @return width of the simulation grid.
     */

    public double getWidth() {
        return width;
    }

    /**
     * Simulates the objects in the given simulation in order Soldiers , Zombies and Bullets. Then, after all step functions are called, removes inActive objects from simulation.
     */

    //Make sure to fill these methods for grading.
    public void stepAll() {
        for(int i = 0 ; i < soldiers.size() ; i++){
            if(soldiers.get(i).isActive())
                soldiers.get(i).step(this );
        }
        for(int i = 0 ; i < zombies.size() ; i++){
            if(zombies.get(i).isActive())
                zombies.get(i).step(this );
        }
        for(int i = 0 ; i < bullets.size() ; i++){
            if(bullets.get(i).isActive())
                bullets.get(i).step(this );
            else
                bullets.get(i).setActive(true);
        }
        for(int i = 0 ; i < soldiers.size() ; i++){
            if(!soldiers.get(i).isActive())
                removeSimulationObject(soldiers.get(i));
        }
        for(int i = 0 ; i < zombies.size() ; i++){
            if(!zombies.get(i).isActive())
                removeSimulationObject(zombies.get(i));
        }
    
    }

    /**
     * Adds the simulation object given in the parameter to the simulation.
     * @param obj Simulation Object will be added to the simulation.
     */

    public void addSimulationObject(SimulationObject obj) {
        Class c = obj.getClass();
        switch (c.getSuperclass().getName()){
            case "Zombie":
                zombies.add(obj);
                break;

            case "Soldier":
                soldiers.add(obj);
                break;

            case "Bullet":
                bullets.add(obj);
                break;

            default:
                System.out.println("Invalid class name");
        }


    }

    /**
     * Removes the simulation object given in the parameter from the simulation.
     * @param obj Simulation Object will be removed from the simulation.
     */
    
    public void removeSimulationObject(SimulationObject obj) {
        Class c = obj.getClass();
        switch (c.getSuperclass().getName()){
            case "Zombie":
                zombies.remove(obj);
                break;
            case "Soldier":
                soldiers.remove(obj);
                break;
            case "Bullet":
                bullets.remove(obj);
                break;

            default:
                System.out.println("Invalid class name");
        }
    }

    /**
     * Checks whether there are only zombies or soldiers left in the simulation. If it is, returns true. If there is no zombie or soldier left, also returns true.
     * @return a Boolean which holds the information about whether there are only zombies or soldiers left in the simulation.
     */
    
    public boolean isFinished() {
        return (soldiers.isEmpty() || zombies.isEmpty());
    }

    @Override
    public String toString() {
        return "SimulationController{" +
                "height=" + height +
                ", width=" + width +
                ", zombies=" + zombies +
                ", soldiers=" + soldiers +
                ", bullets=" + bullets +
                '}';
    }
}
