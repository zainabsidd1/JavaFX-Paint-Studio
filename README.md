JavaFX Paint Application🎨
## About 

JavaFX Paint Studio is a fully-featured desktop painting application built in Java 
with JavaFX. Developed over 4 sprints using Agile/Scrum, the app supports a wide 
range of drawing tools, smart AI features, and a clean, accessible UI.

## Features

### Drawing tools
- **Shapes** — circle, rectangle, square, triangle, oval, polyline, and freehand squiggle
- **Tools** — eraser, fill bucket, spray can, and mover
- **Stroke controls** — adjustable line thickness with a live preview indicator
- **Color controls** — full color selector with accessibility label for visually impaired users

### Editing
- Undo / Redo for all actions including shape movement
- Cut, copy, and paste shapes across the canvas
- Change canvas background color (eraser adapts accordingly)
- Clear canvas with a single click

### AI & generative features
- **Random art generator** — AI-driven composition using seeds for reproducible results
- **Scenery generator** — one-click generation of pre-built scenic compositions

## Architecture

| Pattern | Role |
|---|---|
| Command | Powers the undo/redo system for all user actions |
| Factory | Handles modular creation of all shape types |
| Visitor | Decouples rendering logic from shape definitions |
| Strategy | Drives the pluggable tool system (draw, erase, move, fill, etc.) |
| MVC | Separates model, view, and controller responsibilities |


