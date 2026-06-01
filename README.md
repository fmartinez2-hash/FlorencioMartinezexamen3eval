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


<img src="https://github.com/user-attachments/assets/ae08b920-3117-4e8f-b1b7-db1c2b9848b6" width="500" alt="Descripción de la imagen"/>

<img width="1104" height="698" alt="{E1170EED-8EEF-4A9C-A56C-F810A2C32C3E}" src="https://github.com/user-attachments/assets/c9bc47db-40b9-45f9-85d3-b8d164fb5949" />
