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

## 4. Especificación de Casos de Uso

### CU-01: Iniciar Partida

| Campo | Descripción |
| :--- | :--- |
| **Nombre** | CU-01 Iniciar Partida |
| **Objetivo** | Transicionar el estado del motor de juego para comenzar el ciclo de actualización de entidades. |
| **Actor Principal** | Jugador |
| **Precondiciones** | El motor de juego debe estar instanciado y en estado `MENU` o `GAME_OVER`. |
| **Flujo Principal** | 1. El jugador envía la instrucción de inicio.<br>2. El sistema cambia el `estadoActual` a `JUGANDO`.<br>3. El sistema limpia la lista de entidades previas.<br>4. El sistema instancia al jugador en la posición base $(0,0)$ y a los enemigos iniciales.<br>5. El sistema confirma el inicio enviando un log por consola. |
| **Flujos Alternativos**| **1a. El juego ya está en marcha:** Si el estado es `JUGANDO`, el sistema deniega la acción y avisa que ya hay una sesión activa. |
| **Postcondiciones** | El sistema queda en estado `JUGANDO` listo para recibir movimientos. |
| **Reglas de Negocio** | No se puede iniciar una nueva partida sin limpiar de memoria las entidades de la partida anterior. |

---

### CU-02: Enviar Comando de Movimiento

| Campo | Descripción |
| :--- | :--- |
| **Nombre** | CU-02 Enviar Comando de Movimiento |
| **Objetivo** | Modificar las coordenadas espaciales del jugador dentro de la cuadrícula interactiva. |
| **Actor Principal** | Jugador |
| **Precondiciones** | El sistema debe encontrarse estrictamente en estado `JUGANDO`. |
| **Flujo Principal** | 1. El jugador envía una cadena de texto de dirección ("ARRIBA", "ABAJO", "IZQUIERDA", "DERECHA").<br>2. El sistema valida que la dirección sea correcta.<br>3. El sistema actualiza las coordenadas ($x, y$) del objeto Jugador.<br>4. El sistema ejecuta el método `comprobarColisiones()`. |
| **Flujos Alternativos**| **1a. El motor está en PAUSA:** El sistema ignora el input e informa al jugador por consola.<br>**2a. Comando inválido:** Si la cadena de texto no coincide con las direcciones permitidas, el log reporta "Comando desconocido" y no consume el turno. |
| **Postcondiciones** | La coordenada del jugador se modifica y se evalúan los triggers de posición. |
| **Reglas de Negocio** | Las dimensiones de la cuadrícula están acotadas de forma simulada; el jugador no puede salir de los límites de coordenadas definidos. |

