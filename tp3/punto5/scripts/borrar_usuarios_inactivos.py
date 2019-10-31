#!/usr/bin/python3

import csv
from tempfile import NamedTemporaryFile
import shutil
from datetime import datetime

"""
Este script borra los usuarios inactivos dentro del archivo de cookies
cuando un usuario escribe un mensaje nuevo en el chat su marca de tiempo <timestamp>
dentro del archivo de cookies se refresca.
Si el usuario no "manda mensajes" dentro de su sesion entonces su timestamp no se actualiza
y el crontab ejecuta este script cada cierto tiempo eliminando usuarios inactivos.
"""
cookie_user_list = []
lista_resultado =[]
cookies_filepath = '/usr/local/apache2/cookies.csv'
umbral = 1 #equivalente a 1 minuto. esta en segundos. Esto es cuanto dura la sesion

# Busco todos los usuarios en el archivo de cookies

with open(cookies_filepath, 'r') as csvFile:
    reader = csv.reader(csvFile)
    for row in reader:
        cookie_user_list.append(row)
csvFile.close()

# NICK TOKEN TIMESTAMP ESTADO

print("estoy en cron")

# Actualizo solo el que necesito
with open(cookies_filepath, 'w') as csvFile:
    writer = csv.writer(csvFile)
    for usuario in cookie_user_list:
        timestamp_archivo = datetime.strptime(str(row[2]),'%m/%d/%Y %H:%M:%S')
        timestam_arribo = datetime.strptime(datetime.now().strftime('%m/%d/%Y %H:%M:%S'),'%m/%d/%Y %H:%M:%S')

        if not (((timestam_arribo- timestamp_archivo).total_seconds()/60)> umbral):
            #lista_resultado.append(usuario) #para diferenciar los msj que envio yo de los que recibo.
            writer.writerow(usuario)
        else:
            pass

csvFile.close()
