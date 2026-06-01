public abstract class EntidadVideojuego {
    protected int x;
    protected int y;
    protected int ancho;
    protected int alto;
    protected String nombre;
    protected int salud;

    public EntidadVideojuego(String nombre, int x, int y, int ancho, int alto, int salud) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.salud = salud;
    }

    public abstract void actualizar();

    // Distancia Manhattan para lógicas en cuadrícula
    public int getDistance(EntidadVideojuego otra) {
        return Math.abs(this.x - otra.getX()) + Math.abs(this.y - otra.getY());
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public int getSalud() { return salud; }
    public void setSalid(int salud) { this.salud = salud; }
    
    public void recibirDanio(int cantidad) {
        this.salud -= cantidad;
        if (this.salud < 0) this.salud = 0;
    }
}
