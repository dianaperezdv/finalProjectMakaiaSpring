# Proyecto de Mensajería Express :package: :post_office: <img alt="Java" height="40" width="40" src="https://media.giphy.com/media/KeJXqoqlUE2NSHUYER/giphy.gif">

El proyeco "Mensajería Express APIREST" es una aplicación realizada con Spring Boot que permite a los usuarios hacer seguimiento y control de los clientes, empleados y de los paquetes envíados, todo a través de una interfaz web. La API utiliza el protocolo HTTP para permitir que los usuarios interactúen con el sistema a través de una serie de endpoints de API definidos.

El gestor de dependencias utilizados es Gradle-Groovy y la versión de Java es Java 11.
La base de datos cuenta con un motor en MySQL manejado con JPA e Hibernate. 
## Tecnologías utilizadas
- Spring Boot Framework.
- Gestor de dependencias: Gradle
- Lombok - anotaciones.
- Bases de datos: MySQL
- JPA e Hibernate.

## Seguridad
La aplicación cuenta con una seguridad básica de Spring Security, la cual maneja una autenticación y acceso por roles.
Para tener acceso a los endpoints de la API, se debe autenticar con un usuario y contraseña válidos. La aplicación tiene dos roles: `WRITE` y `READ`. El rol `WRITE` permite al usuario acceder a todos los endpoints de la API, mientras que el rol `READ` solo permite al usuario acceder a los endpoints de lectura.
En caso de desear probar todos los endpoints en su dispositivo, habiendo lanzado la aplicación desde el IDLE de su preferencia, se puede hacer uso de los siguientes datos de acceso:
- Usuario: `admin`
- Contraseña: `admin123`


