# Dartgame Gui

## Target system configuration
### Upload
On build system:
```shell
scp dart-gui/target/dart-gui-0.0.1-SNAPSHOT-dist.tar.gz [user@][target-system]:~/Downloads
```
### Install
On target system:
```shell
mkdir "~/opt"
cd ~/opt
tar -xf ~/Downloads/dart-gui-0.0.1-SNAPSHOT-dist.tar.gz
```
### User service
On target system, create the following file:
```shell
$ cat .config/systemd/user/dartgame.service 
[Unit]
Description=Dartgame GUI
PartOf=graphical-session.target

[Service]
Environment="DISPLAY=:0"
Environment="JAVA_HOME=/usr/lib/jvm/bellsoft-java21-aarch64"
Environment="JAVAFX_HOME=/opt/javafx-sdk-23.0.1"
ExecStart=/home/reuben/opt/dart-gui-dist/javafx-run.sh

[Install]
WantedBy=default.target
```

User ```systemctl --user``` to start/stop and ```journalctl --user -u dartgame``` to inspect logs

### Mqtt Service
```shell
sudo apt install mosquitto
```