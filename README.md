# dart game
Dart Board Game for Electronic Dart Board 

![Granboard Dash Blue](granboard-dash-blue.jpg "Granboard Dash Blue")

Implemented:
* Connect to electronic dart board, i.e Granboard Dash(/3s)
* Run on Raspberry Pi 4 ~~(3:ish)~~ (Faster is recommended)
* ~~Kiosk mode without X11 or~~ desktop system
* Fullscreen with Full HD 1920x1080

Future:
* Lots of in-game videos similar to arcade from 80s (generate with AI?)
* Gamed voices (text-to-speech?)
* Player profiles
  * Join game via QR-code (web-ui)
  * Maintain player profile incl. statistics (web-ui)

# Modules
* [dart-core](dart-core/README.md) Core definitions Library
* [dart-gui](dart-gui/README.md) Dartgame GUI App
* [dartboard-mqtt](dartboard-mqtt) Library for Virtual MQTT Dartboard 
* [dartboard-mqtt-granboard-linux](dartboard-mqtt-granboard-linux/README.md) Granboard Bluetooth App
* [dartboard-mqtt-gui](dartboard-mqtt-gui/README.md) Virtual Dartboard App
* [dartboard-mqtt-monitor](dartboard-mqtt-monitor) MQTT Monitor App
* [dartgame-x01](dartgame-x01/README.md) Dartgame x01 Library (example of how to implement more dartfames games)
* [raspberrypi](raspberrypi/README.md) Target platform configuration

# System Topology
![System Topology](topology.drawio.svg "System Topology")

# Build and Run

* Java version = 21 (without JFX)
* Java FX version = 23.0.1

## From CLI
* Build: ```./mnvw clean install```
* Run
  * Start MQtt server: ```docker-compose up```
  * Run granboard:  ```./mvnw -pl dartboard-mqtt-granboard-linux spring-boot:run``` 
  * Run dartgame: ```./mvnw -pl dart-gui javafx:run```

## Deployment
* See [Dartgame](dart-gui/README.md)
* See [Granboard](dartboard-mqtt-granboard-linux/README.md)