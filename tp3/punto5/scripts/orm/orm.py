import csv
from tempfile import NamedTemporaryFile
import shutil

class Orm():
    """
    Clase para manejar las consulta al archivo usuarios.csv 
    """
    cookies_filepath = '/usr/local/apache2/cookies.csv'
    usuarios_filepath = '/usr/local/apache2/usuarios.csv'


    @classmethod
    def searchUser(self, nick=None):
        """
        Devuelve true o false si encuentra o no al alumno por legajo
        """
        with open('/usr/local/apache2/usuarios.csv', 'r') as csvFile:
            reader = csv.reader(csvFile)
            for row in reader:
                if nick == row[0]:
                    csvFile.close()
                    return True
                else:
                    csvFile.close()
                    return False
        csvFile.close()
        return False
    
    @classmethod
    def saveTokenInTokenfile(self, nick, token, date):
        """
        Metodo que guarda en el archivo de tokens: legajo, pwd, token (EN ESE ORDEN)
        """
        row = [nick,token, date]
        with open('/usr/local/apache2/cookies.csv', 'a') as csvFile:
            writer = csv.writer(csvFile)
            writer.writerow(row)
        csvFile.close()  

    @classmethod
    def findTokenInTokenFile(self, token):
        """
        Busca un token de cookie dentro del archivo de cookies
        y devuelve verdadero o falso segun el caso
        """
        with open('/usr/local/apache2/cookies.csv', 'r') as csvFile:
            reader = csv.reader(csvFile)
            for row in reader:
                if token == row[1]:
                    return True
        return False 
    

    @classmethod
    def getUsuariosEnVivo(self):
        """
        Recorre el archivo de cookies y todos los usuarios que encuentra
        los devuelve en una lista con sus nicks:
        Ejemplo return ['negro','micho','tito','gordo']
        """
        with open('/usr/local/apache2/cookies.csv', 'r') as csvFile:
            lista_usuarios = []
            reader = csv.reader(csvFile)
            for row in reader:
                lista_usuarios.append(row[0]) #El nick del usuario
        csv.close()
        return lista_usuarios

