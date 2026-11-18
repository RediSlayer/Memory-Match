# Memory Match

A 4x4 memory card game (find the matching pairs) built with Java Swing. The project follows a simple MVC structure and includes a timer, a status message area, and a restart button.

## Project Structure

```
src/
├── controlador/
│   └── Main.java          # Entry point, creates the window
├── modelo/
│   ├── Tablero.java       # Game logic and board state
│   └── Reloj.java         # Elapsed-time logic
└── vista/
    ├── Boton.java         # Custom card button
    └── PanelJuego.java    # Main panel: UI and game flow
```

## How to Run

Requires JDK 8 or higher. From the project root:

```bash
javac -d out src/modelo/*.java src/vista/*.java src/controlador/*.java
java -cp out src.controlador.Main
```

## How to Play

1. Click a card to reveal its symbol.
2. Click a second card.
3. If both symbols match, the pair stays revealed and both buttons are disabled.
4. If they don't match, both cards are hidden again after 1.5 seconds.
5. The game ends when all 8 pairs are found. The timer stops and the **Reiniciar** (Restart) button appears.

## Code Explanation

### `controlador/Main.java`

Entry point of the application. It creates a `JFrame` titled "Memory Match" (445x525, not resizable, centered on screen), adds a `PanelJuego` to it, and shows it. Everything runs inside `SwingUtilities.invokeLater` so the UI is created on the Event Dispatch Thread, as Swing requires.

### `modelo/Tablero.java` (Model: board)

Holds the game state and rules, with no UI code.

- `tableroConSimbolos`: a `char[4][4]` matrix with the symbols of each cell.
- `parejasEncontradas`: counter of pairs found so far.
- `IniciarTablero()`: creates a list with 8 symbols (`♥ ♦ ♣ ♠ ★ ☀ ♫ ☯`), each added twice, shuffles it with `Collections.shuffle`, fills the matrix, and resets the pair counter. It is used both at game start and on restart.
- `VerificarParejas(f1, c1, f2, c2)`: compares the symbols at two positions. If they match, it increments `parejasEncontradas` and returns `true`; otherwise it returns `false`.
- `isJuegoTerminado()`: returns `true` when 8 pairs have been found.
- `getSimbolo(fila, columna)`: returns the symbol at a given cell.
- `getParejasEncontradas()`: returns the current pair count.
- `Reiniciar()`: resets the counter and rebuilds the board.

### `modelo/Reloj.java` (Model: clock)

Keeps track of elapsed seconds using a `javax.swing.Timer` that fires every 1000 ms.

- `IniciarReloj()`: creates and starts a repeating timer that increments `segundosTranscurridos`.
- `getSegundosTranscurridos()`: returns the elapsed seconds.
- `DetenerReloj()`: stops the timer (called when the player wins).
- `ReiniciarReloj()`: sets the seconds back to 0 and restarts the timer.

### `vista/Boton.java` (View: card button)

A `JButton` subclass that stores its own position in the board (`fila`, `columna`), exposed through `getFila()` and `getColumna()`. This lets the panel know which board cell a clicked button corresponds to. It also sets the font (`Segoe UI Symbol`, bold, 28), disables the focus border, and uses a light gray background.

### `vista/PanelJuego.java` (View + game flow)

The main `JPanel`. It uses a `null` layout (absolute positioning with `setBounds`) and contains:

- A time label and a message label at the top.
- A 4x4 grid of `Boton` objects, created and positioned in the constructor (80x80 px each, 90 px spacing).
- A restart button, hidden until the game ends.
- Instances of `Tablero` and `Reloj`.

**Control variables**

- `primerBotonSeleccionado` / `segundoBotonSeleccionado`: the cards currently selected.
- `esperandoVerificacion`: freezes the board while a failed pair is waiting to be hidden.

**Main methods**

- `gestionarClickBoton(Boton)`: handles every card click:
  1. Ignores the click if the game is over, the board is frozen, or the same card is clicked twice.
  2. Asks the model for the symbol and shows it on the button.
  3. If it is the first card, stores it. If it is the second, calls `Tablero.VerificarParejas`.
  4. On a match: updates the message, disables both buttons, and checks whether the game is finished (stops both clocks and shows the restart button).
  5. On a mismatch: shows a message, freezes the board, and uses a non-repeating `Timer` (1.5 s) to hide both symbols and unfreeze the board.
- `IniciarRelojVisual()`: starts the model clock and creates a `Timer` that refreshes the time label every second. It stops any previous visual timer first to avoid piling up timers.
- `reiniciarBotones()`: re-enables all buttons and clears their text.
- `ReiniciarJuego()`: resets the buttons, reshuffles the board, restarts the clocks, clears the selections and the freeze flag, and hides the restart button.

## Design Notes

- **MVC separation**: `Tablero` and `Reloj` contain no visual code. `PanelJuego` only asks the model for data and updates the UI.
- **Timers**: Swing timers (`javax.swing.Timer`) are used so that all updates happen on the Event Dispatch Thread, avoiding thread-safety issues with Swing components.
- **Delays**: a 1.5-second delay lets the player see the second card before it is hidden.
