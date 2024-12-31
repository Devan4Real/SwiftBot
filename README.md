# SwiftBot Dance Project

Welcome to the **SwiftBot Dance** repository! This project showcases an interactive program that combines robotics, hexadecimal-based programming, and Java to create a fun, educational, and feature-rich experience with the SwiftBot, a Raspberry Pi robot. The project demonstrates how robots can process QR code inputs, perform dynamic movements, and interact through visual and auditory feedback.

## Table of Contents
- [Project Overview](#project-overview)
- [Features](#features)
- [How It Works](#how-it-works)
  - [Hexadecimal Processing](#hexadecimal-processing)
  - [QR Code Scanning](#qr-code-scanning)
  - [Movement and Dance Logic](#movement-and-dance-logic)
  - [Music Integration](#music-integration)
  - [LED Visual Effects](#led-visual-effects)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
- [Code Structure](#code-structure)
- [Future Enhancements](#future-enhancements)
- [Acknowledgements](#acknowledgements)

## Project Overview
This project leverages the SwiftBot's capabilities to:
- Decode hexadecimal QR codes.
- Convert hexadecimal values into binary, decimal, and octal formats.
- Use these conversions to determine movement patterns, speeds, and visual effects.
- Play music synchronised with the robot's dance movements.

The program is built entirely in Java and highlights core concepts like multithreading, file handling, and the usage of external hardware APIs.

## Features
- **Hexadecimal Input**: Processes QR codes to retrieve hexadecimal values.
- **Dynamic Movements**: Performs spins and straight-line movements based on binary conversions.
- **Music Synchronisation**: Plays a music track while executing the dance.
- **Image Capture**: Takes pictures during movements, saving them locally.
- **LED Colour Effects**: Displays RGB colours corresponding to the hexadecimal value.
- **Button Interaction**: Enables user control through SwiftBot's physical buttons.

## How It Works

### Hexadecimal Processing
1. QR codes are scanned to extract hexadecimal values.
2. The values are converted into binary, decimal, and octal formats.
3. These conversions determine movement speed, LED colours, and dance patterns.

### QR Code Scanning
- The robot uses its camera to capture QR codes.
- The `swiftBot.decodeQRImage` method deciphers the QR code into a hexadecimal string.

### Movement and Dance Logic
- Binary digits (`0` or `1`) dictate the type of movement:
  - `1`: Move forward and capture an image.
  - `0`: Perform a spin.
- The movement duration is influenced by the hexadecimal string length.

### Music Integration
- Users can opt to play music during the dance.
- The track (`Test.wav.wav`) is played using Java's `javax.sound.sampled` package.

### LED Visual Effects
- RGB values are dynamically calculated based on the hexadecimal input.
- The LEDs under the robot illuminate to enhance visual appeal.

## Setup and Installation

### Prerequisites
- SwiftBot hardware with Raspberry Pi setup.
- Java Development Kit (JDK) installed.
- SwiftBot API libraries.
- QR codes containing hexadecimal data.

### Installation Steps
1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/SwiftBot-Dance.git
   ```
2. Import the project into your favourite Java IDE (e.g., IntelliJ IDEA, Eclipse).
3. Ensure the `swiftbot` library is properly configured in your IDE.
4. Place the music file `Test.wav.wav` in the appropriate directory.
5. Deploy the program to the SwiftBot's Raspberry Pi.

## Usage
1. Run the program on the SwiftBot:
   ```bash
   java SBDance
   ```
2. Follow the on-screen prompts to scan a QR code.
3. Press the `Y` button to start the dance or enable music.
4. View captured images in the `/home/pi/Documents` directory.
5. Exit the program by pressing any other button.

## Code Structure

- **Main Class (`SBDance`)**:
  - `main`: Initializes the SwiftBot and starts the program loop.
  - `ConvertAndPrint`: Processes hexadecimal inputs and controls the workflow.
  - `performDance`: Handles movement and image capture logic.
  - `playMusic`: Synchronises music playback with dance.
- **Utility Methods**:
  - `hexToBinary`, `hexToDecimal`, `hexToOctal`: Convert hexadecimal values into various formats.
  - `SetRGB`: Sets LED colours based on RGB values.
  - `writeToFile`: Logs sorted hexadecimal inputs and image count.

## Acknowledgements
This project was made possible thanks to:
- **SwiftBot and Raspberry Pi**: For providing a robust and flexible robotics platform.
- **Java Programming**: For its powerful libraries and multithreading capabilities.
- **Brunel University London**: For fostering creativity and innovation in Computer Science.

---
