#  dartboard-mqtt-granboard-linux

This application connects to a Granboard dartboard via bluetooth low energy. The 
library use the BlueZ bluetooth stack which is only supported on linux systems.

Requires a running mqtt server

## Re-map your dartboard values
Run main in this class ```com.bognandi.dart.domain.dartboard.bluetooth.GranboardValueMapper``` and copy the logs.

A current list via mapping can be found in ```granboard-values.txt```.