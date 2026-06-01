public class Enemigo extends EntidadVideojuego {
    
    public enum ComportamientoEnemigo { PATRULLAR, PERSEGUIR }
    private ComportamientoEnemigo estadoIA;
    private int direccionPatrulla = 1; // 1 = Derecha, -1 = Izquierda

    public Enemigo(String nombre, int x, int y) {
        super(nombre, x, y, 1, 1, 50);
        this.estadoIA = ComportamientoEnemigo.PATRULLAR;
    }

    public void evaluarIA(Jugador jugador) {
        int distancia = this.getDistance(jugador);
        
        // Umbral de persecución: 3 casillas
        if (distancia <= 3) {
            this.estadoIA = ComportamientoEnemigo.PERSEGUIR;
            // Lógica simple de persecución: se acerca al eje del jugador
            if (this.x < jugador.getX()) this.x++;
            else if (this.x > jugador.getX()) this.x--;
            
            if (this.y < jugador.getY()) this.y++;
            else if (this.y > jugador.getY()) this.y--;
        } else {
            this.estadoIA = ComportamientoEnemigo.PATRULLAR;
            // Patrulla horizontal simple de vaivén
            this.x += direccionPatrulla;
            if (this.x >= 5 || this.x <= 0) {
                direccionPatrulla *= -1; // Invierte dirección
            }
        }
    }

    @Override
    public void actualizar() {
        System.out.println("[LOG - Enemigo] " + nombre + " en (" + x + "," + y + ") | Modo: " + estadoIA);
    }
}