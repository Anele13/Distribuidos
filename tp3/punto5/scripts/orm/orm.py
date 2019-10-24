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

    @classmethod
    def createUser(self, nick,date):
        """
        Metodo que sirve para dar de alta un nuevo usuario.
        lanza una excepcion en caso de ingresar un legajo duplicado
        """        
        row = [nick, date]
        with open(self.usuarios_filepath, 'a') as csvFile:
            writer = csv.writer(csvFile)
            writer.writerow(row)
        csvFile.close()

    @classmethod
    def removeUser(self, nick):
        """
        Metodo que sirve para borrar un  usuario.
        """        

        with open(self.usuarios_filepath, 'w+') as csvFile:
            writer = csv.writer(csvFile)
            lista=[]
            for row in writer:
                if row[0] == nick:
                    pass
                else:
                    lista.append(row)
            writer.writerows(lista)
        csvFile.close()
    
    @classmethod
    def removeCookie(self, token):
        """
        Metodo que sirve para dar borrar una cookie.
        """        

        with open(self.cookies_filepath, 'w+') as csvFile:
            writer = csv.writer(csvFile)
            lista=[]
            for row in writer:
                if row[1] == token:
                    pass
                else:
                    lista.append(row)
            writer.writerows(lista)
        csvFile.close()


    @classmethod
    def getNickFromCookie(self, token):
        """
        Metodo que sirve para obtener un nick a partir de una cookie.
        """
        with open(self.cookies_filepath, 'r') as csvFile:
            reader = csv.writer(csvFile)
            for row in reader:
                if row[1] == token:
                    nick = row[0]
        csvFile.close()
        return nick
