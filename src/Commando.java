/**
 * <p> <b>Commando </b>, derived from Soldier Class, represents the commando soldiers in the simulation. </p>
 * @author Berkant Bayraktar
 * @version 1.0
 */
public class Commando extends Soldier{

    /**
     * flag to be able to check whether Commando should return the step function or not.
     */
    private boolean shouldReturn;

    /**
     * <p> Creates Commando object with the given parameters. </p>
     * <p> Name and position of the soldier are given to the parent class's constructor as parameter.</p>
     * <p> Other values which specific to the Commando type is also given to parent class's constructor as parameter. </p>
     * <p> Values specific to Commando type are given below</p>
     * <ul>
     *     <li> {@link SoldierState}: SEARCHING </li>
     *     <li> {@link SoldierType}: REGULAR</li>
     *     <li> collision range of the Soldier: 2.0</li>
     *     <li> shooting range of the Soldier: 10.0</li>
     *     <li> speed of the Soldier: 1.0 </li>
     * </ul>
     * @param name Name of the Commando
     * @param position Position of the Commando
     */

    public Commando(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, 10.0,SoldierType.COMMANDO, 2.0, 10.0, SoldierState.SEARCHING);
        this.shouldReturn = false;
    }

    /**
     * Gets the information that whether Commando should return the step function or not.
     * @return  The information that whether Commando should return the step function or not.
     */

    public boolean isShouldReturn() {
        return shouldReturn;
    }

    /**
     * Sets the information that whether Commando should return the step function or not.
     * @param shouldReturn  The information that whether Commando should return the step function or not.
     */

    public void setShouldReturn(boolean shouldReturn) {
        this.shouldReturn = shouldReturn;
    }

    /**
     * <p> Commando object uses this method to be able to act in the simulation. </p>
     * <p> Commando object can be in two different state which are {@link SoldierState#SEARCHING}, {@link SoldierState#SHOOTING}  </p>
     * <p> The instructions according to the states are given below.</p>
     * <p> State: SEARCHING </p>
     * <ul>
     *     <li> If this is the first step call, selects soldier direction randomly.</li>
     *     <li> Calculates the euclidean distance to the closest zombie. </li>
     *     <li> If the distance is shorter than or equal to the shooting range of the soldier, changes soldier
     *      * direction to the zombie and changes state to SHOOTING and returns </li>
     *     <li> Calculates the next position of the soldier with formula: new_position = position + direction * speed </li>
     *     <li> If the position is out of bounds, changes direction to random value. </li>
     *     <li> If the position is not out of bounds, changes soldier position to the new_position. </li>
     *     <li> Calculates the euclidean distance to the closest zombie.</li>
     *     <li> If the distance is shorter than or equal to the shooting range of the soldier, changes state to
     * SHOOTING. </li>
     * </ul>
     *
     * <p> State: SHOOTING </p>
     * <ul>
     *     <li> Creates a bullet with the position and direction same as soldier's. </li>
     *     <li> Calculates the euclidean distance to the closest zombie. </li>
     *     <li> If the distance is shorter than or equal to the shooting range of the soldier, changes soldier
     * direction to zombie.</li>
     *     <li> If not, randomly changes soldier direction and change state to SEARCHING. </li>
     * </ul>
     *
     * @param controller Simulation Controller
     */

    @Override
    public void step(SimulationController controller) {
        switch (this.getSoldier_state()){
            case SEARCHING:
                if(this.isFirstStep()){  // SET RANDOM DIRECTION IF THIS IS THE FIRST STEP CALL
                    this.setRandomDirection();
                    this.setFirstStep(false);
                }

                this.aim(controller); // CALCULATE THE EUCLIDEAN DISTANCE TO CLOSEST ZOMBIE. IF THE DISTANCE IS SHORTER THAN OR EQUAL TO THE SHOOTING RANGE OF THE SOLDIER, CHANGE SOLDIER DIRECTION TO ZOMBIE AND CHANGE STATE TO SHOOTING

                if(this.isShouldReturn()) { // RETURN IMMEDIATELY , IF THE DISTANCE IS SHORTER THAN OR EQUAL TO THE SHOOTING RANGE. NOTE THAT shouldReturn flag is set in aim() function.
                    this.setShouldReturn(false);
                    return;
                }

                this.moveOrChangeDirection(controller); // CALCULATE NEXT POSITION AND TRY TO RUN , IF THE POSITION IS OUT OF BOUNDS CHANGE DIRECTION TO RANDOM VALUE.

                this.aim(controller); // CALCULATE THE EUCLIDEAN DISTANCE TO CLOSEST ZOMBIE. IF THE DISTANCE IS SHORTER THAN OR EQUAL TO THE SHOOTING RANGE OF THE SOLDIER, CHANGE SOLDIER DIRECTION TO ZOMBIE AND CHANGE STATE TO SHOOTING
                this.setShouldReturn(false); // SET shouldReturn flag FALSE
                break;
            case SHOOTING:
                SimulationObject bullet = new CommandoBullet("Bullet" + Bullet.getBullet_number(), this.getPosition(), this.getDirection()); // CREATE BULLET
                controller.addSimulationObject(bullet);
                System.out.println(this.getName() + " fired " + bullet.getName() + " to " + bullet.getDirection() + ".");

                double distance_to_closest_zombie =  calculateDistanceToEnemy(controller);
                SimulationObject enemy;


                if(distance_to_closest_zombie <= this.getShooting_range()){
                    enemy = this.nearestEnemy(controller);
                    this.turnToEnemy(enemy);

                }

                else{
                    this.setRandomDirection(); // SET RANDOM DIRECTION
                    this.setGivenState(SoldierState.SEARCHING); // SET GIVEN STATE
                }

                break;
            default:
                System.out.println("Invalid State");
        }
    }

    /**
     * <p> Calculates the euclidean distance to the closest zombie. </p>
     * <p> If the distance is shorter than or equal to the shooting range of the soldier, changes soldier
     * direction to the zombie and changes state to SHOOTING and returns </p>
     *
     * @param controller Simulation Controller
     */

    @Override
    public void aim(SimulationController controller) {
        SimulationObject enemy;
        double distance_to_closest_zombie;
        distance_to_closest_zombie = calculateDistanceToEnemy(controller);

        if(distance_to_closest_zombie <= this.getShooting_range()){
            enemy = this.nearestEnemy(controller);
            this.turnToEnemy(enemy);
            this.setSoldier_state(SoldierState.SHOOTING);
            System.out.println(this.getName() + " changed state to " + this.getSoldier_state() + ".");
            this.setShouldReturn(true);
        }
    }
}
