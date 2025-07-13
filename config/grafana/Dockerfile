FROM grafana/grafana:latest
COPY ./provisioning /etc/grafana/provisioning
USER root
RUN chmod -R 755 /etc/grafana/provisioning
USER grafana
