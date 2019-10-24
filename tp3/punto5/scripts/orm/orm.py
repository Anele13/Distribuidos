import csv
from tempfile import NamedTemporaryFile
import shutil

class Orm():
    """
    Clase para manejar las consulta al archivo usuarios.csv 
    """
    cookies_filepath = '/usr/local/apache2/cookies.csv'
    usuarios_filepath = '/usr/local/apache2/usuarios.csv'
    mensajes_filepath = '/usr/local/apache2/mensajes.csv'
    


    @classmethod
    def searchUser(self, nick=None):
        """
        Devuelve true o false si encuentra o no al usuario por nick
        """
        with open('/usr/local/apache2/cookies.csv', 'r') as csvFile:
            reader = csv.reader(csvFile)
            for row in reader:
                if nick == row[0]:
                    return True
        return False
    
    @classmethod
    def saveTokenInTokenfile(self, nick, token, date):
        """
        Metodo que guarda en el archivo de tokens: nick,token y date (EN ESE ORDEN)
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
    def getUserFromTokenfile(self, token):
        """
        busca un usuario y devuelve su legajo en caso de encontrarlo
        dentro del archivo de tokens (cookies.csv)
        """
        with open('/usr/local/apache2/cookies.csv', 'r') as csvFile:
            reader = csv.reader(csvFile)
            for row in reader:
                if token == row[1]: #token
                    return row[0] #Nick
        csvFile.close()
        return None


    @classmethod
    def getNuevosMensajes(self, timestamp):
        lista_mensajes = []
        with open(self.mensajes_filepath, 'r') as csvFile:
            reader = csv.reader(csvFile)
            for row in reader:
                lista_mensajes.append(row)
        csvFile.close()
        return lista_mensajes


    @classmethod
    def getUsuariosEnVivo(self):
        """
        Recorre el archivo de cookies y todos los usuarios que encuentra
        los devuelve en una lista con sus nicks:
        Ejemplo return ['negro','micho','tito','gordo']
        """
        lista_usuarios = []
        with open(self.cookies_filepath, 'r') as csvFile:
            reader = csv.reader(csvFile)
            for row in reader:
                lista_usuarios.append(row[0])
        csvFile.close()
        return lista_usuarios

    
