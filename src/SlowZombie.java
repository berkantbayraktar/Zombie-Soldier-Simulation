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
        double distance = calculateDistanceToEnemy(controller);
        SimulationObject enemy = nearestEnemy(controller);

        if(this.tryToKill(controller,enemy,distance))
            return;

        switch (this.getZombie_state()){
            case WANDERING:

                if(this.isFirstStep()){  // SET RANDOM DIRECTION IF THIS IS THE FIRST STEP CALL
                    this.setRandomDirection();
                    this.setFirstStep(false);
                }

                if(distance <= this.getDetection_range()){
                    this.setZombie_state(ZombieState.FOLLOWING);
                    System.out.println(this.getName() + " changed state to " + this.getZombie_state() + ".");
                    return;
                }
                else{
                    this.moveOrChangeDirection(controller);
                }
                break;

            case FOLLOWING:
                if(distance <= this.getDetection_range()){
                    this.turnToEnemy(enemy);
                }
                this.moveOrChangeDirection(controller);

                if(distance <= this.getDetection_range()){
                    this.setZombie_state(ZombieState.WANDERING);
                    System.out.println(this.getName() + " changed state to " + this.getZombie_state() + ".");
                }


                break;

            default:
                System.out.println("Invalid State");
        }


    }
}
