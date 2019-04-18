package vk;
import robocode.*;
import robocode.util.Utils;
import java.awt.Color;
import java.util.Random;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * BailarinaTres - a robot by Victor Koehler
 */
public class BailarinaTres extends Robot
{
	private boolean atirando; // Controla os estados atirando/girando do robô
	private boolean fugindo; // Se o robô está fugindo
	private int contaPaciencia = 0; // Contador de tiros
	
	/**
	 * run: Comportamento de BailarinaTres
	 */
	public void run() {

		setColors(Color.red,Color.red,Color.yellow); // Definimos as cores (chassis, arma e radar)

		atirando = false;
		fugindo = false;
		scanLoop(); // Loop principal
	}
	
	/**
	 * Nosso robô está sempre entre três estados definidos por "atirando" e "fugindo".
	 * Esse método cuida do estado "Girar"
	 */
	private void scanLoop() {
		while(true) {
			if (!atirando && !fugindo) {
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
		// Se estamos fugindo, vamos ignorar o inimigo.
		if (fugindo) {
			return;
		}

		// Detectamos um inimigo!
		// Nesse momento, alteramos o estado do robô para "Atirar"
		atirando = true;

		// Usamos algumas funções para reajustar a mira
		double turnGunAmt = Utils.normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());
		turnGunRight(turnGunAmt);

		fire(4); // Simplesmente atiramos
		scan(); // Solicitamos um novo escaneamento
		
		atirando = false; // Se chegamos aqui, então voltamos ao estado "Girar"
	}
	
	/**
	 * Quando somos atingidos por um disparo
	 */
	public void onHitByBullet(HitByBulletEvent event) {
		// Fugir para cada tiro que recebe parece ser uma má ideia
		if (contaPaciencia < 3) {
			contaPaciencia++;
			return;
		}
		
		// Estamos oficialmente fugindo!
		fugindo = true;
		contaPaciencia = 0;
		
		// Tomemos uma direção e distância aleatórias!
		Random rand = Utils.getRandom();
		double distance = rand.nextDouble() * 250 + 175;
		double angle = rand.nextDouble() * 115 + 40;
		
		if (rand.nextBoolean()) {
			angle *= -1.0;
		}

		turnRight(angle);
		ahead(distance);
		turnGunLeft(angle);
		
		// Depois de fugir, voltamos ao estado normal
		fugindo = false;
		atirando = false;
		scan();
	}
	
	/**
	 * Agora que nosso robô pode se mover, precisamos de uma detecção contra o muro.
	 */
	public void onHitWall(HitWallEvent event) {
		stop(); // Para tudo o que está fazendo
		turnRight(-event.getBearing()); // Gira para o lado oposto
		ahead(300); // Se afasta do muro

		// Voltamos ao estado normal
		fugindo = false;
		atirando = false;
		scan();
	}
}
