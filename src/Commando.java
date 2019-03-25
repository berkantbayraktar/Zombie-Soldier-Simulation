/**
 *
 *
 */
public class Commando extends Soldier{

    private boolean shouldReturn;

    public Commando(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, 10.0,SoldierType.COMMANDO, 2.0, 10.0, SoldierState.SEARCHING);
        this.shouldReturn = false;
    }

    public boolean isShouldReturn() {
        return shouldReturn;
    }

    public void setShouldReturn(boolean shouldReturn) {
        this.shouldReturn = shouldReturn;
    }

    @Override
    public void step(SimulationController controller) {
        switch (this.getSoldier_state()){
            case SEARCHING:
                this.setRandomDirection(controller); // SET RANDOM DIRECTION IF THIS IS THE FIRST STEP CALL

                this.aim(controller); // CALCULATE THE EUCLIDEAN DISTANCE TO CLOSEST ZOMBIE. IF THE DISTANCE IS SHORTER THAN OR EQUAL TO THE SHOOTING RANGE OF THE SOLDIER, CHANGE SOLDIER DIRECTION TO ZOMBIE AND CHANGE STATE TO SHOOTING

                if(this.isShouldReturn()) { // RETURN IMMEDIATELY , IF THE DISTANCE IS SHORTER THAN OR EQUAL TO THE SHOOTING RANGE. NOTE THAT shouldReturn flag is set in aim() function.
                    this.setShouldReturn(false);
                    return;
                }

                this.moveOrChangeDirection(controller); // CALCULATE NEXT POSITION AND TRY TO RUN , IF THE POSITION IS OUT OF BOUNDS CHANGE DIRECTION TO RANDOM VALUE.

                this.aim(controller); // CALCULATE THE EUCLIDEAN DISTANCE TO CLOSEST ZOMBIE. IF THE DISTANCE IS SHORTER THAN OR EQUAL TO THE SHOOTING RANGE OF THE SOLDIER, CHANGE SOLDIER DIRECTION TO ZOMBIE AND CHANGE STATE TO SHOOTING

                break;
            case SHOOTING:
                break;
            default:
                System.out.println("Invalid State");
        }
    }


    @Override
    public void aim(SimulationController controller) {
        Position zombie_position;
        Position new_direction;
        double distance_to_closest_zombie;

        distance_to_closest_zombie = calculateDistanceToEnemy(controller);

        if(distance_to_closest_zombie <= this.getShooting_range()){
            zombie_position = this.nearestEnemy(controller).getPosition();
            new_direction = new Position(zombie_position.getX()-this.getPosition().getX(),zombie_position.getY()-this.getPosition().getY());
            new_direction.normalize();
            this.setSoldier_state(SoldierState.SHOOTING);
            System.out.println(this.getName() + " changed state to " + this.getSoldier_state() + ".");
            this.setDirection(new_direction);
            System.out.println(this.getName() + " changed direction to " + this.getDirection() + ".");
            this.setShouldReturn(true);
        }
    }
}
