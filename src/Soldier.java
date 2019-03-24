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

    @Override
    public double calculateDistanceToEnemy(SimulationController controller) {
        double distance = Double.MAX_VALUE;
        for(int i = 0 ; i < controller.getZombies().size() ; i++) {
            if(this.getPosition().distance(controller.getZombies().get(i).getPosition()) < distance)
                distance = this.getPosition().distance(controller.getZombies().get(i).getPosition());
        }
        return distance;
    }

    @Override
    public SimulationObject nearestEnemy(SimulationController controller) {
        double distance_to_nearest_enemy = calculateDistanceToEnemy(controller);
        for(int i = 0 ; i < controller.getZombies().size() ; i++){
            if(this.getPosition().distance(controller.getZombies().get(i).getPosition()) == distance_to_nearest_enemy)
                return controller.getZombies().get(i);
        }
        return null;
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