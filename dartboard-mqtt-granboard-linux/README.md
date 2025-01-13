#  dartboard-mqtt-granboard-linux

This application connects to a Granboard dartboard via bluetooth low energy. The 
library use the BlueZ bluetooth stack which is only supported on linux systems.

Requires a running mqtt server

## Re-map your dartboard values
If needed to discover what vales the granboard is sending, run main in this class ```com.bognandi.dart.domain.dartboard.bluetooth.GranboardValueMapper``` and copy the logs.

A current list via mapping can be found in ```granboard-values.txt```.

## Target system
### Deploy
On build system:
```shell
scp dartboard-mqtt-granboard-linux/target/dartboard-mqtt-granboard-linux-0.0.1-SNAPSHOT-dist.tar.gz [user@][target-system]:~/Downloads
```
### Installation
On target system:
````shell
cd /opt
tar -xf ~/Downloads/dartboard-mqtt-granboard-linux-0.0.1-SNAPSHOT-dist.tar.gz
````
### System service
On target system create this file:
````shell
$ cat /lib/systemd/system/granboard.service
[Unit]
Description=Granboard Bluetooth to Mqtt
After=multi-user.target

[Service]
ExecStart=/usr/lib/jvm/bellsoft-java21-aarch64/bin/java -cp /opt/granboard-dist -jar /opt/granboard-dist/dartboard-mqtt-granboard-linux-0.0.1-SNAPSHOT.jar
User=root

[Install]
WantedBy=multi-user.target
````

Use ```systemctl``` to start/stop service and ```journalctl -u granboard``` to check the logs