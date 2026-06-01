import java.util.ArrayList;
import java.util.List;

public class MotorJuego {
    public enum EstadoJuego { MENU, JUGANDO, PAUSA, GAME_OVER }
    
    private EstadoJuego estadoActual;
    private final List<EntidadVideojuego> entidades;
    private Jugador jugadorPrincipal;

    public MotorJuego() {
        this.estadoActual = EstadoJuego.MENU;
        this.entidades = new ArrayList<>();
    }

    public void iniciarPartida() {
        if (this.estadoActual == EstadoJuego.JUGANDO) {
            System.out.println("[SISTEMA] Error: Ya hay una partida en curso.");
            return;
        }
        System.out.println("[SISTEMA] Iniciando Cyberpunk Grid Runner...");
        this.entidades.clear();
        
        // Inicialización de entidades básicas
        this.jugadorPrincipal = new Jugador("Neo_Hacker", 0, 0);
        this.entidades.add(jugadorPrincipal);
        this.entidades.add(new Enemigo("Drone_A", 4, 0));
        
        this.estadoActual = EstadoJuego.JUGANDO;
    }

    public void pausarPartida() {
        if (this.estadoActual == EstadoJuego.JUGANDO) {
            this.estadoActual = EstadoJuego.PAUSA;
            System.out.println("[SISTEMA] Partida en PAUSA.");
        }
    }

    public void reanudarPartida() {
        if (this.estadoActual == EstadoJuego.PAUSA) {
            this.estadoActual = EstadoJuego.JUGANDO;
            System.out.println("[SISTEMA] Partida REANUDADA.");
        }
    }

    public void procesarComando(String comando) {
        if (estadoActual != EstadoJuego.JUGANDO) {
            System.out.println("[SISTEMA] Comando ignorado. El juego no está activo.");
            return;
        }
        
        System.out.println("\n--- Accion de Jugador: " + comando + " ---");
        jugadorPrincipal.mover(comando);
        actualizar();
    }

    public void actualizar() {
        if (estadoActual != EstadoJuego.JUGANDO) return;

        // Evaluar la IA de los enemigos antes del ciclo general de actualizaciones
        for (EntidadVideojuego e : entidades) {
            if (e instanceof Enemigo) {
                ((Enemigo) e).evaluarIA(jugadorPrincipal);
            }
        }

        // Ciclo de Actualización estándar del Game Loop
        for (EntidadVideojuego e : entidades) {
            e.actualizar();
        }

        // Funcionalidad Avanzada 1: Detección de colisiones matemáticas AABB
        comprobarColisiones();
        
        // Verificar condición de derrota
        if (jugadorPrincipal.getSalud() <= 0) {
            estadoActual = EstadoJuego.GAME_OVER;
            System.out.println("\n=================================");
            System.out.println("  [GAME OVER] Has sido destruido ");
            System.out.println("=================================");
        }
    }

    public void comprobarColisiones() {
        // Colisión simple basada en intersección AABB (Ejes coordenados alineados)
        for (int i = 0; i < entidades.size(); i++) {
            for (int j = i + 1; j < entidades.size(); j++) {
                EntidadVideojuego e1 = entidades.get(i);
                EntidadVideojuego e2 = entidades.get(j);

                if (e1.getX() < e2.getX() + e2.getAncho() &&
                    e1.getX() + e1.getAncho() > e2.getX() &&
                    e1.getY() < e2.getY() + e2.getAlto() &&
                    e1.getY() + e1.getAncho() > e2.getY()) {
                    
                    System.out.println("[COLISIÓN] Intersección detectada entre " + e1.getNombre() + " y " + e2.getNombre());
                    
                    // Consecuencia del Impacto
                    if (e1 instanceof Jugador && e2 instanceof Enemigo) {
                        e1.recibirDanio(20);
                    } else if (e2 instanceof Jugador && e1 instanceof Enemigo) {
                        e2.recibirDanio(20);
                    }
                }
            }
        }
    }

    public EstadoJuego getEstadoActual() { return estadoActual; }
}