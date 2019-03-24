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

       if(this.isFirstStep()){
           this.setDirection(Position.generateRandomDirection(true));
           this.setFirstStep(false);
           System.out.println(this.getName() + " changed direction to " + this.getDirection() + ".");
       }

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
