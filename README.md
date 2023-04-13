# Hotel API

Hotel API se compone de dos proyectos:
- hotel-search: realiza las consultas a la base de datos y envia los mensajes a kafka para "guardar" nuevas busquedas de hoteles. Este proyecto esta integrado con spring-docs, para generar la documentacion segun OpenApi, se puede ingresar a la misma desde `http://localhost:8080/documentation`.
- hotel-availability: consume los mensajes que son enviados al topico de kafka y los persiste en la base de datos.

Dentro de la carpeta `resources` se puede encontrar la collection de postman con los endpoints correspondientes.

El proyecto esta configurado para correr con docker, para ejecutar el mismo solo es necesario ejecutar el comando `docker-compose up -d`, este generara las imagenes correspondientes y ejecutara los contenedores.


