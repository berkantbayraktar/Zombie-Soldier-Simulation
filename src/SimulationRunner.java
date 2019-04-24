
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 *
 */
public class SimulationRunner {

    public static void main(String[] args) {
        SimulationController simulation = new SimulationController(50, 50);


        simulation.addSimulationObject(new RegularSoldier("Soldier1", new Position(10, 10)));
        simulation.addSimulationObject(new RegularZombie("Zombie1", new Position(20, 20)));
        simulation.addSimulationObject(new Commando("Soldier2", new Position(15, 15)));
        simulation.addSimulationObject(new SlowZombie("Zombie2", new Position(20, 20)));
        simulation.addSimulationObject(new Sniper("Soldier3", new Position(5, 5)));
        simulation.addSimulationObject(new FastZombie("Zombie3", new Position(40, 40)));


        while (!simulation.isFinished()) {
            simulation.stepAll();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SimulationRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
