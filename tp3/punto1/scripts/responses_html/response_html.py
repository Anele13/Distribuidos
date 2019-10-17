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