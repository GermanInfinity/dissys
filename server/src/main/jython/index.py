import sys

from java.io import *
from javax.servlet.http import HttpServlet

class jythonTemplate(HttpServlet):
    def doGet(self, request, response):
        self.doPost(request, response)

    def doPost(self, request, response):
        toClient = response.getWriter()
        response.setContentType("text/html")
        toClient.println("<html><head><title>Servlet Test</title></head>" +
                    "<body><h1>Servlet Test</h1></body></html>")

if __name__ == "__main__":
    JS = jythonTemplate()