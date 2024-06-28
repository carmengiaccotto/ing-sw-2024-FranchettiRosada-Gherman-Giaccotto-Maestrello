# CodexNaturalis Board Game
![](CodexNaturalis/src/main/resources/images/logo.jpg)
  
## Polimi University Project
This is a Java implementation of CodexNaturalis, a board game designed by Thomas Dupont and Maxime Morin, and published by Cranio Creations. This is the final project for the Software Engineering course of "Engineering of Computing Systems" held at Politecnico di Milano (2023/2024).

**Students** <br>

Giaccotto Carmen <br>
Gherman Denisa Minodora <br>
Franchetti-Rosada Alessia <br>
Maestrello Lucrezia <br>

## Implemented Features

| Feature                              |Status|
|--------------------------------------|----- |
| Complete rules                       | ✅   |
| Socket and RMI                       | ✅   |
| TUI                                  | ✅   |
| GUI                                  | ✅   |
| Multiple Games                       | ✅   |
| Persistence                          | ❌   |
| Chat                                 | ❌   |
| Resilience to clients disconnections | ❌   |

## Test cases

| Package  | Class Coverage | Method Coverage | Line Coverage  |
|----------|----------------|-----------------|----------------|
| Model    | 95 % (39/41)   | 90 % (170/187)  | 91 % (556/608) |

## How to run

In the [deliverables/final/jar](deliverables/final/jar) folder there are the two executable jar files already compiled.

- In order to run the server, execute this command:
    ```bash
    java -jar Server.jar
    ```

- To run the client, execute this command:
    ```bash
    java -jar Client.jar
    ```
  You will be able to choose between the CLI or the GUI interface by typing the option number on your keyboard accordingly.

## More

**Tools and Software Used**

- **[Draw.io](https://www.drawio.com)**: UML and sequence diagrams.
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/)**: Main IDE for project development.
- **[JavaFX](https://openjfx.io/)**: GUI design.
- **[Maven](https://maven.apache.org/)**: Package and dependency management.
- **[SceneBuilder](https://gluonhq.com/products/scene-builder/)**: GUI design.
- **[Overleaf](https://www.overleaf.com/)**: Documentation.

