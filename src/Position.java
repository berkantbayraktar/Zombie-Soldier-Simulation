import java.util.Random;

/**
 *
 *
 */
public class Position {
    private double x;
    private double y;
    
    private double length;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
        
        this.calculateLength();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distance(Position other) {
        return Math.sqrt(Math.pow(this.x-other.getX(), 2)+Math.pow(this.y-other.getY(), 2));
    }
    
    public void add(Position other) {
        this.x += other.x;
        this.y += other.y;
        
        this.calculateLength();
    }
    
    public void mult(double constant) {
        this.x *= constant;
        this.y *= constant;
        
        this.calculateLength();
    }

    private void calculateLength() {
        this.length = Math.sqrt(Math.pow(x, 2.0)+Math.pow(y, 2.0));
    }
    
    public void normalize() {
        this.x /= this.length;
        this.y /= this.length;
        
        this.length = 1.0;
    }

    /**
     * Generates random direction for the Simulation object. If the parameters is set True, the direction will be normalized. If not, the direction will not be normalized.
     * @param normalize If the direction to be calculated is to be normalized, it must be set to True.
     * @return random direction for the Simulation object.
     */
    
    public static Position generateRandomDirection(boolean normalize) {
        Random random = new Random();
        double x = -1+random.nextDouble()*2;
        double y = -1+random.nextDouble()*2;
        
        Position result = new Position(x, y);
        if (normalize)
            result.normalize();
        return result;
    }

    /**
     * Calculates the next position of the Simulation Object
     * @param pos Position of the Simulation Object
     * @param direction Direction of the Simulation Object
     * @param speed Speed of the Simulation Object
     * @return the next position of the Simulation Object
     */

    public static Position calculateNextPosition(Position pos, Position direction, double speed){
        Position target = new Position(direction.getX(),direction.getY());
        target.mult(speed);
        target.add(pos);
        return target;
    }
    
    @Override
    protected Object clone() {
        return new Position(x, y); 
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }
    
}
