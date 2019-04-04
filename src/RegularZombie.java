/**
 *
 * 
 */
public class RegularZombie extends  Zombie {
    /**
     * Keeps the number of step Regular Zombie has been in FOLLOWING state
     */
    private int step_count;

    public RegularZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, 5.0 , ZombieType.REGULAR, ZombieState.WANDERING, 2.0,20.0);
        this.step_count = 0;
    }

    public int getStep_count() {
        return step_count;
    }

    public void setStep_count(int step_count) {
        this.step_count = step_count;
    }

    @Override
    public void step(SimulationController controller) {
        double distance = calculateDistanceToEnemy(controller);
        SimulationObject enemy = nearestEnemy(controller);

       if(this.tryToKill(controller,enemy,distance))
           return;

        if(this.isFirstStep()){  // SET RANDOM DIRECTION IF THIS IS THE FIRST STEP CALL
            this.setRandomDirection();
            this.setFirstStep(false);
        }

        this.moveOrChangeDirection(controller); // CALCULATE NEXT POSITION AND TRY TO RUN , IF THE POSITION IS OUT OF BOUNDS CHANGE DIRECTION TO RARNDOM VALUE.

        switch (this.getZombie_state()){
            case WANDERING:
                distance = calculateDistanceToEnemy(controller); // CALCULATE THE DISTANCE TO NEAREST SOLDIER
                    if(distance <= this.getDetection_range()){  // IF THE CLOSEST DISTANCE < DETECTION RANGE , CHANGE THE STATE AS FOLLOWING
                        this.turnToEnemy(enemy);
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
