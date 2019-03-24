import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class SimulationController {
    private final double height;
    private final double width;
    private List<SimulationObject> zombies = new ArrayList();
    private List<SimulationObject> soldiers = new ArrayList();
    private  List<SimulationObject> bullets = new ArrayList();

    public List<SimulationObject> getZombies() {
        return zombies;
    }

    public List<SimulationObject> getSoldiers() {
        return soldiers;
    }

    public List<SimulationObject> getBullets() {
        return bullets;
    }

    public SimulationController(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public boolean isInTheRange(double x, double y){   // CHECKS THE GIVEN LOCATION IS IN THE GRID OR NOT ?
        return ( x > 0 && x < getWidth() && y > 0 && y < getHeight());

    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    //Make sure to fill these methods for grading.
    public void stepAll() {
        for(int i = 0 ; i < soldiers.size() ; i++){
            soldiers.get(i).step(this );
        }
        for(int i = 0 ; i < zombies.size() ; i++){
            zombies.get(i).step(this );
        }
        for(int i = 0 ; i < bullets.size() ; i++){
            if(bullets.get(i).isActive())
                bullets.get(i).step(this );
            else
                bullets.get(i).setActive(true);
        }
    
    }
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
                System.out.println("Invalid class name ADD");
        }


    }
    
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
