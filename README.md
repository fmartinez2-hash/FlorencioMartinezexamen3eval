# FlorencioMartinezexamen3eval
# Cyberpunk Grid Runner - Motor de Videojuego 2D Base

## 1. Temática Elegida
**Cyberpunk Grid Runner** es una simulación de lógica interna para un juego táctico en cuadrícula 2D. El jugador controla a un "Hacker" que debe evadir a los "Drones de Seguridad" (NPCs con IA de patrulla y persecución) mientras se desplaza por el entorno. El objetivo del motor es procesar el movimiento, gestionar las colisiones en la cuadrícula y coordinar los estados globales del juego (Menú, Jugando, Pausa, Game Over).

---

## 2. Arquitectura del Software
El sistema se ha diseñado bajo un enfoque minimalista de Programación Orientada a Objetos (POO), limitando el acoplamiento y encapsulando la lógica en 5 clases esenciales:

* **`Main`**: Actúa como el controlador de la simulación. Reemplaza la interfaz gráfica ejecutando un bucle de juego interactivo por consola que inyecta comandos del usuario.
* **`MotorJuego`**: El núcleo central. Gestiona el ciclo de vida del juego (`EstadoJuego`), la lista activa de entidades y expone los métodos públicos para alterar el entorno.
* **`EntidadVideojuego`**: Clase abstracta que define las propiedades comunes de cualquier objeto en el espacio bidimensional ($x, y, w, h$), su estado vital y su representación visual abstracta.
* **`Jugador`** (Hereda de `EntidadVideojuego`): Añade la gestión de energía y la capacidad de interactuar directamente con comandos de movimiento de la cuadrícula.
* **`Enemigo`** (Hereda de `EntidadVideojuego`): Integra la lógica de comportamiento móvil (Estados: `PATRULLAR`, `PERSEGUIR`). Cambia su estado dinámicamente según la distancia Manhattan calculada respecto al jugador.

---

## 3. Diagramas UML (Mermaid)

### A. Diagrama de Clases
```mermaid
classDiagram
    direction TB
    
    class EstadoJuego {
        <<enumeration>>
        MENU
        JUGANDO
        PAUSA
        GAME_OVER
    }

    class Main {
        +main(args: String[]) void
    }

    class MotorJuego {
        -EstadoJuego estadoActual
        -List~EntidadVideojuego~ entidades
        -Jugador jugadorPrincipal
        +MotorJuego()
        +iniciarPartida() void
        +pausarPartida() void
        +reanudarPartida() void
        +actualizar() void
        +procesarComando(String comando) void
        +comprobarColisiones() void
        +agregarEntidad(EntidadVideojuego e) void
        -inicializarEscenario() void
    }

    class EntidadVideojuego {
        <<abstract>>
        #int x
        #int y
        #int ancho
        #int alto
        #String nombre
        #int salud
        +EntidadVideojuego(nombre: String, x: int, y: int, w: int, h: int, salud: int)
        +actualizar() void
        +getDistance(EntidadVideojuego otra) int
        +getNombre() String
        +getX() int
        +getY() int
        +getAncho() int
        +getAlto() int
        +getSalud() int
        +setSalud(salud: int) void
    }

    class Jugador {
        +Jugador(nombre: String, x: int, y: int)
        +mover(String direccion) void
        +actualizar() void
    }

    class ComportamientoEnemigo {
        <<enumeration>>
        PATRULLAR
        PERSEGUIR
    }

    class Enemigo {
        -ComportamientoEnemigo estadoIA
        +Enemigo(nombre: String, x: int, y: int)
        +actualizar() void
        +evaluarIA(Jugador jugador) void
    }

    MotorJuego --> EstadoJuego : gestiona
    MotorJuego "1" *--> "*" EntidadVideojuego : contiene
    MotorJuego --> Jugador : referenciaDirecta
    EntidadVideojuego <|-- Jugador : hereda
    EntidadVideojuego <|-- Enemigo : hereda
    Enemigo --> ComportamientoEnemigo : estadoIA
    Main ..> MotorJuego : inicializa y simula

graph LR
    Jugador((Actor: Jugador))
    
    subgraph Motor de Juego 2D
        CU1(CU-01: Iniciar Partida)
        CU2(CU-02: Enviar Comando de Movimiento)
        CU3(CU-03: Pausar/Reanudar Juego)
        CU4(CU-04: Procesar Ciclo de Colisiones)
    end
    
    Jugador --> CU1
    Jugador --> CU2
    Jugador --> CU3
    CU2 ..> CU4 : <<include>>
    Jugador --> CU3
    CU2 ..> CU4 : <<include>>
    Enemigo --> ComportamientoEnemigo : estadoIA
    Main ..> MotorJuego : inicializa y simula
