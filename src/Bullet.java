import javafx.geometry.Pos;

public abstract class Bullet extends SimulationObject {

    private static int bullet_number = 0;

    public Bullet(String name, Position position,Position direction, double speed) {
        super(name, position, speed);
        this.setDirection(direction);
        this.setActive(false);
        bullet_number++;
    }

    public static int getBullet_number() {
        return bullet_number;
    }

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
                System.out.println("Now the position is " + this.getPosition() + "step :" + i );
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
    public void setRandomDirection(SimulationController controller) {
        return ;
    }

    @Override
    public String toString() {
        return "Bullet{" +
                "name=" + this.getName() +
                ", position=" + this.getPosition() +
                ", speed=" + this.getSpeed() +
                ", direction=" + this.getDirection()+
                '}';
    }
}
