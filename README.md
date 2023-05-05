# Proyecto de Mensajería Express  <img alt="Java" height="40" width="60" src="https://static.vecteezy.com/system/resources/previews/012/980/756/original/truck-with-stop-watch-express-delivery-icon-for-shipping-services-ecomers-signs-illustration-png.png">

El proyeco "Mensajería Express APIREST" es una aplicación realizada con Spring Boot que permite a los usuarios hacer seguimiento y control de los clientes, empleados y de los paquetes envíados, todo a través de una interfaz web. La API utiliza el protocolo HTTP para permitir que los usuarios interactúen con el sistema a través de una serie de endpoints de API definidos.

El gestor de dependencias utilizado es Gradle-Groovy y la versión de Java es Java 11.
La base de datos cuenta con un motor en MySQL manejado con JPA e Hibernate. 
## Tecnologías utilizadas
- Spring Boot Framework.
- Gestor de dependencias: Gradle
- Lombok - anotaciones.
- Bases de datos: MySQL
- JPA e Hibernate.

## Seguridad <img align="center" alt="Pruebas" height="40" width="40" src="https://www.baeldung.com/wp-content/uploads/2020/03/lsso-course-page-shield-elements-icon.png">
La aplicación cuenta con una seguridad básica de Spring Security, la cual maneja una autenticación y acceso por roles.
Para tener acceso a los endpoints de la API, se debe autenticar con un usuario y contraseña válidos. La aplicación tiene dos roles: `WRITE` y `READ`. El rol `WRITE` permite al usuario acceder a todos los endpoints de la API, mientras que el rol `READ` solo permite al usuario acceder a los endpoints de lectura.
En caso de desear probar todos los endpoints en su dispositivo, habiendo lanzado la aplicación desde el IDLE de su preferencia, se puede hacer uso de los siguientes datos de acceso:
- Usuario: `admin`
- Contraseña: `admin123`

## CI:CD Integración continua y despliegue continuo <img align="center" alt="Pruebas" height="40" width="100" src="https://miro.medium.com/v2/resize:fit:612/0*KB2oUiVeUuC_zlAc.png"> 
La integración continúa es una práctica para automatizar la integración de los cambios del código, el proyecto hace uso de GitHub Actions. Al hacer un push al repositorio, se limpian los datos anteriores para garantizar que el despliegue sea de la versión correcta, se realizan las pruebas unitarias para garantizar que el proyecto continúe funcionado y se crea un archivo JA ejecutable.

Este archivo JAR se despliega automáticamente en el servicio de Railway, donde se ejecuta la aplicación. El servicio de Railway también aloja la base de datos MySQL utilizada por la aplicación. Railway ofrece un despliegue continúo que, además de permitir que los cambios se implemente automaticamente ofrece la posibilidad de acceder al microservicio en cualquier momento y en cualquier lugar.

## Configuración <img align="center" alt="Pruebas" height="40" width="40" src="https://cdn-icons-png.flaticon.com/512/6671/6671938.png">
Antes de comenzar, asegúrese de tener una base de datos configurada y actualice las credenciales de la base de datos en el archivo **application.properties**.
```java
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:8080/apiMensajeria/v1/
spring.datasource.username=root
spring.datasource.password={password}
spring.jpa.hibernate.ddl-auto=update
spring.mvc.pathmatch.matching-strategy=ant-path-matcher
```
## Documentación <img align="center" alt="Pruebas" height="40" width="40" src="https://www.alura.com.br/assets/api/cursos/512/swagger-documentando-apis.png">
La documentación de la API se genera automáticamente con Swagger. Este servicio ofrece una documentación que es fácil de entender y es amigable con todo tipo de usuarios.

