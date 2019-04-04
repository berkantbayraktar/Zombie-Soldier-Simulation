/**
 * <p> <b>RegularSoldier </b>, derived from Soldier Class, represents the regular soldiers in the simulation. </p>
 * @author Berkant Bayraktar
 * @version 1.0
 */
public class RegularSoldier extends  Soldier{
    /**
     * <p> Creates RegularSoldier object with the given parameters. </p>
     * <p> Name and position of the soldier are given to the parent class's constructor as parameter.</p>
     * <p> Other values which specific to the Regular Soldier type is also given to parent class's constructor as parameter. </p>
     * <p> Values specific to the Regular Soldier type are given below</p>
     * <ul>
     *     <li> {@link SoldierState}: SEARCHING </li>
     *     <li> {@link SoldierType}: REGULAR</li>
     *     <li> collision range of the Soldier: 2.0</li>
     *     <li> shooting range of the Soldier: 20.0</li>
     *     <li> speed of the Soldier: 5.0 </li>
     * </ul>
     * @param name Name of the Regular Soldier
     * @param position Position of the Regular Soldier
     */

    public RegularSoldier(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, 5.0,SoldierType.REGULAR, 2.0, 20.0, SoldierState.SEARCHING);
    }

    /**
     * <p> RegularSoldier object uses this method to be able to act in the simulation. </p>
     * <p> RegularSoldier object can be in three different state which are {@link SoldierState#SEARCHING}, {@link SoldierState#AIMING}, {@link SoldierState#SHOOTING}  </p>
     * <p> The instructions according to the states are given below.</p>
     * <p> State: SEARCHING </p>
     * <ul>
     *     <li> If this is the first step call, selects soldier direction randomly.</li>
     *     <li> Calculates the next position of the soldier with formula: new_position = position + direction * speed </li>
     *     <li> If the position is out of bounds, changes direction to random value. </li>
     *     <li> If the position is not out of bounds, changes soldier position to the new_position. </li>
     *     <li> Calculates the euclidean distance to the closest zombie.</li>
     *     <li> If the distance is shorter than or equal to the shooting range of the soldier, changes state to
     * AIMING. </li>
     * </ul>
     * <p> State: AIMING </p>
     * <ul>
     *     <li> Calculates the euclidean distance to the closest zombie.</li>
     *     <li> If the distance is shorter than or equal to the shooting range of the soldier, changes soldier
     * direction to zombie and changes state to SHOOTING.</li>
     *     <li> If not, changes state to SEARCHING.</li>
     * </ul>
     * <p> State: SHOOTING </p>
     * <ul>
     *     <li> Creates a bullet with the position and direction same as soldier's. </li>
     *     <li> Calculates the euclidean distance to the closest zombie. </li>
     *     <li> If the distance is shorter than or equal to the shooting range of the soldier, changes state to
     * AIMING.</li>
     *     <li> If not, randomly changes soldier direction and change state to SEARCHING. </li>
     * </ul>
     *
     * @param controller Simulation Controller
     */

    @Override
    public void step(SimulationController controller) {
        double distance_to_closest_zombie;

        switch (this.getSoldier_state()){
            case SEARCHING:
                if(this.isFirstStep()){  // SET RANDOM DIRECTION IF THIS IS THE FIRST STEP CALL
                    this.setRandomDirection();
                    this.setFirstStep(false);
                }

                this.moveOrChangeDirection(controller); // CALCULATE NEXT POSITION AND TRY TO RUN , IF THE POSITION IS OUT OF BOUNDS CHANGE DIRECTION TO RANDOM VALUE.

                distance_to_closest_zombie = calculateDistanceToEnemy(controller); // CALCULATE THE DISTANCE TO NEAREST ZOMBIE

                if(distance_to_closest_zombie <= this.getShooting_range()){ // IF THE CLOSEST DISTANCE < SHOOTING RANGE , CHANGE THE STATE AS AIMING
                    this.setGivenState(SoldierState.AIMING);
                }

                break;

            case AIMING:
                this.aim(controller); // CALCULATE THE EUCLIDEAN DISTANCE TO CLOSEST ZOMBIE. IF THE DISTANCE IS SHORTER THAN OR EQUAL TO THE SHOOTING RANGE OF THE SOLDIER, CHANGE SOLDIER DIRECTION TO ZOMBIE AND CHANGE STATE TO SHOOTING ELSE CHANGE STATE TO SEARCHING
                break;

            case SHOOTING:
                SimulationObject bullet = new RegularSoldierBullet("Bullet" + Bullet.getBullet_number(), this.getPosition(), this.getDirection());
                this.shoot(controller, bullet);// CREATE BULLET WITH THE POSITION AND DIRECTION OF THE SOLDIER THEN ADD IT TO SIMULATION. AFTER THAT, TURN BACK TO AIMING OR SEARCHING STATE ACCORDING TO DISTANCE TO ZOMBIE
                break;
            default:
                System.out.println("Invalid State");

        }
    }
}

