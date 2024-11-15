# GameSkinsAPI

API que permite a los usuarios consultar, adquirir, modificar y eliminar skins de un videojuego.

Las skins disponibles se leen desde un archivo en formato JSON y 
aquellas que son adquiridas por el usuario, se almacenan en una base de datos embebida `SQLite`.

---
## Tabla de contenidos

- [Ejecuta la aplicación](#ejecuta-la-aplicación)
- [Interactúa con la API](#interactúa-con-la-api)
    - [Opción 1. Usa `Swagger`](#opción-1-usa-swagger)
    - [Opción 2. Método `cURL`](#opción-2-método-curl)
    - [Opción 3. Utiliza `Postman` o `Insomnia`](#opción-3-utiliza-postman-o-insomnia)
  - [Endpoints](#endpoints)
    - [GET /skins/available](#get-skinsavailable)
    - [POST /skins/buy](#post-skinsbuy)
    - [GET /skins/myskins](#get-skinsmyskins)
    - [PUT /skins/color](#put-skinscolor)
    - [DELETE /skins/delete/{id}](#delete-skinsdeleteid)
    - [GET /skins/getskin/{id}](#get-skinsgetskinid)
  - [Pruebas y verificación](#pruebas-y-verificación)
    - [Skin no encontrada al comprar](#skin-no-encontrada-al-comprar)
    - [Skin ya adquirida](#skin-ya-adquirida)
    - [Actualizar color de skin no comprada](#actualizar-color-de-skin-no-comprada)
    - [Introducir un color inválido](#introducir-un-color-inválido)
    - [Eliminar una skin no comprada](#eliminar-una-skin-no-comprada)
  - [Detener la aplicación](#detener-la-aplicación)
    - [Liberar puerto](#liberar-puerto)
- [Información sobre el repositorio](#información-sobre-el-repositorio)
  - [Desarrollo de aplicación](#desarrollo-de-aplicación)
  - [Gestión de datos](#gestión-de-datos)
  - [Documentación de la API](#documentación-de-la-api)
  - [Simplificación de código](#simplificación-de-código)
  - [Pruebas y validación](#pruebas-y-validación)
  - [Manejo de datos y conversión](#manejo-de-datos-y-conversión)

---

## Ejecuta la aplicación

Clona el repositorio y usa `./gradlew bootRun` para iniciar la aplicación.

```bash
git clone https://github.com/marinucs/GameSkins.git
  ```

Por defecto, la aplicación de Spring Boot con un servidor Tomcat integrado se ejecutará en el puerto 8080. Para cambiar el puerto, modifica la propiedad `server.port` en el archivo `application.properties` que se encuentra en la carpeta `src/main/resources`.

```bash
./gradlew bootRun
```

---

## Interactúa con la API

En esta sección se detallan los endpoints disponibles en la API, las herramientas disponibles para interactuar con ella, y las pruebas que puedes realizar.

### Opción 1. Usa `Swagger`
Abre el navegador y accede a la URL [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

### Opción 2. Método `cURL`
Utiliza `cURL` para realizar solicitudes HTTP.

### Opción 3. Utiliza `Postman` o `Insomnia`

Importa la colección de solicitudes HTTP desde el archivo `GameSkins_API_Collection.json` situado en la raíz del proyecto.

---

## Endpoints

A continuación, se detallan los endpoints disponibles en la API.

### GET /skins/available

Obtiene una lista de todas las skins disponibles para comprar.

```bash
curl -X GET "http://localhost:8080/skins/available"
```

**Respuesta esperada:**

```json
[
  {
    "id": 1,
    "name": "Ashe Crystal Archer",
    "type": "Epic",
    "price": 1350,
    "color": "Blue"
  },
  {
    "id": 2,
    "name": "Zed Crimson Shadow",
    "type": "Legendary",
    "price": 1820,
    "color": "Red"
  },
  {
    "id": 3,
    "name": "Lux Lady of Flames",
    "type": "Epic",
    "price": 1350,
    "color": "Orange"
  }
]
```

---

### POST /skins/buy

Permite a los usuarios adquirir una skin específica mediante su ID.

```bash
curl -X POST "http://localhost:8080/skins/buy?id=1"
```

**Respuesta esperada:**

```json
{
  "id": 1,
  "name": "Ashe Crystal Archer",
  "type": "Epic",
  "price": 1350,
  "color": "Blue"
}
```

---

### GET /skins/myskins

Obtiene una lista de todas las skins que el usuario ha adquirido.

```bash
curl -X GET "http://localhost:8080/skins/myskins"
```

**Respuesta esperada:**

```json
[
  {
    "id": 1,
    "name": "Ashe Crystal Archer",
    "type": "Epic",
    "price": 1350,
    "color": "Blue"
  }
]
```

---

### PUT /skins/color

Permite a los usuarios cambiar el color de una skin comprada especificando su ID.

Las opciones disponibles son: RED, BLUE, GREEN, YELLOW, BLACK, WHITE, ORANGE, METALLIC, GOLDEN, PURPLE

```bash
curl -X PUT "http://localhost:8080/skins/color?id=1&color=Golden"
```

**Respuesta esperada:**

```json
{
  "id": 1,
  "name": "Ashe Crystal Archer",
  "type": "Epic",
  "price": 1350,
  "color": "Golden"
}
```

---

### DELETE /skins/delete/{id}

Permite a los usuarios eliminar una skin comprada mediante su ID.

```bash
curl -X DELETE "http://localhost:8080/skins/delete/1"
```

**Respuesta esperada:**

- **Código de estado:** `204 No Content`

  *No se devuelve contenido en la respuesta.*

---

### GET /skins/getskin/{id}

Obtiene los detalles de una skin específica utilizando su ID.

```bash
curl -X GET "http://localhost:8080/skins/getskin/1"
```

**Respuesta esperada:**

```json
{
  "id": 1,
  "name": "Ashe Crystal Archer",
  "type": "Epic",
  "price": 1350,
  "color": "Golden"
}
```

---

## Pruebas y verificación

### **Skin no encontrada al comprar**

```bash
curl -X POST "http://localhost:8080/skins/buy?id=999"
```

**Respuesta esperada:**

```json
{
    "timestamp": "2024-04-27T12:34:56.789",
    "message": "Skin with ID 999 not found.",
    "details": "uri=/skins/buy"
}
```

**Código de estado HTTP:** `404 Not Found`

---

### **Skin ya adquirida**

Primero, compra la skin con ID 1:

```bash
curl -X POST "http://localhost:8080/skins/buy?id=1"
```

Luego, intenta comprarla nuevamente:

```bash
curl -X POST "http://localhost:8080/skins/buy?id=1"
```

**Respuesta esperada:**

```json
{
    "timestamp": "2024-04-27T12:35:56.789",
    "message": "Skin with ID 1 has already been purchased.",
    "details": "uri=/skins/buy"
}
```

**Código de estado HTTP:** `400 Bad Request`

---

### **Actualizar color de skin no comprada**

```bash
curl -X PUT "http://localhost:8080/skins/color?id=999&color=Golden"
```

**Respuesta esperada:**

```json
{
    "timestamp": "2024-04-27T12:36:56.789",
    "message": "Skin with ID 999 has not been purchased and cannot be modified or deleted.",
    "details": "uri=/skins/color"
}
```

**Código de estado HTTP:** `400 Bad Request`

---

### **Introducir un color inválido**

```bash
curl -X PUT "http://localhost:8080/skins/color?id=999&color=Golden"
```

**Respuesta esperada:**

```json
{
    "timestamp": "2024-04-27T12:36:56.789",
    "message": "Skin with ID 999 has not been purchased and cannot be modified or deleted.",
    "details": "uri=/skins/color"
}
```

**Código de estado HTTP:** `400 Bad Request`

---

### **Eliminar una skin no comprada**

```bash
curl -X DELETE "http://localhost:8080/skins/delete/999"
```

**Respuesta esperada:**

```json
{
    "timestamp": "2024-04-27T12:37:56.789",
    "message": "Skin with ID 999 has not been purchased and cannot be modified or deleted.",
    "details": "uri=/skins/delete/999"
}
```

**Código de estado HTTP:** `400 Bad Request`

---

## Detener la aplicación

Para detener una aplicación Spring Boot que has ejecutado con el comando `./gradlew bootRun`, haz `CTRL + C` en la terminal.

### Liberar puerto

En el caso de que el puerto siga ocupado después de detener la aplicación, puedes liberarlo identificando el PID del proceso y deteniéndolo.

En **Linux/macOS**:
  ```sh
  lsof -i :8080
  ```

En **Windows**:
  ```cmd
  netstat -ano | findstr :8080
  ```

Una vez identificado el PID, ejecuta el siguiente comando para detener el proceso.

En **Linux/macOS**:
  ```sh
  kill -9 00000
  ```

En **Windows**:
  ```cmd
  taskkill /PID 00000 /F
  ```

---

## Información sobre el repositorio

A continuación, se detallan las tecnologías utilizadas en el desarrollo de la API, las dependencias importantes, y otras herramientas y características.

### Desarrollo de aplicación

- **Java (versión 23)**: Lenguaje de programación principal para la aplicación.
- **Spring Boot (versión 3.3.5)**: Framework para el desarrollo de aplicaciones RESTful con características preconfiguradas.
- **Gradle**: Sistema de compilación y gestión de dependencias para automatizar la construcción del proyecto.

### Gestión de datos

- **Hibernate**: Implementación de JPA (Java Persistence API) que facilita la interacción con bases de datos, incluyendo soporte para dialectos comunitarios.
- **Spring Boot Starter Data JPA**: Parte del ecosistema de Spring que ayuda a manejar la persistencia de datos con Hibernate.
- **SQLite JDBC (versión 3.47.0.0)**: Base de datos embebida ligera que permite almacenar los datos de la aplicación sin necesidad de un servidor de base de datos independiente.

### Documentación de la API

- **SpringDoc OpenAPI (versión 2.2.0)**: Utilizado para generar documentación interactiva de la API REST (con soporte Swagger).
- **Swagger/OpenAPI**: Herramienta para documentar y probar la API de manera interactiva.
- **Asciidoctor**: Permite generar documentación HTML o PDF a partir de fragmentos de pruebas automatizadas y ejemplos de uso.

### Simplificación de código

- **Lombok**: Biblioteca que elimina la necesidad de escribir código repetitivo (boilerplate) en Java mediante anotaciones, como getters, setters y constructores.

### Pruebas y validación

- **JUnit 5**: Framework para la creación y ejecución de pruebas unitarias e integradas.
- **Spring REST Docs**: Herramienta para generar documentación de la API a partir de las pruebas, facilitando la comprensión y el uso de los servicios REST.

### Manejo de datos y conversión

- **Jackson Databind (versión 2.15.2)**: Biblioteca utilizada para la serialización/deserialización de objetos Java a JSON y viceversa, facilitando el intercambio de datos entre la API y sus consumidores.

---
Hasta aquí la documentación de la API. ¡Gracias por leer! ^o^
