/**
 * <b> Soldier </b> represents the parent class of all the soldier types derived from.
 * Each type of the soldier derived from this base class.
 * @author Berkant Bayraktar
 * @version 1.0
 */


public abstract class Soldier extends SimulationObject {
    /**
     * Type of the Soldier
     */
    private final SoldierType soldier_type;

    /**
     * Collision range of the Soldier
     */
    private final double collision_range;

    /**
     *  Shooting range of the Soldier
     */
    private final double shooting_range;

    /**
     *  State of the Soldier
     */
    private  SoldierState soldier_state;
    /**
     * A flag to be able to check whether the soldier is in the first state or not
     */
    private boolean isFirstStep;

    /**
     * <p> Creates a Soldier object with the given parameters. Also, assigns True to the isFistStep variable by default.</p>
     * @param name Name of the Soldier
     * @param position Position of the Soldier
     * @param speed Speed of the Soldier
     * @param soldier_type Type of the Soldier
     * @param collision_range Collision range of the Soldier
     * @param shooting_range Shooting range of the Soldier
     * @param soldier_state State of the Soldier
     */

    public Soldier(String name, Position position, double speed, SoldierType soldier_type, double collision_range, double shooting_range, SoldierState soldier_state) {
        super(name, position, speed);
        this.soldier_type = soldier_type;
        this.collision_range = collision_range;
        this.shooting_range = shooting_range;
        this.soldier_state = soldier_state;
        this.isFirstStep = true;
    }

    /**
     * Gets state of the Soldier.
     * @return State of the Soldier.
     */
    public SoldierState getSoldier_state() {
        return soldier_state;
    }

    /**
     * Sets state of the Soldier.
     * @param soldier_state State of the Soldier.
     */

    public void setSoldier_state(SoldierState soldier_state) {
        this.soldier_state = soldier_state;
    }

    /**
     * Gets shooting range of the Soldier.
     * @return Shooting range of the Soldier.
     */

    public double getShooting_range() {
        return shooting_range;
    }


    /**
     * Gets collision range of the Soldier.
     * @return Collision range of the Soldier.
     */

    public double getCollision_range() {
        return collision_range;
    }

    /**
     * Gets the information that whether Soldier is in the first step or not
     * @return a Boolean according to whether Soldier is in the first step or not
     */

    public boolean isFirstStep() {
        return isFirstStep;
    }

    /**
     * Sets isFirstStep flag with the given parameter
     * @param firstStep Information that whether Soldier is in the first step or not
     */

    public void setFirstStep(boolean firstStep) {
        isFirstStep = firstStep;
    }

    /**
     * <p> Calculates the euclidean distance to the closest zombie. </p>
     * <p> If the distance is shorter than or equal to the shooting range of the soldier, changes soldier
     * direction to the zombie and changes state to SHOOTING. </p>
     * <p> If not, changes soldier state to SEARCHING</p>
     *
     * @param controller Simulation Controller
     */
    public void aim(SimulationController controller){
        SimulationObject enemy;
        double distance;

        distance = calculateDistanceToEnemy(controller);
        if(distance <= this.getShooting_range()){
            enemy = this.nearestEnemy(controller);
            this.turnToEnemy(enemy);
            this.setGivenState(SoldierState.SHOOTING);
        }
        else{
            this.setGivenState(SoldierState.SEARCHING);
        }
    }

    /**
     * <p> Adds the Bullet fired by Soldier to the simulation. </p>
     * <p> Calculates the euclidean distance to the closest zombie. </p>
     * <p> If the distance is shorter than or equal to the shooting range of the soldier, changes state to
     * AIMING.</p>
     * <p> If not, randomly changes soldier direction and changes state to SEARCHING. </p>
     * @param controller Simulation Controller
     * @param bullet Bullet that will be added to the simulation
     */

    public void shoot(SimulationController controller, SimulationObject bullet){
        double distance_to_closest_zombie;

        controller.addSimulationObject(bullet);
        System.out.println(this.getName() + " fired " + bullet.getName() + " to direction " + bullet.getDirection() + ".");
        distance_to_closest_zombie = calculateDistanceToEnemy(controller);
        if(distance_to_closest_zombie <= this.getShooting_range()) {
            this.setGivenState(SoldierState.AIMING);
        }
        else{
            this.setRandomDirection();
            this.setGivenState(SoldierState.SEARCHING);
        }
    }

    /**
     * Sets given Soldier State and prints the following information to the stdout.
     * <ul>
     *      <li> &lt;soldier_name&gt; changed state to &lt;state_name&gt;.&lt;newline&gt;</li>
     *      <li> For example:</li>
     *      <li> Soldier1 changed state to AIMING. </li>
     * </ul>
     * @param state State of the Soldier
     */

    public void setGivenState(SoldierState state){
        this.setSoldier_state(state); // CHANGE SOLDIER
        System.out.println(this.getName() + " changed state to " + this.getSoldier_state() + ".");
    }

}
