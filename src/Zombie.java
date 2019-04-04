/**
 * <b> Zombie </b> represents the parent class of all the zombie types derived from.
 * Each type of the zombie derived from this base class.
 * @author Berkant Bayraktar
 * @version 1.0
 */

public abstract class Zombie extends  SimulationObject {
    /**
     * Type of the Zombie
     */
    private final ZombieType zombie_type;
    /**
     * State of the Zombie
     */
    private ZombieState zombie_state;
    /**
     * Collision range of the Zombie
     */
    private final double collision_range;
    /**
     * Detection range of the Zombie
     */
    private final double detection_range;
    /**
     * A flag to be able to check whether the zombie is in the first state or not
     */
    private boolean isFirstStep;

    /**
     * <p> Creates a Zombie object with the given parameters. Also, assigns True to the isFistStep variable by default.</p>
     * @param name Name of the Zombie
     * @param position Position of the Zombie
     * @param speed Speed of the Zombie
     * @param zombie_type Type of the Zombie
     * @param zombie_state State of the Zombie
     * @param collision_range Collision range of the Zombie
     * @param detection_range Detection range of the Zombie
     */

    public Zombie(String name, Position position, double speed, ZombieType zombie_type, ZombieState zombie_state, double collision_range, double detection_range) {
        super(name, position, speed);
        this.zombie_type = zombie_type;
        this.zombie_state = zombie_state;
        this.collision_range = collision_range;
        this.detection_range = detection_range;
        this.isFirstStep = true;
    }

    /**
     * Gets state of the Zombie.
     * @return State of the Zombie.
     */

    public ZombieState getZombie_state() {
        return zombie_state;
    }

    /**
     * Sets state of the Zombie.
     * @param zombie_state State of the Zombie.
     */

    public void setZombie_state(ZombieState zombie_state) {
        this.zombie_state = zombie_state;
    }

    /**
     * Gets type of the Zombie.
     * @return Type of the Zombie.
     */

    public ZombieType getZombie_type() {
        return zombie_type;
    }

    /**
     * Gets collision range of the Zombie
     * @return Collision range of the Zombie
     */

    public double getCollision_range() {
        return collision_range;
    }

    /**
     * Gets detection range of the Zombie
     * @return Detection range of the Zombie
     */

    public double getDetection_range() {
        return detection_range;
    }

    public boolean isFirstStep() {
        return isFirstStep;
    }

    public void setFirstStep(boolean firstStep) {
        isFirstStep = firstStep;
    }


    public boolean tryToKill(SimulationController controller, SimulationObject enemy ,double distance_to_closest_soldier ){
        if(distance_to_closest_soldier <= this.getCollision_range() + ((Soldier)enemy).getCollision_range()){
            System.out.println(this.getName() + " killed " + enemy.getName() + ".");
            controller.removeSimulationObject(enemy);
            return true;
        }
        else
            return false;
    }

}
