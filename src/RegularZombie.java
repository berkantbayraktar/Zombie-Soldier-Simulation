/**
 *
 * 
 */
public class RegularZombie extends  Zombie {

    public RegularZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, 5.0 , ZombieType.REGULAR, ZombieState.WANDERING, 2.0,20.0, 0);
    }

    @Override
    public void step(SimulationController controller) {
        double distance_to_closest_soldier = calculateDistanceToEnemy(controller);
        SimulationObject closest_soldier = nearestEnemy(controller);

       if(distance_to_closest_soldier <= this.getCollision_range() + ((Soldier)closest_soldier).getCollision_range()){
           System.out.println(this.getName() + " killed " + closest_soldier.getName() + ".");
           controller.removeSimulationObject(closest_soldier);
           return;
       }

        if(this.isFirstStep()){  // SET RANDOM DIRECTION IF THIS IS THE FIRST STEP CALL
            this.setRandomDirection();
            this.setFirstStep(false);
        }

        this.moveOrChangeDirection(controller); // CALCULATE NEXT POSITION AND TRY TO RUN , IF THE POSITION IS OUT OF BOUNDS CHANGE DIRECTION TO RARNDOM VALUE.

        switch (this.getZombie_state()){
            case WANDERING:
                distance_to_closest_soldier = calculateDistanceToEnemy(controller); // CALCULATE THE DISTANCE TO NEAREST SOLDIER
                    if(distance_to_closest_soldier <= this.getDetection_range()){  // IF THE CLOSEST DISTANCE < DETECTION RANGE , CHANGE THE STATE AS FOLLOWING
                        this.setZombie_state(ZombieState.FOLLOWING);
                        System.out.println(this.getName() + " changed state to " + this.getZombie_state() + ".");
                }
                break;

            case FOLLOWING:
                this.setStep_count(this.getStep_count()+1);

                if(this.getStep_count() == 4){
                    this.setZombie_state(ZombieState.WANDERING);
                    this.setStep_count(0);
                    System.out.println(this.getName() + " changed state to " + this.getZombie_state() + ".");
                }

                break;
            default:
                System.out.println("Invalid State");
        }


    }
    
}
