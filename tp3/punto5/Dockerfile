FROM hypoport/httpd-cgi

RUN apk update && apk add wget && rm -rf /var/cache/apk/*

RUN apk update && apk add --no-cache python3-dev

RUN touch /usr/local/apache2/mensajes.csv && chmod 777 /usr/local/apache2/mensajes.csv

RUN touch /usr/local/apache2/cookies.csv && chmod 777 /usr/local/apache2/cookies.csv
