package vk;
import robocode.*;
import java.awt.Color;
import java.util.Random;
import robocode.util.Utils;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * FujaoUm - a robot by (your name here)
 */
public class FujaoDois extends Robot
{
	/**
	 * run: FujaoUm's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		setColors(Color.yellow,Color.yellow,Color.blue); // body,gun,radar
		
		while (true) {
			System.out.println("Novo destino!");
			
			Random rand = Utils.getRandom();
			double height = getBattleFieldHeight();
			double width = getBattleFieldWidth();
			double distance = rand.nextDouble() * 600 + 150;
			double angle = rand.nextDouble() * 60 + 30;
			
			if (rand.nextBoolean()) {
				angle *= -1.0;
			}
			if (rand.nextBoolean()) {
				distance *= -1.0;
			}
			
			turnRight(angle);
			ahead(distance);
			turnGunRight(15);
		}
	}
	
	//private boolean interrompe = true;

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		fire(3);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		stop();
		double turnGunAmt = Utils.normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());
		turnGunRight(turnGunAmt);
		scan();
		back(400);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		turnRight(-e.getBearing());
		ahead(300);
	}	
}
