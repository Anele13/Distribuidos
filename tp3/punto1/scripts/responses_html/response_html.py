class ResponseHtml():
    """
    Esta clase se arma de acuerdo a las peticiones que se deben devolver 
    desde los script CGI armados con python. Funciona como clase generica o wrap para
    respuestas html.
    """
    header = "Content-type: text/html"
    cookie = None
    body = None

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
        print(self.body)

    def redirect(self, URI):
        print("Content-type: text/html\r")
        if self.cookie:
            print(self.cookie)
        print("Location: "+URI+"\r\n\r")



    def http_response_rango_edades():
        cadena1 ="<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>\
            <script src='https://code.jquery.com/jquery-3.3.1.slim.min.js' integrity='sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo' crossorigin='anonymous'></script>\
            <script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js' integrity='sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1' crossorigin='anonymous'></script>\
            <script src='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js' integrity='sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM' crossorigin='anonymous'></script>\
            <html>\
            <meta charset='utf-8'>"    
        cadena2 = "<div class='row justify-content-center'>\
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
        self.setContentBody(cadena1 + cadena2)