import csv

class Orm():
    """
    Clase para manejar las consulta al archivo usuarios.csv 
    """

    @classmethod
    def modificateUser(self,legajo, form):


        


    @classmethod
    def getUserFromTokenfile(self, token):
        """
        busca un usuario y devuelve su legajo en caso de encontrarlo
        dentro del archivo de tokens (cookies.csv)
        """
        with open('/usr/local/apache2/cookies.csv', 'r') as csvFile:
            reader = csv.reader(csvFile)
            for row in reader:
                if token == row[2]: #token
                    return row[0] #legajo
        csvFile.close()
        return None


    @classmethod
    def getDatosUsuario(self, legajo):
        """
        Devuelve una lista con todos los datos del usuario que busco
        desde el archivo de usuarios
        """
        with open('/usr/local/apache2/usuarios.csv', 'r') as csvFile:
            reader = csv.reader(csvFile)
            for row in reader:
                if legajo == row[1]: #legajo
                    return row
        csvFile.close()
        return None


    @classmethod
    def createUser(self, lista_datos_usuario):
        pass
    

    @classmethod
    def searchUser(self, legajo, pwd=None):
        """
        Devuelve true o false si encuentra o no al alumno por legajo
        """
        with open('/usr/local/apache2/usuarios.csv', 'r') as csvFile:
            reader = csv.reader(csvFile)
            for row in reader:
                if pwd:
                    if legajo == row[1] and pwd == row[4]:
                    return True
                else:
                    if legajo == row[1]:
                    return True
        return False


    @classmethod
    def findTokenInTokenFile(self, token):
        """
        Busca un token de cookie dentro del archivo de cookies
        y devuelve verdadero o falso segun el caso
        """
        with open('/usr/local/apache2/cookies.csv', 'r') as csvFile:
            reader = csv.reader(csvFile)
            for row in reader:
                if token == row[2]:
                    return True
        return False
    

    @classmethod
    def saveTokenInTokenfile(self, legajo, pwd, token):
        """
        Metodo que guarda en el archivo de tokens: legajo,pwd,token
        """
        row = [legajo,pwd, token]
        with open('/usr/local/apache2/cookies.csv', 'a') as csvFile:
            writer = csv.writer(csvFile)
            writer.writerow(row)
        csvFile.close()