/**
 *
 *
 */
public class RegularSoldier extends  Soldier{

    public RegularSoldier(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, 5.0,SoldierType.REGULAR, 2.0, 20.0, SoldierState.SEARCHING);
    }

    @Override
    public void step(SimulationController controller) {
        double distance_to_closest_zombie;
        Position zombie_position;
        Position new_direction;


        switch (this.getSoldier_state()){
            case SEARCHING:
                this.setRandomDirection(controller); // SET RANDOM DIRECTION IF THIS IS THE FIRST STEP CALL
                this.moveOrChangeDirection(controller); // CALCULATE NEXT POSITION AND TRY TO RUN , IF THE POSITION IS OUT OF BOUNDS CHANGE DIRECTION TO RARNDOM VALUE.

                distance_to_closest_zombie = calculateDistanceToEnemy(controller); // CALCULATE THE DISTANCE TO NEAREST ZOMBIE

                if(distance_to_closest_zombie <= this.getShooting_range()){ // IF THE CLOSEST DISTANCE < SHOOTING RANGE , CHANGE THE STATE AS AIMING
                    this.setSoldier_state(SoldierState.AIMING);
                    System.out.println(this.getName() + " changed state to " + this.getSoldier_state() + ".");
                }

                break;

            case AIMING:
                distance_to_closest_zombie = calculateDistanceToEnemy(controller);
                if(distance_to_closest_zombie <= this.getShooting_range()){
                    zombie_position = this.nearestEnemy(controller).getPosition();
                    new_direction = new Position(zombie_position.getX()-this.getPosition().getX(),zombie_position.getY()-this.getPosition().getY());
                    new_direction.normalize();
                    this.setSoldier_state(SoldierState.SHOOTING);
                    System.out.println(this.getName() + " changed state to " + this.getSoldier_state() + ".");
                    this.setDirection(new_direction);
                    System.out.println(this.getName() + " changed direction to " + this.getDirection() + ".");

                }
                else{
                    this.setSoldier_state(SoldierState.SEARCHING);
                    System.out.println(this.getName() + " changed state to " + this.getSoldier_state() + ".");
                }
                break;

            case SHOOTING:
                SimulationObject bullet = new RegularSoldierBullet("Bullet" + Bullet.getBullet_number(), this.getPosition(), this.getDirection());
                controller.addSimulationObject(bullet);
                System.out.println(this.getName() + " fired " + bullet.getName() + " to " + bullet.getDirection() + ".");
                distance_to_closest_zombie = calculateDistanceToEnemy(controller);
                if(distance_to_closest_zombie <= this.getShooting_range()) {
                    this.setSoldier_state(SoldierState.AIMING);
                    System.out.println(this.getName() + " changed state to " + this.getSoldier_state() + ".");
                }
                else{
                    this.setSoldier_state(SoldierState.SEARCHING);
                    System.out.println(this.getName() + " changed state to " + this.getSoldier_state() + ".");
                }
                break;
            default:
                System.out.println("Invalid State");

        }
    }
}

