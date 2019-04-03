/**
 * Represents the bullet of Regular Soldier
 * @author Berkant Bayraktar
 * @version 1.0
 */

public class RegularSoldierBullet extends Bullet {

    /**
     *Creates new Regular Soldier Bullet with the given name, position and direction.
     * Position and direction of the bullet should be the same position and direction of the soldier firing it.
     * Speed of the bullet should be 40.0 as default.
     * @param name Name of the Bullet
     * @param position Position of the Bullet
     * @param direction Direction of the Bullet
     */

    public RegularSoldierBullet(String name, Position position, Position direction) {
        super(name, position, direction, 40.0 );
    }
}
