package vk;
import robocode.*;
import java.awt.Color;
import java.util.Random;
import robocode.util.Utils;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * FujaoUm - a robot by VictorKoehler
 */
public class FujaoUm extends Robot
{
	/**
	 * run: Comportamento padrão de FujaoUm
	 */
	public void run() {
		setColors(Color.yellow,Color.yellow,Color.blue); // Definimos as cores (chassis, arma e radar)
		
		mainLoop();
	}

	/**
	 * Nosso loop principal
	 */
	private void mainLoop() {
		while (true) {
			// Algumas variáveis de movimento.
			Random rand = Utils.getRandom(); // Gerador de valores aleatórios
			double height = getBattleFieldHeight(); // Largura do campo de batalha
			double width = getBattleFieldWidth(); // Comprimento do campo de batalha
			double distance = rand.nextDouble() * 600 + 150; // Distância a ser percorrida
			double angle = rand.nextDouble() * 60 + 30; // Ângulo da rotação
			
			if (rand.nextBoolean()) {
				angle *= -1.0; // Se vamos girar para o lado contrário
			}
			if (rand.nextBoolean()) {
				distance *= -1.0; // Se vamos andar para o lado contrário
			}
			
			turnRight(angle); // Primeiro rotacionamos o tanque
			scan(); // Escaneamos para verificar a ocorrência de um inimigo na direção
			ahead(distance); // Depois andamos para frente (para trás caso distance < 0)
			turnGunLeft(15); // Uma leve rotação para forçar o escaneamento de inimigos.
		}
	}
	

	/**
	 * onScannedRobot: Um robô foi detectado pelo radar
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Vamos simplesmente efetuar um disparo em sua direção
		fire(4);
		scan(); // Escaneamos para verificar a ocorrência de um inimigo na direção
	}

	/**
	 * onHitByBullet: Tanque atingido por um disparo!
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Recuar!
		back(400);
	}
	
	/**
	 * onHitWall: O veículo atingiu um muro enquanto movimentava-se.
	 */
	public void onHitWall(HitWallEvent e) {
		turnRight(-e.getBearing()); // Gira para o lado oposto
		ahead(300); // Se afasta do muro
	}
}
