public abstract class Zombie extends  SimulationObject {
    private final ZombieType zombie_type;
    private ZombieState zombie_state;
    private final double collision_range;
    private final double detection_range;
    private int step_count;
    private boolean isFirstStep;

    public Zombie(String name, Position position, double speed, ZombieType zombie_type, ZombieState zombie_state, double collision_range, double detection_range, int step_count) {
        super(name, position, speed);
        this.zombie_type = zombie_type;
        this.zombie_state = zombie_state;
        this.collision_range = collision_range;
        this.detection_range = detection_range;
        this.step_count = step_count;
        this.isFirstStep = true;
    }

    public ZombieState getZombie_state() {
        return zombie_state;
    }

    public void setZombie_state(ZombieState zombie_state) {
        this.zombie_state = zombie_state;
    }

    public ZombieType getZombie_type() {
        return zombie_type;
    }

    public double getCollision_range() {
        return collision_range;
    }

    public double getDetection_range() {
        return detection_range;
    }

    public int getStep_count() {
        return step_count;
    }

    public void setStep_count(int step_count) {
        this.step_count = step_count;
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
        for(int i = 0 ; i < controller.getSoldiers().size() ; i++) {
            if(this.getPosition().distance(controller.getSoldiers().get(i).getPosition()) < distance)
                distance = this.getPosition().distance(controller.getSoldiers().get(i).getPosition());
        }
        return distance;
    }

    @Override
    public SimulationObject nearestEnemy(SimulationController controller) {
        double distance_to_nearest_enemy = calculateDistanceToEnemy(controller);
        for(int i = 0 ; i < controller.getSoldiers().size() ; i++){
            if(this.getPosition().distance(controller.getSoldiers().get(i).getPosition()) == distance_to_nearest_enemy)
                return controller.getSoldiers().get(i);
        }
        return null;
    }

    @Override
    public void setRandomDirection(SimulationController controller) {
        if(isFirstStep()){
            this.setDirection(Position.generateRandomDirection(true));
            this.setFirstStep(false);
            System.out.println(this.getName() + " changed direction to " + this.getDirection() + ".");
        }
    }

    @Override
    public String toString() {
        return "Zombie{" +
                "name=" + this.getName() +
                ", position=" + this.getPosition() +
                ", speed=" + this.getSpeed() +
                ", zombie_type=" + zombie_type +
                ", zombie_state=" + zombie_state +
                ", collison_range=" + collision_range +
                ", detection_range=" + detection_range +
                ", direction=" + this.getDirection() +
                '}';
    }
}
