class ResponseHtml():
    """
    Esta clase se arma de acuerdo a las peticiones que se deben devolver 
    desde los script CGI armados con python. Funciona como clase generica o wrap para
    respuestas html.
    """
    header = "Content-type: text/html"
    cookie = None
    message = None
    body = None

    def get_static(self):
        """
        metodo que tiene incluida las rutas a los estaticos bootstrap
        """
        static_path ="<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>\
                    <script src='https://code.jquery.com/jquery-3.3.1.slim.min.js' integrity='sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo' crossorigin='anonymous'></script>\
                    <script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js' integrity='sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1' crossorigin='anonymous'></script>\
                    <script src='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js' integrity='sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM' crossorigin='anonymous'></script>"
            
        return static_path


    def set_http_alert(self,message, level="default"):
        """
        Metodo que crea un mensaje de "Exito" o "Error" con Bootstrap luego de
        realizar una operacion (alta usurio, modificacion, etc)
        """
        alert = "<h5><div align='center' class='alert alert-"+ level + " role='alert'>"+ message+ "</div></h5>"
        self.message = alert
        

    def set_http_response_busqueda_usuario(self, lista_usuarios=None):
        cadena = ""
        usuarios = ""
        if lista_usuarios:
            for usuario in lista_usuarios:
                usuarios = usuarios + "<tr> <td>"+ usuario[0] +"</td> <td>"+ usuario[1] +"</td> <td>"+ usuario[2] +"</td> <td>"+ usuario[3] + "</td>  </tr>"

            cadena="<html>\
                    <meta charset='utf-8'>\
                    <div class='row justify-content-center'>\
                        <form action='busqueda_usuario' method='POST' name='alta_usuario_form' class='form-inline'>\
                            <input type='text' name='clave' id='clave' maxlength='70' placeholder='inserte campo a buscar' required>\
                            <button class='btn btn-info' type='submit'>Buscar</button>\
                        </form>\
                    </div>\
                    <div class='row justify-content-center'>\
                        <div class='col-md-4'>\
                            <table class='table table-bordered'>\
                                <thead>\
                                    <tr>\
                                        <th>NyA</th>\
                                        <th>Legajo</th>\
                                        <th>Sexo</th>\
                                        <th>Edad</th>\
                                    </tr>\
                                </thead>\
                                <tbody>"+usuarios+"</tbody>\
                            </table>\
                        </div>\
                    </div>\
                    </html>"
        else:
            cadena = "<div class='row justify-content-center'>\
                    <form action='busqueda_usuario' method='POST' class='form-inline'>\
                        <input type='text' name='clave' id='clave' maxlength='70' placeholder='inserte campo a buscar' required >\
                        <button class='btn btn-info' type='submit'>Buscar</button>\
                        </form>\
                    </div>"
        self.setContentBody(cadena)

    def set_http_response_alta_usuario(self):
        cadena = "<html>\
                <meta charset='utf-8'>\
                <button class='btn btn-info' type='submit' onclick='location.href='index2.html''>Volver</button>\
                <div class='row justify-content-center'>\
                    <form action='alta_usuario' method='POST' name='alta_usuario_form' required>\
                        <input type='text' name='nya' id='nya' maxlength='70' placeholder='nombre y apellido' required>\
                    <input type='number' name='legajo' id='legajo' min='1' max='9999' placeholder='legajo' required>\
                        <select name='select_sexo' id='select_sexo' placeholder='sexo'>\
                            <option value='F'>Femenino</option>\
                            <option value='M'>Masculino</option>\
                        </select>\
                        <input type='number' name='edad' id='edad' min='18' max='99' placeholder='edad' required>\
                        <input type='password' placeholder='Contraseña' name='pwd' id='pwd' required>\
                        <button class='btn-default' type='submit'>Alta</button>\
                    </form>\
                    </div> </html>"
        self.setContentBody(cadena)


    def set_http_response_moficacion_usuario(self, datos_usuario):
        # '<button class="btn btn-info" type="submit" onclick='+'location.href="http://localhost:9090/index2.html"'+'>Volver</button>'+
        if datos_usuario[2] == 'F': #Sexo
            cadena_sexo = "<option value='F'>Femenino</option>\
                           <option value='M'>Masculino</option>"
        else:
            cadena_sexo = "<option value='M'>Masculino</option>\
                           <option value='F'>Femenino</option>"

        cadena = "<html>\
                    <meta charset='utf-8'>\
                    <div class='row justify-content-center'>\
                        '<form action='modificar_usuario' method='POST' name='alta_usuario_form' required>\
                            <input type='text' name='nya' id='nya' maxlength='70' placeholder='nombre y apellido' value="+datos_usuario[0]+" required>\
                            <select name='select_sexo' id='select_sexo' placeholder='sexo'>"+ cadena_sexo + "</select>\
                            <input type='number' name='edad' id='edad' maxlength='10' placeholder='edad' value="+datos_usuario[3]+" required>\
                            <input type='password' placeholder='pwd' name='pwd' id='pwd' value="+datos_usuario[4]+" required>\
                            <button class='btn btn-info' type='submit' value='submit' name='boton_submit_modificacion' id='boton_submit_modificacion'> Modificar </button>'\
                        </form>\
                    </div>\
                </html>"
        self.setContentBody(cadena)


    def set_http_response_login(self):
        cadena = "<html>\
                    <meta charset='utf-8'>\
                        <html>\
                            <meta charset='utf-8'>\
                            <div class='row justify-content-center'>\
                                <form action='login' method='POST' class='form-inline'>\
                                    <input type='text' name='legajo' id='legajo' maxlength='70' placeholder='Numero de alumno (legajo)' required>\
                                    <input type='password' name='pwd' id='pwd' maxlength='70' placeholder='Contraseña' required>\
                                    <button class='btn btn-info' type='submit'>Login</button>\
                                </form>\
                            </div>\
                        </html>"
        self.setContentBody(cadena)

    #Funcion para el punto 4.
    def set_http_response_rango_edades(self):
        cadena = "<html>\
                <meta charset='utf-8'>\
                <div class='row justify-content-center'>\
                '<form action='consulta_rango_edades' method='POST' name='consulta_rango_edades_form' required>\
                    <select name='select_rango' id='select_rango' placeholder='rango_edades'>\
                        <option value='1'>0-20</option>\
                        <option value='2'>20-40</option>\
                        <option value='3'>mas de 40</option>\
                    </select>\
                        <button class='btn-default' type='submit'>Buscar</button>'\
                    </form>\
                </div>\
            </html>"
        self.setContentBody(cadena)


    def getContentBody(self):
        return self.body


    def setHeader(self, new_header):
        self.header = new_header
            

    def setCookie(self, new_cookie):
        self.cookie = new_cookie


    def setContentBody(self, new_content):
        self.body = new_content
    

    def render(self):
        """
        Metodo para renderizar el html desde cualquier CGI
        """
        # Cabecera
        print(self.header)
        # Cookie
        if (self.cookie):
            print(self.cookie)
            print()            
        else:
            print()
            print()
        # Body del html
        print(self.get_static()) # path a estaticos bootstrap   
        if (self.message):
            print(self.message)
        print(self.body)


    def redirect(self, URI):
        print("Content-type: text/html\r")
        if self.cookie:
            print(self.cookie)
        print("Location: "+URI+"\r\n\r")
        if self.message:
            print(self.message)



