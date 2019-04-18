package vk;
import robocode.*;
import robocode.util.Utils;
import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * BailarinaUm - a robot by Victor Koehler
 */
public class BailarinaUm extends Robot
{
	private boolean atirando; // Controla os estados do robô

	/**
	 * run: Comportamento de BailarinaUm
	 */
	public void run() {

		setColors(Color.red,Color.red,Color.blue); // Definimos as cores (chassis, arma e radar)

		atirando = false;
		scanLoop(); // Loop principal
	}
	
	/**
	 * Nosso robô está sempre entre dois estados definidos por "atirando".
	 * Esse método cuida do estado "Girar"
	 */
	private void scanLoop() {
		while(true) {
			if (!atirando) {
				// Nosso estado agora é "Girar"
				turnGunRight(360); // Então vamos girar a arma.
			}
			
			// Se não estamos no estado "Girar", então apenas esperamos
		}
	}

	/**
	 * Quando nosso radar escaneia algum robô inimigo.
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Detectamos um inimigo!
		// Nesse momento, alteramos o estado do robô para "Atirar"
		atirando = true;

		// Usamos algumas funções para reajustar a mira
		double turnGunAmt = Utils.normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());
		turnGunRight(turnGunAmt);

		// Entramos no estado "Atirar".
		// Sairemos apenas quando onBulletMissed for acionado.
		while(atirando) {
			fire(2); // Então atiramos.
		}
	}
	
	/**
	 * Em algum momento, nosso tiro atingiu o muro, o que signifca que erramos.
	 * Ou seja, nosso inimigo não está mais na linha de tiro.
	 */
	public void onBulletMissed(BulletMissedEvent event) {
		// Erramos o tiro? Volta para o estado "Girar" para encontrar o adversário!
		atirando = false;
	}
}
