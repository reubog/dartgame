# dart game
Dart Board Game for Electronic Dart Board

Purpose:
* Connect to electronic dart board, i.e Granboard Dash(/3s)
* Run on Raspberry Pi (3:ish)
* Kiosk mode without X11 or desktop system
* Fullscreen with Full HD
* Lots of in-game videos similar to arcade from 80s (generate with AI?)
* Gamed voices (text-to-speech?)
* Player profiles
  * Join game via QR-code (web-ui)
  * Maintain player profile incl. statistics (web-ui)
  * 
  
# Build and Run

## From CLI
Build: ```./mnvw clean install```

Run: ```./mnvw javafx:run```

## From IntelliJ
Download javafx SDK platform dependent modules from [Gluon](https://gluonhq.com/products/javafx/) and copy so that jar files is found here ```platform/javafx-sdk-23.0.1/lib```

In run configuration add 
```
--module-path platform/javafx-sdk-23.0.1/lib --add-modules javafx.controls
```
as VM Options