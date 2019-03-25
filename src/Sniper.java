/**
 *
 *
 */
public class Sniper extends  Soldier{

    public Sniper(String name, Position position) {// DO NOT CHANGE PARAMETERS
        super(name, position, 2.0,SoldierType.SNIPER, 5.0, 40.0, SoldierState.SEARCHING);
        
    }

    @Override
    public void step(SimulationController controller) {
        switch(this.getSoldier_state()){
            case SEARCHING:
                this.setRandomDirection(controller); // SET RANDOM DIRECTION IF THIS IS THE FIRST STEP CALL
                this.moveOrChangeDirection(controller); // CALCULATE NEXT POSITION AND TRY TO RUN , IF THE POSITION IS OUT OF BOUNDS CHANGE DIRECTION TO RANDOM VALUE.
                this.setSoldier_state(SoldierState.AIMING); // CHANGE STATE TO AIMING
                System.out.println(this.getName() + " changed state to " + this.getSoldier_state() + ".");
                break;
            case AIMING:
                this.aim(controller); // CALCULATE THE EUCLIDEAN DISTANCE TO CLOSEST ZOMBIE. IF THE DISTANCE IS SHORTER THAN OR EQUAL TO THE SHOOTING RANGE OF THE SOLDIER, CHANGE SOLDIER DIRECTION TO ZOMBIE AND CHANGE STATE TO SHOOTING ELSE CHANGE STATE TO SEARCHING
                break;
            case SHOOTING:
                SimulationObject bullet = new SniperBullet("Bullet" + Bullet.getBullet_number(), this.getPosition(), this.getDirection());
                this.shoot(controller, bullet); // CREATE BULLET WITH THE POSITION AND DIRECTION OF THE SOLDIER THEN ADD IT TO SIMULATION. AFTER THAT, TURN BACK TO AIMING OR SEARCHING STATE ACCORDING TO DISTANCE TO ZOMBIE
                break;
             default:
                 System.out.println("Invalid State");
        }
    }
}
