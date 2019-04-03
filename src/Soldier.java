public abstract class Soldier extends SimulationObject {
    private final SoldierType soldier_type;
    private final double collision_range;
    private final double shooting_range;
    private  SoldierState soldier_state;
    private boolean isFirstStep;


    public Soldier(String name, Position position, double speed, SoldierType soldier_type, double collision_range, double shooting_range, SoldierState soldier_state) {
        super(name, position, speed);
        this.soldier_type = soldier_type;
        this.collision_range = collision_range;
        this.shooting_range = shooting_range;
        this.soldier_state = soldier_state;
        this.isFirstStep = true;
    }

    public SoldierState getSoldier_state() {
        return soldier_state;
    }

    public void setSoldier_state(SoldierState soldier_state) {
        this.soldier_state = soldier_state;
    }

    public double getShooting_range() {
        return shooting_range;
    }

    public SoldierType getSoldier_type() {
        return soldier_type;
    }

    public double getCollision_range() {
        return collision_range;
    }

    public boolean isFirstStep() {
        return isFirstStep;
    }

    public void setFirstStep(boolean firstStep) {
        isFirstStep = firstStep;
    }


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



    public void shoot(SimulationController controller, SimulationObject bullet){
        double distance_to_closest_zombie;

        controller.addSimulationObject(bullet);
        System.out.println(this.getName() + " fired " + bullet.getName() + " to direction " + bullet.getDirection() + ".");
        distance_to_closest_zombie = calculateDistanceToEnemy(controller);
        if(distance_to_closest_zombie <= this.getShooting_range()) {
            this.setGivenState(SoldierState.AIMING);
        }
        else{
            this.setGivenState(SoldierState.SEARCHING);
        }
    }

    public void setGivenState(SoldierState state){
        this.setSoldier_state(state); // CHANGE SOLDIER
        System.out.println(this.getName() + " changed state to " + this.getSoldier_state() + ".");
    }

    @Override
    public String toString() {
        return "Soldier{" +
                "name=" + this.getName() +
                ", position=" + this.getPosition() +
                ", speed=" + this.getSpeed() +
                ", soldier_type=" + soldier_type +
                ", collision_range=" + collision_range +
                ", shooting_range=" + shooting_range +
                ", soldier_state=" + soldier_state +
                ", direction=" + this.getDirection()+
                '}';
    }
}
