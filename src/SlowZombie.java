/**
 *
 *
 */
public class SlowZombie extends  Zombie{

    public SlowZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name,position, 2.0, ZombieType.SLOW, ZombieState.WANDERING, 1.0, 40.0, 0);
    }

    @Override
    public void step(SimulationController controller) {
        double distance_to_closest_soldier = calculateDistanceToEnemy(controller);
        SimulationObject closest_soldier = nearestEnemy(controller);
        Position new_direction;

        if(distance_to_closest_soldier <= this.getCollision_range() + ((Soldier)closest_soldier).getCollision_range()){
            System.out.println(this.getName() + " killed " + closest_soldier.getName() + ".");
            controller.removeSimulationObject(closest_soldier);
            return;
        }

        switch (this.getZombie_state()){
            case WANDERING:

                if(this.isFirstStep()){  // SET RANDOM DIRECTION IF THIS IS THE FIRST STEP CALL
                    this.setRandomDirection();
                    this.setFirstStep(false);
                }

                if(distance_to_closest_soldier <= this.getDetection_range()){
                    this.setZombie_state(ZombieState.FOLLOWING);
                    System.out.println(this.getName() + " changed state to " + this.getZombie_state() + ".");
                    return;
                }
                else{
                    this.moveOrChangeDirection(controller);
                }
                break;

            case FOLLOWING:
                if(distance_to_closest_soldier <= this.getDetection_range()){
                    new_direction = new Position(closest_soldier.getPosition().getX()-this.getPosition().getX(),closest_soldier.getPosition().getY()-this.getPosition().getY());
                    new_direction.normalize();
                    this.setDirection(new_direction);
                    System.out.println(this.getName() + " changed direction to " + this.getDirection() + ".");
                }
                this.moveOrChangeDirection(controller);

                if(distance_to_closest_soldier <= this.getDetection_range()){
                    this.setZombie_state(ZombieState.WANDERING);
                    System.out.println(this.getName() + " changed state to " + this.getZombie_state() + ".");
                }


                break;

            default:
                System.out.println("Invalid State");
        }


    }
}
