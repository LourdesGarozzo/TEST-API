# TEST-API-ROSHKA

TEST: Apificar una página web

El objetivo de este test es APIFICAR una página web para convertirla en un API RESTful de tal forma a que los datos arrojados por la página puedan integrarse con otros
sistemas satélites que requieran hacer uso de la información en cuestión.

Pasos para levantar el proyecto:

  - Clonar el repo https://github.com/LourdesGarozzo/TEST-API-ROSHKA.git

  - Crear la base de datos SQLSERVER con las siguientes credenciales:

    host: localhost
    port: 1433
    databaseName= roshka_test
    username=test
    password=test

  - En caso de modificar estos datos se puede modificar el archivo application.properties
  
  - Ejecutar el proyecto RoshkaTestApplication
  
  - Ingresar al link para realizar la prueba de la API creada. http://localhost:8080/swagger-ui/index.html#
  
  - Para más detalles de la API, ingresar al doc creado, allí se describe más a detalle los endpoints disponibles. https://docs.google.com/document/d/1MuCuppJveYWvRmHWJdttB47IsoSIeLQF_E8ypp7fhRc/edit?tab=t.0