Para acceder a la documentación, abra un navegador web y vaya a [Documentación Swagger](https://finalprojectmakaiaspring-production.up.railway.app/swagger-ui/index.html#/).

## Pruebas Unitarias <img align="center" alt="Pruebas" height="40" width="40" src="https://media.giphy.com/media/1sMGC0XjA1Hk58wppo/giphy.gif">
Se han incluido pruebas unitarias utilizando Mockito y JUnit para asegurar que los microservicios de Cliente, Envío y Empleado funcionan correctamente.
Las pruebas unitarias se encuentran en la carpeta src/test/java del proyecto.

## Patrón de Diseño
Este proyecto utiliza el patrón de diseño DTO (Data Transfer Object) para transferir datos entre las diferentes capas de la aplicación. Los DTO son objetos simples que contienen campos y métodos de acceso, y se utilizan para transferir datos entre los controladores y los servicios.

En este caso se utilizan para facilitar el manejo de las respuestas 

## Diagrama del Modelo Entidad-Relación <img align="center" alt="MySQL" height="40" width="40" src="https://d1.awsstatic.com/asset-repository/products/amazon-rds/1024px-MySQL.ff87215b43fd7292af172e2a5d9b844217262571.png">
Este es el diagrama del modelo entidad-relación para la base de datos MySQL del proyecto de sistema de reservas:
![diagrama](https://github.com/dianaperezdv/finalProjectMakaiaSpring/blob/4781385d15d368650a77be796ec167559e58cdcb/DB_DIAGRAM.png)

-
## Diagramas del proyecto por paquetes

```java
com.example.finalProject
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
│   ├── Cliente
│   ├── Empleado.java
│   ├── Envio.java
│   ├── Paquete.java
│   └── Usuario.java
├── Repositories
│   ├── ClienteRepository.java
│   ├── EmpleadoRepository.java
│   ├── EnvioRepository.java
│   └── PaqueteRepository.java
├── Segurity
│   └── WebSecurityConfig.java
├── Services
│   ├── ClienteService.java
│   ├── EmpleadoService.java
│   ├── EnvioService.java
└── FinalProjectApplication.java
```

El proyecto está organizado en cuatro paquetes principales, cada uno correspondiente a un nivel de abstracción diferente:

- El paquete **Configurer** contiene las clases de configuración para la base de datos y Swagger.


- El paquete **Controllers** contiene las clases controladoras para los servicios de Cliente, Habitación y Envio.
  - Los controladores contienen los endpoint para recibir peticiones HTTP, a través de estas el usuario realiza cambios en la base de datos.


- El paquete **DTO** contiene las clases DTO (Data Transfer Object) para los objetos Cliente, Empleado y Envío que se utilizan para transferir datos entre la capa de presentación y la capa de servicios.


- El paquete **Exception** contiene las clases de Excepciones (ApiExceptionHandler, ApiRequestException) para personalizar el manejo de errores y envíar mensajes que permitan al usuario comprender las respuestas del servidor.
- 
- El paquete **Models** contiene las clases de entidades JPA (Java Persistence API) para los objetos Cliente, Empleado y Envio, que se utilizan para mapear las tablas de la base de datos.
  - Dentro del paquete Models, hay un subpaquete llamado Enums que contiene las clases de enumeración para los objetos EstadoEnvio, TipoEmpleado y TipoPaquete. Un enum es una clase especial que representa un grupo de constantes (variables inmutables, como valores de cadena, valores enteros, etc.). Los enums se utilizan para representar datos que no cambian con el tiempo, como los días de la semana, los meses del año, etc. En este caso los usamos para limitar los valores posibles de los atributos EstadoEnvio, TipoEmpleado y TipoPaquete, son atributos que solamente permiten 3 datos, por tanto se benefician del uso de enum y evitan errores en la base de datos.


- El paquete **Repositories** contiene las interfaces de repositorios JPA para los objetos Cliente, Empleado y Envio, que se utilizan para interactuar con la base de datos.
  - Por medio de JPA podemos comunicarnos con la base de datos sin necesidad de utilizar querys SQL, sino que utilizamos métodos de JPA que nos permiten realizar las operaciones básicas de una base de datos (insertar, actualizar, eliminar, consultar). En este caso, utilizamos los métodos de JPA para realizar las operaciones CRUD (Create, Read, Update, Delete) en la base de datos.


- El paquete **Security** contiene la clase de seguridad que se utiliza para autenticación y autorización de seguridad
  - La clase WebSecurityConfig extiende la clase WebSecurityConfigurerAdapter, que proporciona métodos de configuración por defecto para la seguridad HTTP. Sobreescribimos algunos de estos métodos para personalizar la configuración de seguridad. Autorizamos los endpoints para que los usuarios puedan acceder a ellos según su rol.


- El paquete **Services** contiene las clases de servicios para los servicios de Cliente, Empleado y Envio, que contienen la lógica de negocio. Estos servicios son implementados por los endpoints de los controladores. 


- La clase FinalProjectApplication es la clase principal del proyecto que se utiliza para iniciar la aplicación.

## Diagramas de los diferentes paquetes con sus métodos

![diagrama](https://github.com/dianaperezdv/finalProjectMakaiaSpring/blob/02262e149c562949f5005a0f38a9f900b02a5bc1/Diagramas/Models.PNG)
![diagrama](https://github.com/dianaperezdv/finalProjectMakaiaSpring/blob/02262e149c562949f5005a0f38a9f900b02a5bc1/Diagramas/controladores.PNG)
![diagrama](https://github.com/dianaperezdv/finalProjectMakaiaSpring/blob/02262e149c562949f5005a0f38a9f900b02a5bc1/Diagramas/servicios.PNG)
![diagrama](https://github.com/dianaperezdv/finalProjectMakaiaSpring/blob/02262e149c562949f5005a0f38a9f900b02a5bc1/Diagramas/dto.PNG)
![diagrama](https://github.com/dianaperezdv/finalProjectMakaiaSpring/blob/29cda521613c3ee2d6d5932247ee98091fb5b11b/Diagramas/ConfigUML.png)



## Endpoints <img align="center" alt="microservicio" height="40" width="40" src="https://user-images.githubusercontent.com/115324147/233541782-7b18ad4a-54d2-4304-945c-db24491a886e.png">

El proyecto cuenta con 3 controladores, a continuación indicaremos los endpoints de cada uno de ellos con algunos ejemplos de las peticiones:

#### ClienteController
| Método Http   | EndPoint                                                         | Descripción                  |
| ------------- |------------------------------------------------------------------|------------------------------|
|`POST`         | ``(http://localhost:8080/apiMensajeria/v1/clientes)``            | Crea un nuevo cliente        |
|`PUT`          | ``(http://localhost:8080/apiMensajeria/v1/clientes)``            | Actualizar datos del cliente |
|`DELETE`         | ``(http://localhost:8080/apiMensajeria/v1/clientes/1234567890)`` | Eliminar cliente por cédula  |
|`GET`       | ``(http://localhost:8080/apiMensajeria/v1/clientes/1234567890)`` | Obtener cliente por cédula   |
|`GET`       | ``(http://localhost:8080/apiMensajeria/v1/clientes)``            | Obtener todos los clientes   |
| `DELETE`    | ``(http://localhost:8080/apiMensajeria/v1/clientes/{cedula}``    | Eliminar un cliente          |



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

- **PUT /cliente** - Actualizar datos del cliente. Solicita los datos del cliente con el dato actualizado.

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

#### EmpeladoController
| Método Http   | EndPoint                                                          | Descripción                   |
| ------------- |-------------------------------------------------------------------|-------------------------------|
|`POST`         | ``(http://localhost:8080/apiMensajeria/v1/empleados)``            | Crea un nuevo empleado        |
|`PUT`          | ``(http://localhost:8080/apiMensajeria/v1/empleados)``            | Actualizar datos del empleado |
|`DELETE`         | ``(http://localhost:8080/apiMensajeria/v1/empleados/1234567890)`` | Eliminar cliente por cédula   |
|`GET`       | ``(http://localhost:8080/apiMensajeria/v1/empleados/1234567890)`` | Obtener empleado por cédula   |
|`GET`       | ``(http://localhost:8080/apiMensajeria/v1/empleados)``            | Obtener todos los empleados   |
| `DELETE`    | ``(http://localhost:8080/apiMensajeria/v1/empleados/{cedula}``    | Eliminar un empleado          |



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

- **GET /empleado/{cedula}** - Obtener un empleado por cédula

Respuesta esperada:

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

#### Envio Controller
| Método Http | EndPoint                                                                         | Descripción                                     |
|-------------|----------------------------------------------------------------------------------|-------------------------------------------------|
| `POST`      | ``(http://localhost:8080/apiMensajeria/v1/envios)``                              | Crea un nuevo envio                             |
| `PUT`       | ``(http://localhost:8080/api/v1/envios/{idGuia})``                               | Actualizar datos del estado del envío           |
| `GET`       | ``(http://localhost:8080/api/v1/envios/{idGuia})``                               | Obtener envio por número de guia                |
| `GET`       | ``(http://localhost:8080/apiMensajeria/v1/envios``                               | Obtener lista de todos los envíos               |
| `GET`       | ``(http://localhost:8080/apiMensajeria/v1/envios/cliente/{idGuia}``              | Obtener lista de todos los envíos de un cliente |
| `GET`       | ``(http://localhost:8080/apiMensajeria/v1/envios/cliente/{idGuia}``              | Obtener lista de todos los envíos de un cliente |
| `GET`       | ``(http://localhost:8080/apiMensajeria/v1/envios?estado=RECIBIDO&cedula=1887654321`` | Obtener lista de todos los envíos por estado    |
| `DELETE`    | ``(http://localhost:8080/apiMensajeria/v1/envios/{idGuia}`` | Eliminar un envío                               |

Endpoints:
- **POST /envio** - Crea un nuevo envio
En el siguiente diagrama podemos ver algunas validaciones que se realizan para crear un envío.
![diagrama](https://github.com/dianaperezdv/finalProjectMakaiaSpring/blob/e6d97ef0c3a1d3f223333b6dcbeada28e76780fb/CreacionEnvio.png)

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

Respuesta esperada:

```java
{
    "idGuia": "W5PRXYa80C",
    "ultimoEstado":"EN RUTA"
}
```


