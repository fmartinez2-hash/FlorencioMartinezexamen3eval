# FlorencioMartinezexamen3eval
# Cyberpunk Grid Runner - Motor de Videojuego 2D Base

## 1. Temática Elegida
**Cyberpunk Grid Runner** es una simulación de lógica interna para un juego táctico en cuadrícula 2D. El jugador controla a un "Hacker" que debe evadir a los "Drones de Seguridad" (NPCs con IA de patrulla y persecución) mientras se desplaza por el entorno. El objetivo del motor es procesar el movimiento, gestionar las colisiones en la cuadrícula y coordinar los estados globales del juego (Menú, Jugando, Pausa, Game Over).

---
## 1.1 Gestión de Repositorios (Flujo de Trabajo Git-Flow Adaptado)

Para el ciclo de desarrollo de **Cyberpunk Grid Runner**, se ha implementado un flujo de trabajo basado en **Git-Flow** adaptado a las necesidades del proyecto. Esto garantiza un historial de cambios limpio, estabilidad en producción y un desarrollo modular de las características del motor.

### 🌳 Estructura de Ramas
El repositorio está organizado estrictamente en tres niveles de ramas:
* **`main`**: Es la rama de producción. Contiene únicamente código 100% estable, testeado y listo para su ejecución final. Cada actualización aquí representa una versión completada del motor de juego.
* **`develop`**: Es la rama de integración. Aquí se unifican todas las nuevas funciones desarrolladas por el equipo antes de pasar a la rama principal. Es el espacio de trabajo del estado "actual" del desarrollo.
* **`feature/*`**: Son las ramas temporales de características (por ejemplo: `feature/colisiones`, `feature/ia-enemigo`, `feature/documentacion`). Se crean siempre a partir de `develop` y se destruyen una vez que la funcionalidad está terminada, revisada e integrada de vuelta en `develop`.

### 🔄 Flujo de Trabajo Diario
1. **Creación de Funcionalidad:** Para trabajar en un nuevo módulo, se abre una rama específica desde integración:
   ```bash
   git checkout develop
   git checkout -b feature/nombre-de-la-funcion

## 2. Arquitectura del Software
El sistema se ha diseñado bajo un enfoque minimalista de Programación Orientada a Objetos (POO), limitando el acoplamiento y encapsulando la lógica en 5 clases esenciales:

* **`Main`**: Actúa como el controlador de la simulación. Reemplaza la interfaz gráfica ejecutando un bucle de juego interactivo por consola que inyecta comandos del usuario.
* **`MotorJuego`**: El núcleo central. Gestiona el ciclo de vida del juego (`EstadoJuego`), la lista activa de entidades y expone los métodos públicos para alterar el entorno.
* **`EntidadVideojuego`**: Clase abstracta que define las propiedades comunes de cualquier objeto en el espacio bidimensional ($x, y, w, h$), su estado vital y su representación visual abstracta.
* **`Jugador`** (Hereda de `EntidadVideojuego`): Añade la gestión de energía y la capacidad de interactuar directamente con comandos de movimiento de la cuadrícula.
* **`Enemigo`** (Hereda de `EntidadVideojuego`): Integra la lógica de comportamiento móvil (Estados: `PATRULLAR`, `PERSEGUIR`). Cambia su estado dinámicamente según la distancia Manhattan calculada respecto al jugador.

<img src="https://github.com/user-attachments/assets/ae08b920-3117-4e8f-b1b7-db1c2b9848b6" width="500" alt="Descripción de la imagen"/>

<img width="1104" height="698" alt="{E1170EED-8EEF-4A9C-A56C-F810A2C32C3E}" src="https://github.com/user-attachments/assets/c9bc47db-40b9-45f9-85d3-b8d164fb5949" />

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

## 5. Bitácora del Uso de Inteligencia Artificial

### Herramienta y Rol
* **Herramienta:** ChatGPT (Modelo GPT-4o) / Claude 3.5 Sonnet.
* **Rol de la IA:** Arquitecto de Software Experto en Java y Desarrollo de Videojuegos 2D.

---

### Muestra de Prompts Reales

#### 🤖 Prompt 1 (Estructuración de Arquitectura)
> **Usuario:** "Actúa como un ingeniero de software senior. Necesito diseñar la lógica de control en Java de un motor 2D en cuadrícula sin interfaz gráfica. Tengo una restricción estricta de máximo 6 clases. Necesito definir la clase abstracta EntidadVideojuego con campos (x, y, w, h, salud) y dos subclases: Jugador y Enemigo. El Enemigo debe cambiar su estado entre PATRULLAR y PERSEGUIR si la distancia al jugador es menor que 3 unidades. Genérame únicamente la estructura de firmas y métodos de estas entidades aplicando encapsulamiento."

* **Impacto en el desarrollo:** Estableció la jerarquía de herencia base del proyecto y definió la lógica de cambio de estado de la IA del enemigo mediante la distancia de Manhattan, minimizando el acoplamiento entre las entidades desde la fase de diseño.

#### 🤖 Prompt 2 (Optimización del Algoritmo de Colisión)
> **Usuario:** "Escribe un método eficiente en Java dentro de la clase MotorJuego llamado comprobarColisiones(). Debe iterar sobre una lista de EntidadVideojuego utilizando bucles anidados para verificar si las cajas de colisión (AABB definidas por x, y, ancho, alto) se superponen. Si el Jugador colisiona con un Enemigo, debe restarle 20 puntos de vida al jugador e imprimir un log detallado."

* **Impacto en el desarrollo:** Proporcionó la base algorítmica para la detección de colisiones por cajas del tipo *Axis-Aligned Bounding Box* (AABB), asegurando que el motor procese los daños en el ciclo de juego e interactúe correctamente con el estado vital de la entidad `Jugador`.

## X. Gestión de Repositorios (Flujo de Trabajo Git-Flow Adaptado)

Para el ciclo de desarrollo de **Cyberpunk Grid Runner**, se ha implementado un flujo de trabajo basado en **Git-Flow** adaptado a las necesidades del proyecto. Esto garantiza un historial de cambios limpio, estabilidad en producción y un desarrollo modular de las características del motor.

### 🌳 Estructura de Ramas

El repositorio está organizado estrictamente en tres niveles de ramas:

* **`main`**: Es la rama de producción. Contiene únicamente código 100% estable, testeado y listo para su ejecución final. Cada actualización aquí representa una versión completada del motor de juego.
* **`develop`**: Es la rama de integración. Aquí se unifican todas las nuevas funciones desarrolladas por el equipo antes de pasar a la rama principal. Es el espacio de trabajo del estado "actual" del desarrollo.
* **`feature/*`**: Son las ramas temporales de características (por ejemplo: `feature/colisiones`, `feature/ia-enemigo`, `feature/documentacion`). Se crean siempre a partir de `develop` y se destruyen una vez que la funcionalidad está terminada, revisada e integrada de vuelta en `develop`.

---

### 🔄 Flujo de Trabajo Diario

1. **Creación de Funcionalidad:** Para trabajar en un nuevo módulo, se abre una rama específica desde integración:
   ```bash
   git checkout develop
   git checkout -b feature/nombre-de-la-funcion
