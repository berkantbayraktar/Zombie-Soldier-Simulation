
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 *
 */
public class SimulationRunner {

    public static void main(String[] args) {
        SimulationController simulation = new SimulationController(50, 50);
        SimulationObject obj1 = new RegularSoldier("Soldier1", new Position(10, 10));
        SimulationObject obj2 = new RegularZombie("Zombie1", new Position(40, 40));

        simulation.addSimulationObject(obj1);
        simulation.addSimulationObject(obj2);

        //System.out.println(simulation);
        while (!simulation.isFinished()) {
            simulation.stepAll();
            System.out.println(simulation);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SimulationRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
