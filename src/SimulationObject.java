/**
 * SimulationObject represents the  parent class of Soldier, Zombie and Bullet classes. It encapsulates the common feature of these classes.
 * @author Berkant Bayraktar
 * @version 1.0
 */
public abstract class SimulationObject {

    /**
     * name of the simulation object
     */
    private final String name;
    /**
     * position of the simulation object
     */
    private Position position;
    /**
     * direction of the simulation object
     */
    private Position direction;
    /**
     * speed of the simulation object
     */
    private final double speed;
    /**
     * a Flag which holds the information whether the simulation object is Active or not
     */
    private boolean active;

    /**
     * Creates a SimulationObject object with the given parameters. By default, direction is set null and all objects start simulation as Active.
     * @param name name of the simulation object
     * @param position position of the simulation object
     * @param speed speed of the simulation object
     */

    public SimulationObject(String name, Position position, double speed) {
        this.name = name;
        this.position = position;
        this.speed = speed;
        this.direction = null;
        this.active = true;
    }

    /**
     * Gets name of the simulation object
     * @return a String which holds name of the object
     */

    public String getName() {
        return name;
    }

    /**
     * Gets position of the simulation object
     * @return a Position which holds the position of the object.
     */

    public Position getPosition() {
        return position;
    }

    /**
     * Sets position of the simulation object with the given parameter
     * @param position Position to be set
     */

    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Gets direction of the simulation object
     * @return a Position which holds the direction of the object.
     */

    public Position getDirection() {
        return direction;
    }

    /**
     * Sets direction of the simulation object with the given parameter.
     * @param direction Direction to be set
     */

    public void setDirection(Position direction) {
        this.direction = direction;
    }

    /**
     * Gets speed of the simulation object
     * @return a Double which holds speed of the object.
     */

    public double getSpeed() {
        return speed;
    }

    /**
     * Gets the information about whether the object is active or not
     * @return the information about whether the object is active or not
     */

    public boolean isActive() {
        return active;
    }

    /**
     * Sets the object as Active or Inactive
     * @param active if the simulation object will be set as Active, it should be True . If not, False
     */

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Each SimulationObject uses this method to be able to act in the simulation.
     * @param controller Simulation Controller
     */
    

    public abstract void step(SimulationController controller);

    /**
     * Gets the distance to the nearest enemy
     * @param controller Simulation Controller
     * @return the distance to the nearest enemy
     */

    public double calculateDistanceToEnemy(SimulationController controller){
        double distance = Double.MAX_VALUE;

        if(this.getClass().getSuperclass().getName().equals("Soldier") || this.getClass().getSuperclass().getName().equals("Bullet")){
            for(int i = 0 ; i < controller.getZombies().size() ; i++) {
                if(this.getPosition().distance(controller.getZombies().get(i).getPosition()) < distance)
                    distance = this.getPosition().distance(controller.getZombies().get(i).getPosition());
            }

        }
        else if(this.getClass().getSuperclass().getName().equals("Zombie")){
            for(int i = 0 ; i < controller.getSoldiers().size() ; i++) {
                if(this.getPosition().distance(controller.getSoldiers().get(i).getPosition()) < distance)
                    distance = this.getPosition().distance(controller.getSoldiers().get(i).getPosition());
            }

        }
        else {
            System.out.println("Invalid Class");
        }

        return distance;
    }

    /**
     * Gets the nearest enemy object
     * @param controller Simulation Controller
     * @return the nearest enemy object
     */

    public SimulationObject nearestEnemy(SimulationController controller){
        double distance_to_nearest_enemy;
        if(this.getClass().getSuperclass().getName().equals("Soldier") || this.getClass().getSuperclass().getName().equals("Bullet")){
            distance_to_nearest_enemy = calculateDistanceToEnemy(controller);
            for(int i = 0 ; i < controller.getZombies().size() ; i++){
                if(this.getPosition().distance(controller.getZombies().get(i).getPosition()) == distance_to_nearest_enemy)
                    return controller.getZombies().get(i);
            }
        }
        else if(this.getClass().getSuperclass().getName().equals("Zombie")){
            distance_to_nearest_enemy = calculateDistanceToEnemy(controller);
            for(int i = 0 ; i < controller.getSoldiers().size() ; i++){
                if(this.getPosition().distance(controller.getSoldiers().get(i).getPosition()) == distance_to_nearest_enemy)
                    return controller.getSoldiers().get(i);
            }
        }
        else
            System.out.println("Invalid Class");

        return null;
    }

    /**
     * Changes simulation object direction to enemy and prints the following information to stdout
     * <ul>
     *      <li> &lt;object_name&gt; changed direction to &lt;direction&gt;.&lt;newline&gt;</li>
     *      <li> For example:</li>
     *      <li> Soldier1 changed direction to (0.33, -0.94). </li>
     * </ul>
     * @param enemy Enemy object
     */

    public void turnToEnemy(SimulationObject enemy){
        Position dir;
        dir = new Position(enemy.getPosition().getX()-this.getPosition().getX(),enemy.getPosition().getY()-this.getPosition().getY());
        dir.normalize();
        this.setDirection(dir);
        System.out.println(this.getName() + " changed direction to " + this.getDirection() + ".");
    }

    /**
     * Generates random direction and sets it . Also prints the following information to stdout.
     * <ul>
     *      <li> &lt;object_name&gt; changed direction to &lt;direction&gt;.&lt;newline&gt;</li>
     *      <li> For example:</li>
     *      <li> Soldier1 changed direction to (0.33, -0.94). </li>
     * </ul>
     */

    public void setRandomDirection(){
        this.setDirection(Position.generateRandomDirection(true));
        System.out.println(this.getName() + " changed direction to " + this.getDirection() + ".");
    }

    /**
     * Calculate the next position of the object
     * If the position is out of bounds, changes direction to random value and prints the following information to stdout
     * <ul>
     *      <li> &lt;object_name&gt; changed direction to &lt;direction&gt;.&lt;newline&gt;</li>
     *      <li> For example:</li>
     *      <li> Soldier1 changed direction to (0.33, -0.94). </li>
     * </ul>
     * If the position is not out of bounds, changes position of the object to the new_position. Then, prints the following information to stdout
     * <ul>
     *      <li> &lt;object_name&gt; moved to &lt;position&gt;.&lt;newline&gt;</li>
     *      <li> For example:</li>
     *      <li> Soldier1 moved to (12.37, 34.43). </li>
     * </ul>
     * @param controller Simulation Controller
     */

    public void moveOrChangeDirection(SimulationController controller){
        Position nextPosition = Position.calculateNextPosition(this.getPosition(), this.getDirection(), this.getSpeed());  //CALCULATE TARGET LOCATION
        boolean changePosition = controller.isInTheRange(nextPosition.getX(),nextPosition.getY());  // SET CHANGE POSITION FLAG BY LOOKING THE TARGET LOCATION IS IN THE RANGE OR NOT

        if(changePosition){
            this.setPosition(nextPosition);
            System.out.println(this.getName() + " moved to " + this.getPosition() + ".");
        }
        else{
            this.setDirection(Position.generateRandomDirection(true));
            System.out.println(this.getName() + " changed direction to " + this.getDirection() + ".");
        }
    }

}
