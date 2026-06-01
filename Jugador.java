public class Jugador extends EntidadVideojuego {

    public Jugador(String nombre, int x, int y) {
        // Nombre, x, y, ancho, alto, salud_inicial
        super(nombre, x, y, 1, 1, 100);
    }

    public void mover(String direccion) {
        switch (direccion.toUpperCase()) {
            case "ARRIBA":    y--; break;
            case "ABAJO":     y++; break;
            case "IZQUIERDA": x--; break;
            case "DERECHA":   x++; break;
            default:
                System.out.println("[INPUT] Comando de movimiento no reconocido: " + direccion);
        }
    }

    @Override
    public void actualizar() {
        // En lógica por comandos, el estado del jugador se actualiza tras los inputs
        System.out.println("[LOG - Jugador] " + nombre + " se encuentra en la posición (" + x + "," + y + ") | HP: " + salud);
    }
}
