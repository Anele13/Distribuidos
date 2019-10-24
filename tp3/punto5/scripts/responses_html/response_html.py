class ResponseHtml():
    """
    Esta clase se arma de acuerdo a las peticiones que se deben devolver 
    desde los script CGI armados con python. Funciona como clase generica o wrap para
    respuestas html.
    """
    header = "Content-type: text/html"
    sala_de_chat = None
    cookie = None
    message = None
    body = None
    is_ajax_response = False


    def get_static(self):
        """
        metodo que tiene incluida las rutas a los estaticos bootstrap
        """
        static_path ="<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>\
                    <script src='https://code.jquery.com/jquery-3.3.1.slim.min.js' integrity='sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo' crossorigin='anonymous'></script>\
                    <script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js' integrity='sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1' crossorigin='anonymous'></script>\
                    <script src='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js' integrity='sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM' crossorigin='anonymous'></script>\
                    <link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css' type='text/css' rel='stylesheet'>"

        return static_path


    def set_http_alert(self,message, level="default"):
        """
        Metodo que crea un mensaje de "Exito" o "Error" con Bootstrap luego de
        realizar una operacion (alta usurio, modificacion, etc)
        """
        alert = "<h5><div align='center' class='alert alert-"+ level + " role='alert'>"+ message+ "</div></h5>"
        self.message = alert
        

    #-----------------------------------------------------------------------------------
    # inicio de metodos que ya no usamos
    #-----------------------------------------------------------------------------------
    def set_http_response_busqueda_usuario(self, lista_usuarios=None):
        cadena = ""
        usuarios = ""
        if lista_usuarios:
            for usuario in lista_usuarios:
                usuarios = usuarios + "<tr> <td>"+ usuario[0] +"</td> <td>"+ usuario[1] +"</td> <td>"+ usuario[2] +"</td> <td>"+ usuario[3] + "</td>  </tr>"

            cadena="<html>\
                    <meta charset='utf-8'>\
                    <a href='index2.html' class='btn btn-primary btn-lg active' role='button' aria-pressed='true' >Volver</a>\
                    <div class='row justify-content-center'>\
                         <form action='busqueda_usuario' method='POST' name='alta_usuario_form' class='form-inline'>\
                            <input type='text' name='clave' id='clave' maxlength='70' placeholder='Ingrese nombre de alumno' required>\
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
            cadena = "<a href='index2.html' class='btn btn-primary btn-lg active' role='button' aria-pressed='true' >Volver</a>\
                    <div class='row justify-content-center'>\
                    <form action='busqueda_usuario' method='POST' class='form-inline'>\
                        <input type='text' name='clave' id='clave' maxlength='70' placeholder='Ingrese nombre de alumno' required >\
                        <button class='btn btn-info' type='submit'>Buscar</button>\
                        </form>\
                    </div>"
        self.setContentBody(cadena)

    def set_http_response_alta_usuario(self):
        cadena = "<html>\
                <meta charset='utf-8'>\
                <a href='index2.html' class='btn btn-primary btn-lg active' role='button' aria-pressed='true' >Volver</a>\
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
                        <button class='btn btn-info' type='submit'>Alta</button>\
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
                        <form action='modificar_usuario' method='POST' name='alta_usuario_form' required>\
                            <a href='index2.html' class='btn btn-primary btn-lg active' role='button' aria-pressed='true' >Inicio</a>\
                            <input type='text' name='nya' id='nya' maxlength='70' placeholder='nombre y apellido' value="+datos_usuario[0]+" required>\
                            <select name='select_sexo' id='select_sexo' placeholder='sexo'>"+ cadena_sexo + "</select>\
                            <input type='number' name='edad' id='edad' maxlength='10' placeholder='edad' value="+datos_usuario[3]+" required>\
                            <input type='password' placeholder='pwd' name='pwd' id='pwd' value="+datos_usuario[4]+" required>\
                            <button class='btn btn-info' type='submit' value='submit' name='boton_submit_modificacion' id='boton_submit_modificacion'> Modificar </button>\
                        </form>\
                    </div>\
                </html>"
        self.setContentBody(cadena)


    def set_http_response_login(self):
        cadena = "<html>\
                    <meta charset='utf-8'>\
                        <html>\
                            <meta charset='utf-8'>\
                            <a href='index2.html' class='btn btn-primary btn-lg active' role='button' aria-pressed='true' >Volver</a>\
                            <div class='row justify-content-center'>\
                                <form action='login' method='GET' class='form-inline'>\
                                    <input type='text' name='nick' id='nick' maxlength='70' placeholder='Nombre de usuario (nick)' required>\
                                    <button class='btn btn-info' type='submit'>Login</button>\
                                </form>\
                                <form action='logout' method='POST' class='form-inline'>\
                                    <button class='btn btn-info' type='submit'>Logout</button>\
                                </form>\
                            </div>\
                        </html>"
        self.setContentBody(cadena)

    #Funcion para el punto 4.
    def set_http_response_rango_edades(self):
        cadena = "<html>\
                <meta charset='utf-8'>\
                <a href='index2.html' class='btn btn-primary btn-lg active' role='button' aria-pressed='true' >Volver</a>\
                <div class='row justify-content-center'>\
                <form action='consulta_rango_edades' method='POST' name='consulta_rango_edades_form' required>\
                    <select name='select_rango' id='select_rango' placeholder='rango_edades'>\
                        <option value='1'>0-20</option>\
                        <option value='2'>20-40</option>\
                        <option value='3'>mas de 40</option>\
                    </select>\
                        <button class='btn btn-info' type='submit'>Buscar</button>\
                    </form>\
                </div>\
            </html>"
        self.setContentBody(cadena)

    def set_http_response_busqueda_usuarios_en_rango(self, lista_usuarios=None):
        cadena = ""
        usuarios = ""
        if lista_usuarios:
            for usuario in lista_usuarios:
                usuarios = usuarios + "<tr> <td>"+ usuario[0] +"</td> <td>"+ usuario[1] +"</td> <td>"+ usuario[2] +"</td> <td>"+ usuario[3] + "</td>  </tr>"

            cadena="<html>\
                    <meta charset='utf-8'>\
                    <a href='index2.html' class='btn btn-primary btn-lg active' role='button' aria-pressed='true' >Volver</a>\
                    <div class='row justify-content-center'>\
                    <form action='consulta_rango_edades' method='POST' name='consulta_rango_edades_form' required>\
                        <select name='select_rango' id='select_rango' placeholder='rango_edades'>\
                            <option value='1'>0-20</option>\
                            <option value='2'>20-40</option>\
                            <option value='3'>mas de 40</option>\
                        </select>\
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
            cadena = "<html>\
                    <meta charset='utf-8'>\
                    <a href='index2.html' class='btn btn-primary btn-lg active' role='button' aria-pressed='true' >Volver</a>\
                    <div class='row justify-content-center'>\
                    <form action='consulta_rango_edades' method='POST' name='consulta_rango_edades_form' required>\
                        <select name='select_rango' id='select_rango' placeholder='rango_edades'>\
                            <option value='1'>0-20</option>\
                            <option value='2'>20-40</option>\
                            <option value='3'>mas de 40</option>\
                        </select>\
                            <button class='btn btn-info' type='submit'>Buscar</button>\
                        </form>\
                    </div>"
        self.setContentBody(cadena)

    #---------------------------------------------------------------------------------------------------------
    # MEtodos para chat
    #---------------------------------------------------------------------------------------------------------

    def set_ajax_response(self, response):
        self.setContentBody(response)
        self.is_ajax_response = True


    def set_http_response_sala_de_chat(self, usuarios_activos=None, mensajes_usuarios=None, usuario_que_solicita=None):

        usuariosActivos = ""
        mensajesDeChat = ""

        if usuarios_activos:
            for usuario in usuarios_activos:
                usuariosActivos = usuariosActivos + "<div class='chat_list'>\
                                                        <div class='chat_people'>\
                                                            <div class='chat_img'> <img src='https://ptetutorials.com/images/user-profile.png' alt='sunil'> </div>\
                                                            <div class='chat_ib'>\
                                                            <h5>"+ usuario +" <span class='chat_date'>Dec 25</span></h5>\
                                                            <p> Hola soy un usuario.</p>\
                                                            </div>\
                                                        </div>\
                                                    </div>"
                
        if mensajes_usuarios:
            for mensaje in mensajes_usuarios:
                if mensaje[0] == usuario_que_solicita:
                    mensajesDeChat = mensajesDeChat + "<div class='incoming_msg'>\
                                                        <div class='incoming_msg_img'> <img src='https://ptetutorials.com/images/user-profile.png' alt='sunil'> </div>\
                                                        <div class='received_msg'>\
                                                            <div class='received_withd_msg'>\
                                                            <p>"+mensaje[1]+"</p>\
                                                            <span class='time_date'>"+mensaje[2]+"</span></div>\
                                                        </div>\
                                                        </div>"
                else:
                    mensajesDeChat = mensajesDeChat + "<div class='outgoing_msg'>\
                                                        <div class='sent_msg'>\
                                                            <p>"+mensaje[1]+"</p>\
                                                            <span class='time_date'>"+mensaje[2]+"</span></div>\
                                                        </div>"

        sala_chat = "<style>\
                    .container{max-width:1170px; margin:auto;}\
                    img{ max-width:100%;}\
                    .inbox_people {\
                    background: #f8f8f8 none repeat scroll 0 0;\
                    float: left;\
                    overflow: hidden;\
                    width: 40%; border-right:1px solid #c4c4c4;\
                    }\
                    .inbox_msg {\
                    border: 1px solid #c4c4c4;\
                    clear: both;\
                    overflow: hidden;\
                    }\
                    .top_spac{ margin: 20px 0 0;}\
                    .recent_heading {float: left; width:40%;}\
                    .srch_bar {\
                    display: inline-block;\
                    text-align: right;\
                    width: 60%; padding:\
                    }\
                    .headind_srch{ padding:10px 29px 10px 20px; overflow:hidden; border-bottom:1px solid #c4c4c4;}\
                    .recent_heading h4 {\
                    color: #05728f;\
                    font-size: 21px;\
                    margin: auto;\
                    }\
                    .srch_bar input{ border:1px solid #cdcdcd; border-width:0 0 1px 0; width:80%; padding:2px 0 4px 6px; background:none;}\
                    .srch_bar .input-group-addon button {\
                    background: rgba(0, 0, 0, 0) none repeat scroll 0 0;\
                    border: medium none;\
                    padding: 0;\
                    color: #707070;\
                    font-size: 18px;\
                    }\
                    .srch_bar .input-group-addon { margin: 0 0 0 -27px;}\
                    .chat_ib h5{ font-size:15px; color:#464646; margin:0 0 8px 0;}\
                    .chat_ib h5 span{ font-size:13px; float:right;}\
                    .chat_ib p{ font-size:14px; color:#989898; margin:auto}\
                    .chat_img {\
                    float: left;\
                    width: 11%;\
                    }\
                    .chat_ib {\
                    float: left;\
                    padding: 0 0 0 15px;\
                    width: 88%;\
                    }\
                    .chat_people{ overflow:hidden; clear:both;}\
                    .chat_list {\
                    border-bottom: 1px solid #c4c4c4;\
                    margin: 0;\
                    padding: 18px 16px 10px;\
                    }\
                    .inbox_chat { height: 550px; overflow-y: scroll;}\
                    .active_chat{ background:#ebebeb;}\
                    .incoming_msg_img {\
                    display: inline-block;\
                    width: 6%;\
                    }\
                    .received_msg {\
                    display: inline-block;\
                    padding: 0 0 0 10px;\
                    vertical-align: top;\
                    width: 92%;\
                    }\
                    .received_withd_msg p {\
                    background: #ebebeb none repeat scroll 0 0;\
                    border-radius: 3px;\
                    color: #646464;\
                    font-size: 14px;\
                    margin: 0;\
                    padding: 5px 10px 5px 12px;\
                    width: 100%;\
                    }\
                    .time_date {\
                    color: #747474;\
                    display: block;\
                    font-size: 12px;\
                    margin: 8px 0 0;\
                    }\
                    .received_withd_msg { width: 57%;}\
                    .mesgs {\
                    float: left;\
                    padding: 30px 15px 0 25px;\
                    width: 60%;\
                    }\
                    .sent_msg p {\
                    background: #05728f none repeat scroll 0 0;\
                    border-radius: 3px;\
                    font-size: 14px;\
                    margin: 0; color:#fff;\
                    padding: 5px 10px 5px 12px;\
                    width:100%;\
                    }\
                    .outgoing_msg{ overflow:hidden; margin:26px 0 26px;}\
                    .sent_msg {\
                    float: right;\
                    width: 46%;\
                    }\
                    .input_msg_write input {\
                    background: rgba(0, 0, 0, 0) none repeat scroll 0 0;\
                    border: medium none;\
                    color: #4c4c4c;\
                    font-size: 15px;\
                    min-height: 48px;\
                    width: 100%;\
                    }\
                    .type_msg {border-top: 1px solid #c4c4c4;position: relative;}\
                    .msg_send_btn {\
                    background: #05728f none repeat scroll 0 0;\
                    border: medium none;\
                    border-radius: 50%;\
                    color: #fff;\
                    cursor: pointer;\
                    font-size: 17px;\
                    height: 33px;\
                    position: absolute;\
                    right: 0;\
                    top: 11px;\
                    width: 33px;\
                    }\
                    .messaging { padding: 0 0 50px 0;}\
                    .msg_history {\
                    height: 516px;\
                    overflow-y: auto;\
                    }\
                    </style>\
                    <html>\
                    <body>\
                    <div class='container'>\
                    <h3 class=' text-center'>Messaging</h3>\
                    <div class='messaging'>\
                        <div class='inbox_msg'>\
                            <div class='inbox_people'>\
                            <div class='headind_srch'>\
                                <div class='recent_heading'>\
                                <h4>Recent</h4>\
                                </div>\
                                <div class='srch_bar'>\
                                <div class='stylish-input-group'>\
                                    <input type='text' class='search-bar'  placeholder='Search' >\
                                    <span class='input-group-addon'>\
                                    <button type='button'> <i class='fa fa-search' aria-hidden='true'></i> </button>\
                                    </span> </div>\
                                </div>\
                            </div>\
                            <div class='inbox_chat'>"+ usuariosActivos + "</div>\
                            </div>\
                            <div class='mesgs'>\
                            <div class='msg_history'>"+ mensajesDeChat +"</div>\
                            <div class='type_msg'>\
                                <div class='input_msg_write'>\
                                <input type='text' class='write_msg' placeholder='Type a message' />\
                                <button class='msg_send_btn' type='button'><i class='fa fa-paper-plane-o' aria-hidden='true'></i></button>\
                                </div>\
                            </div>\
                            </div>\
                        </div>\
                        <p class='text-center top_spac'> Design by <a target='_blank' href='#'>Sunil Rajput</a></p>\
                        </div></div>\
                        </body>\
                        </html>"
        self.setContentBody(sala_chat)












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
        if self.is_ajax_response:
            print("Content-type: application/json")
        else:
            print(self.header)
        # Cookie
        if (self.cookie):
            print(self.cookie)
            print()            
        else:
            print()
            print()
        # Body del html
        if not self.is_ajax_response:
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