## Configuración
Antes de comenzar, asegúrese de tener una base de datos configurada y actualice las credenciales de la base de datos en el archivo **application.properties**.
```java
spring.datasource.url=jdbc:mysql://localhost:8080/finalProject
spring.datasource.username=root
spring.datasource.password={password}
spring.jpa.hibernate.ddl-auto=update
spring.mvc.pathmatch.matching-strategy=ant-path-matcher
```
## Documentación <img align="center" alt="Pruebas" height="40" width="40" src="https://avatars.githubusercontent.com/u/7658037?s=280&v=4">
La documentación de la API se genera automáticamente con Swagger. Para acceder a la documentación, abra un navegador web y vaya a [Documentación Swagger](https://proyecto-integrador-production-f4fb.up.railway.app/swagger-ui/index.html).

## Patrón de Diseño
Este proyecto utiliza el patrón de diseño DTO (Data Transfer Object) para transferir datos entre las diferentes capas de la aplicación. Los DTO son objetos simples que contienen campos y métodos de acceso, y se utilizan para transferir datos entre los controladores y los servicios.


## Diagrama del Modelo Entidad-Relación <img align="center" alt="Pruebas" height="40" width="40" src="https://user-images.githubusercontent.com/115324147/233542530-c691174d-7f63-4ea9-8126-c3ecf520b2c2.png">
Este es el diagrama del modelo entidad-relación para la base de datos MySQL del proyecto de sistema de reservas:
![diagrama](subir imagen)

- La tabla **Cliente** contiene información sobre los clientes, como su cedula, nombre, apellido, celular, ciudad, correo electronico y dirección
- La tabla **Empleado** contiene información sobre los empleados, como su cedula, nombre, apellido, celular, ciudad, correo electronico, dirección, antiguedad, tipo de sangre y tipo de empleado.
- La tabla **Envio** contiene información sobre los envios, como el numero de guia, ciudad de destino, ciudad origen, dirección destino, estado del envio, hora de entrega, nombre de la persona, numero de la persona, valor del envio.
- La tabla **Paquete** contiene información sobre los paquetes, como el id del paquete, peso, tipo de paquete y valor declarado.

## Diagrama del proyecto por paquetes

```java
com.example.Proyecto-Integrador
├── Configurer
│   └── SwaggerConfig.java
├── Controllers
│   ├── ClienteController.java
│   ├── EmpleadoController.java
│   ├── EnvioController.java
├── DTO
│   ├── CambioEnvioDTO.java
│   ├── EmpleadoDTO.java
│   ├── EnvioCreadoDTO.java
│   ├── EnvioNuevoDTO.java
│   ├── EstadoEnvioDTO.java
│   └── PaqueteDto.java
├── Exception
│   ├── ApiExceptionHandler.java
│   └── ApiRequestException.java
├── Models
        ├──Enums
        │   ├── EstadoEnvio.java
        │   ├── TipoEmpleado.java
        │   └── TipoPaquete.java
│   ├── Empleado.java
│   ├── Envio.java
│   ├── Paquete.java
│   └── Usuario.java
├── Repositories
│   ├── ClienteRepository.java
│   ├── EmpleadoRepository.java
│   ├── ReservaRepository.java
│   └── PaqueteRepository.java
├── Segurity
│   └── WebSecurityConfig.java
├── Services
│   ├── ClienteService.java
│   ├── EmpleadoService.java
│   ├── EnvioService.java
└── FinalProjectApplication.java
```

El proyecto está organizado en cuatro paquetes principales, cada uno correspondiente a un microservicio:

- El paquete **Configurer** contiene las clases de configuración para la base de datos y Swagger.
- El paquete **Controller** contiene las clases controladoras para los microservicios de Cliente, Habitación y Envio.
- El paquete **Dto** contiene las clases DTO (Data Transfer Object) para los objetos Cliente, Habitación y Reserva, que se utilizan para transferir datos entre la capa de presentación y la capa de servicios.
- El paquete **Exception** contiene las clases de Excepciones (ApiExceptionHandler, ApiRequestException) para el manejo de errores.
- El paquete **Model** contiene las clases de entidades JPA (Java Persistence API) para los objetos Cliente, Empleado y Envio, que se utilizan para mapear las tablas de la base de datos.
- El paquete **Repository** contiene las interfaces de repositorios JPA para los objetos Cliente, Empleado y Envio, que se utilizan para interactuar con la base de datos.
- El paquete **Security** contiene la clase de seguridad que se utiliza para autenticación y autorización de seguridad
- El paquete **Service** contiene las clases de servicios para los microservicios de Cliente, Empleado y Envio, que contienen la lógica de negocio.
  La clase FinalProjectApplication es la clase principal del proyecto que se utiliza para iniciar la aplicación.

Además, hay un paquete adicional llamado common que contiene clases y utilidades compartidas por los microservicios.

## Microservicios <img align="center" alt="microservicio" height="40" width="40" src="https://user-images.githubusercontent.com/115324147/233541782-7b18ad4a-54d2-4304-945c-db24491a886e.png">

Este proyecto está dividido en tres microservicios diferentes: Cliente, Habitación y Reserva. Cada microservicio tiene su propia base de datos y API REST. La comunicación entre los microservicios se realiza a través de peticiones HTTP.

#### Cliente Microservicio
| Método Http   | EndPoint                                                         |Descripción   |
| ------------- |------------------------------------------------------------------|------------- |
|`POST`         | ``(http://localhost:8080/apiMensajeria/v1/clientes)``            |Crea un nuevo cliente|
|`PUT`          | ``(http://localhost:8080/apiMensajeria/v1/clientes)``            |Actualizar datos del cliente|
|`DELETE`         | ``(http://localhost:8080/apiMensajeria/v1/clientes/1234567890)`` |Eliminar cliente por cédula|
|`GET`       | ``(http://localhost:8080/apiMensajeria/v1/clientes/1234567890)`` |Obtener cliente por cédula|

Endpoints:
- **POST /cliente** - Crea un nuevo cliente

Ejemplo de petición:

```java
	{
        "cedula": 1211122222,
        "nombre":"Diana",
        "apellido":"Perez",
        "correoElectronico" : "dianis@gmail.com",
        "celular": "3134742493",
        "direccionResidencia" :"calle 46 # 69-90",
        "ciudadResidencia" : "Lima"
        }
```

- **PUT /cliente** - Actualizar datos del cliente

Ejemplo de petición:

```java
	{
        "cedula": 1211122222,
        "nombre":"Diana",
        "apellido":"Perez",
        "correoElectronico" : "camilita@gmail.com",
        "celular": "3134742493",
        "direccionResidencia" :"calle 46 # 69-90",
        "ciudadResidencia" : "Lima"
        }
```

#### Empleado Microservicio
| Método Http   | EndPoint                                                          |Descripción   |
| ------------- |-------------------------------------------------------------------|------------- |
|`POST`         | ``(http://localhost:8080/apiMensajeria/v1/empleados)``            |Crea un nuevo empleado|
|`PUT`          | ``(http://localhost:8080/apiMensajeria/v1/empleados)``            |Actualizar datos del empleado|
|`DELETE`         | ``(http://localhost:8080/apiMensajeria/v1/empleados/1234567890)`` |Eliminar cliente por cédula|
|`GET`       | ``(http://localhost:8080/apiMensajeria/v1/empleados/1234567890)`` |Obtener empleado por cédula|


Endpoints:
- **POST /empleado** - Crea un nuevo empleado

Ejemplo de petición:
```java
{
        "cedula": 1111123456,
        "nombre": "Pedro",
        "apellido": "Gomez",
        "correoElectronico": "pedrito@gmail.com",
        "celular": "3046303886",
        "direccionResidencia": "calle 56 #21-5",
        "ciudadResidencia": "Manizalez",
        "tipoDeSangreRH": "O+",
        "antiguedadEmpleado": 2,
        "tipoDeEmpleado": "CONDUCTOR",
        }
```

- **PUT /empleado** - Actualizar los datos de un empleado

Ejemplo de petición:

```java
{
        "cedula": 1111123456,
        "nombre": "Pedro",
        "apellido": "Gomez",
        "correoElectronico": "pedrito@gmail.com",
        "celular": "3046303886",
        "direccionResidencia": "calle 56 #21-5",
        "ciudadResidencia": "Manizalez",
        "tipoDeSangreRH": "O+",
        "antiguedadEmpleado": 2,
        "tipoDeEmpleado": "COORDINADOR",
        }
```

#### Envio Microservicio
| Método Http   | EndPoint                                             |Descripción   |
| ------------- |------------------------------------------------------|------------- |
|`POST`         | ``(http://localhost:8080/apiMensajeria/v1/envios)``  |Crea un nuevo envio|
|`PUT`          | ``(http://localhost:8080/api/v1/envios)``            |Actualizar datos del envio|
|`GET`       | ``(http://localhost:8080/api/v1/envios/W5PRXYa80C)``           |Obtener envio por número de guia|
|`GET`          | ``(http://localhost:8080/apiMensajeria/v1/envios/cliente`` |Obtener envios por estado|

Endpoints:
- **POST /envio** - Crea un nuevo envio

Ejemplo de petición:

```java
{
        "cedula":123456,
        "nombreRemitente": "Mateo",
        "ciudadOrigen":"Medellin",
        "ciudadDestino":"Bogota",
        "direccionDestino": "calle 46 # 69-90",
        "nombrePersona" : "Juan Manuel",
        "numeroPersona" : 30463,
        "peso": 2.0,
        "valorDeclarado": 15000
        }
```

- **PUT /envio** - Actualizar estado de envio

Ejemplo de petición:

```java
{
"numGuia": "W5PRXYa80C",
"estadoEnvio":"EN RUTA"
}
```
## Postman <img align="center" alt="Pruebas" height="40" width="40" src="https://uxwing.com/wp-content/themes/uxwing/download/brands-and-social-media/postman-icon.png">
Para probar los endpoints de la aplicación, se recomienda el uso de la herramienta Postman. Esta herramienta permite hacer peticiones HTTP a la API y recibir las respuestas en tiempo real.

![postman](agregar imagen)

## Pruebas Unitarias <img align="center" alt="Pruebas" height="40" width="40" src="https://media.giphy.com/media/1sMGC0XjA1Hk58wppo/giphy.gif">
Se han incluido pruebas unitarias utilizando Mockito y JUnit para asegurar que los microservicios de Cliente, Habitación y Reserva funcionan correctamente.
Las pruebas unitarias se encuentran en la carpeta src/test/java del proyecto.

## Integración continua con GitHub Actions <img align="center" alt="Integracion" height="50" width="50" src="https://media.giphy.com/media/Vnk8f29XU6GSZK8uGJ/giphy.gif">
Este proyecto cuenta con integración continua mediante Github Actions. Cada vez que se realiza un push al repositorio, se ejecutan las pruebas unitarias y se crea un archivo JAR ejecutable.

## Despliegue del microservicio (Railway) <img align="center" alt="Depliegue" height="40" width="40" src="https://media.giphy.com/media/tzv65Sc3tBQWNMSI3B/giphy.gif">
Este proyecto cuenta con un despliegue del microservicio mediante Railway. Conecta directamente con Github y nuestra base de datos(MySQL).