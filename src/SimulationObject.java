/**
 *
 *
 */
public abstract class SimulationObject {
    private final String name;
    private Position position;
    private Position direction;
    private final double speed;
    private boolean active;
    public SimulationObject(String name, Position position, double speed) {
        this.name = name;
        this.position = position;
        this.speed = speed;
        this.direction = null;
        this.active = true;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getDirection() {
        return direction;
    }

    public void setDirection(Position direction) {
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    

    public abstract void step(SimulationController controller);

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

    public void turnToEnemy(SimulationObject enemy){
        Position dir;
        dir = new Position(enemy.getPosition().getX()-this.getPosition().getX(),enemy.getPosition().getY()-this.getPosition().getY());
        dir.normalize();
        this.setDirection(dir);
        System.out.println(this.getName() + " changed direction to " + this.getDirection() + ".");
    }


    public void setRandomDirection(){
        this.setDirection(Position.generateRandomDirection(true));
        System.out.println(this.getName() + " changed direction to " + this.getDirection() + ".");
    }

    public void moveOrChangeDirection(SimulationController controller){
        Position calculatedTarget = Position.calculateNextPosition(this.getPosition(), this.getDirection(), this.getSpeed());  //CALCULATE TARGET LOCATION
        boolean change_position = controller.isInTheRange(calculatedTarget.getX(),calculatedTarget.getY());  // SET CHANGE POSITION FLAG BY LOOKING THE TARGET LOCATION IS IN THE RANGE OR NOT

        if(change_position){
            this.setPosition(calculatedTarget);
            System.out.println(this.getName() + " moved to " + this.getPosition() + ".");
        }
        else{
            this.setDirection(Position.generateRandomDirection(true));
            System.out.println(this.getName() + " changed direction to " + this.getDirection() + ".");
        }
    }

}
