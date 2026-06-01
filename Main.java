public class Main {
    public static void main(String[] args) {
        MotorJuego motor = new MotorJuego();

        // 1. Intentar simular sin arrancar
        motor.procesarComando("DERECHA");

        // 2. Arrancar partida
        motor.iniciarPartida();

        // 3. Simular secuencia de inputs táctiles en la cuadrícula
        motor.procesarComando("DERECHA");   // El enemigo patrulla en dirección opuesta o se acerca
        motor.procesarComando("DERECHA");   // El jugador se mueve a X=2. El enemigo entra en rango de IA
        motor.procesarComando("DERECHA");   // Colisión inminente o intercepción en cuadrícula

        // 4. Forzar pausa y testear bloqueo de inputs
        motor.pausarPartida();
        motor.procesarComando("ARRIBA");
        
        // 5. Reanudar y continuar hasta recibir daño letal (simulación)
        motor.reanudarPartida();
        motor.procesarComando("ABAJO");
        motor.procesarComando("DERECHA");
        motor.procesarComando("DERECHA");
    }
}
