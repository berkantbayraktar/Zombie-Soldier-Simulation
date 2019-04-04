/**
 * <b> Bullet </b> represents the parent class of all the bullet types derived from.
 * Each type of the bullet derived from this base class.
 * @author Berkant Bayraktar
 * @version 1.0
 */


public abstract class Bullet extends SimulationObject {

    /**
     * Keeps the number of bullets. At the beginning, it is initially zero.
     * It is incremented by one for the each bullet creation.
     */

    private static int bullet_number = 0;

    public Bullet(String name, Position position,Position direction, double speed) {
        super(name, position, speed);
        this.setDirection(direction);
        this.setActive(false);
        bullet_number++;
    }

    /**
     * Gets the number of bullet.
     * @return A integer representing the number of bullet
     */
    public static int getBullet_number() {
        return bullet_number;
    }

    /**
     * <p> Each type of the bullet derived from the Bullet class uses this method to be able to act in the simulation. </p>
     * <p> Since the bullets move fastest in the simulation, calculating the next position of the bullet directly is not a good approach and result in wrong conclusion. </p>
     *
     * <p> The following algorithm is implemented step by step to be able to achieve the correct result.</p>
     * <p> 1- Cast the speed from double to integer to calculate the N small steps</p>
     * <p> 2- For every small steps between [0;N):
     * <ul>
     *     <li> Calculate the euclidean distance to the closest zombie using bullet’s and zombie’s position. </li>
     *     <li> Formula: sqrt((obj_x1 - obj_x0)^2 + (obj_y1 - obj_y0)^2)  </li>
     *     <li> If the distance is smaller than or equal to the collision distance of the zombie, remove the
     *          zombie and the bullet from the simulation and exit loop. </li>
     *     <li> Calculate the next position of the bullet with formulate: position = position + direction * 1:0</li>
     *     <li> If the bullet moved out of bound, exit loop.</li>
     * </ul>
     * <p> This method prints following three type of information to the stdout depends on the situation</p>
     * <p> 1- When bullet collides with a zombie:</p>
     * <ul>
     *     <li> &lt;bullet_name&gt; hit &lt;zombie_name&gt;.&lt;newline&gt;</li>
     *     <li> For example:</li>
     *     <li> Bullet0 hit Zombie1.</li>
     * </ul>
     * <p> 2- When bullet moves out of simulation bounds:</p>
     * <ul>
     *     <li> &lt;bullet_name&gt; moved out of bounds.&lt;newline&gt;</li>
     *     <li> For example:</li>
     *     <li> Bullet0 moved out of bounds. </li>
     * </ul>
     * <p> 3- When bullet completes its step without going out of bounds or hitting a zombie:</p>
     * <ul>
     *     <li> &lt;bullet_name&gt; dropped to the ground at &lt;bullet_position&gt;.&lt;newline&gt;</li>
     *     <li> For example:</li>
     *     <li> Bullet0 dropped to the ground at (12.37, 34.43). </li>
     * </ul>
     *
     *
     *
     * @param controller Simulation controller
     */

    @Override
    public void step(SimulationController controller) {
        int step = (int) this.getSpeed();
        double distance;
        SimulationObject nearest_enemy;
        nearest_enemy = this.nearestEnemy(controller);
        Position next_position;

        for(int i = 0 ; i < step ; i++){
            distance = calculateDistanceToEnemy(controller);
            if(distance <= ((Zombie)nearest_enemy).getCollision_range()){
                controller.removeSimulationObject(nearest_enemy);
                controller.removeSimulationObject(this);
                this.setActive(false);
                System.out.println(this.getName() + " hit " + nearest_enemy.getName() + ".");
                break;
            }

            next_position = Position.calculateNextPosition(this.getPosition(), this.getDirection(), 1.0);
            boolean change_position = controller.isInTheRange(next_position.getX(),next_position.getY());
            if(change_position){
                this.setPosition(next_position);
            }
            else{
                controller.removeSimulationObject(this);
                this.setActive(false);
                System.out.println(this.getName() + " moved out of bounds.");
                break;
            }


        }
        if(isActive()){
            controller.removeSimulationObject(this);
            System.out.println(this.getName() + " dropped to ground at " + this.getPosition() + ".");
        }

    }
}
