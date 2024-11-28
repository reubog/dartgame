FROM eclipse-mosquitto:2.0
COPY mosquitto.conf /mosquitto/config/mosquitto.conf
RUN chown root:root /mosquitto/config/mosquitto.conf
RUN chmod 755 /mosquitto/config/mosquitto.conf
EXPOSE 1883
EXPOSE 9001
