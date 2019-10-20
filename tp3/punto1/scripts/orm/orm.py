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
    def modificateUser(self,legajo, form):
        nya = form.getvalue("nya")
        sexo = form.getvalue("select_sexo")
        edad = form.getvalue("edad")
        pwd = form.getvalue("pwd")

        #--------------------------------------------------
        # Busco todos los usuarios en el archivo de cookies
        cookie_user_list = []
        with open(self.cookies_filepath, 'r') as csvFile:
            reader = csv.reader(csvFile)
            for row in reader:
                cookie_user_list.append(row)
        csvFile.close()
        # Actualizo solo el que necesito
        with open(self.cookies_filepath, 'w') as csvFile:
            writer = csv.writer(csvFile)
            for usuario in cookie_user_list:
                if usuario[0] == legajo:
                    writer.writerow([usuario[0], pwd, usuario[2]])
                else:
                    writer.writerow(usuario)
        csvFile.close()
        
        #---------------------------------------------------
        # Busco todos los usuarios en el archivo de usuarios
        user_list = []
        with open(self.usuarios_filepath, 'r') as csvFile:
            reader = csv.reader(csvFile)
            for row in reader:
                user_list.append(row)
        csvFile.close()
        # Actualizo solo el que necesito
        with open(self.usuarios_filepath, 'w') as csvFile:
            writer = csv.writer(csvFile)
            for usuario in user_list:
                if usuario[1] == legajo:
                    writer.writerow([nya,usuario[1],sexo,edad,pwd])
                else:
                    writer.writerow(usuario)
        csvFile.close()

    @classmethod
    def searchUserList(self, form):
        """
        Busca una lista de usuarios completa por legajo
        """
        nombre = form.getvalue("clave") #Busqueda por campo nombre de persona
        lista_usuarios_encontrados = []
        with open('/usr/local/apache2/usuarios.csv' , 'r') as csvFile:
            reader = csv.reader(csvFile)
            for row in reader:
                if nombre in row[0]: #Nombre de usuario
                    lista_usuarios_encontrados.append(row)
        csvFile.close()
        if not lista_usuarios_encontrados:
            raise Exception("No se encontro ningun usuario con ese legajo.")
        return lista_usuarios_encontrados


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
    def createUser(self, form):
        """
        Metodo que sirve para dar de alta un nuevo usuario.
        lanza una excepcion en caso de ingresar un legajo duplicado
        """
        nombre = form.getvalue("nya")
        legajo = form.getvalue("legajo")
        sexo = form.getvalue("select_sexo")
        edad = form.getvalue("edad")
        pwd = form.getvalue("pwd")

        if self.searchUser(legajo):
            raise Exception("El numero de legajo ingresado ya existe")
        
        row = [nombre, legajo, sexo, edad, pwd]
        with open(self.usuarios_filepath, 'a') as csvFile:
            writer = csv.writer(csvFile)
            writer.writerow(row)
        csvFile.close()
        

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
        Metodo que guarda en el archivo de tokens: legajo, pwd, token (EN ESE ORDEN)
        """
        row = [legajo,pwd, token]
        with open('/usr/local/apache2/cookies.csv', 'a') as csvFile:
            writer = csv.writer(csvFile)
            writer.writerow(row)
        csvFile.close()
        
    @classmethod
    def searchUsersInRange(self, range):
        """
        Devuelve la lista de usuarios en el rango dado
        """
        lista_usuarios_en_rango = []
        with open('/usr/local/apache2/usuarios.csv', 'r') as csvFile:
            reader = csv.reader(csvFile)
            for row in reader:
                if (range == "1"):
                    if (("0" <= row[3])  and (row[3]<="20")):
                        lista_usuarios_en_rango.append(row)
                else:
                    if (range == "2"):
                        if (("20" < row[3]) and (row[3] <= "40")):
                            lista_usuarios_en_rango.append(row)
                    else:
                        if (range == "3"):
                            if ("40" < row[3]):
                                lista_usuarios_en_rango.append(row)
        csvFile.close()
        return lista_usuarios_en_rango