# GameSkinsAPI

API que permite a los usuarios consultar, adquirir, modificar y eliminar skins de un videojuego.

Las skins disponibles se leen desde un archivo en formato JSON y 
aquellas que son adquiridas por el usuario, se almacenan en una base de datos embebida `SQLite`.

---
## Tabla de contenidos

- [Información sobre el repositorio](#información-sobre-el-repositorio)
  - [Tecnologías utilizadas](#tecnologías-utilizadas)
  - [Dependencias importantes](#dependencias-importantes)
  - [Otras herramientas y características](#otras-herramientas-y-características)
  - [Script de construcción y configuración](#script-de-construcción-y-configuración)
- [Instrucciones para la ejecución](#instrucciones-para-la-ejecución)
  - [Clona el repositorio](#clona-el-repositorio)
  - [Ejecuta la aplicación](#ejecuta-la-aplicación)
- [Interactúa con la API](#interactúa-con-la-api)
  - [Herramientas disponibles](#herramientas-disponibles)
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

---

## Información sobre el repositorio

A continuación, se detallan las tecnologías utilizadas en el desarrollo de la API, las dependencias importantes, y otras herramientas y características.

### Tecnologías utilizadas

- **Java:** Versión 23
- **Spring Boot:** Versión 3.3.5
- **Gradle:** Sistema de compilación y gestión de dependencias.
- **Hibernate:** Implementación de JPA y dialectos comunitarios.
- **SQLite JDBC:** Base de datos embebida (`sqlite-jdbc` versión 3.47.0.0).
- **Lombok:** Para simplificar el código eliminando el "boilerplate".
- **SpringDoc OpenAPI:** Para documentación de la API (`springdoc-openapi-starter-webmvc-ui` versión 2.2.0).
- **Junit 5:** Para realizar las pruebas de la aplicación.
- **Spring REST Docs:** Para la generación de documentación.

### Dependencias importantes

- **Spring Boot Starter Data JPA y Web**: Para la gestión de datos y desarrollo de la API REST.
- **Jackson Databind (versión 2.15.2)**: Para el mapeo de objetos Java a JSON y viceversa.
- **SQLite JDBC**: Base de datos ligera para almacenamiento de datos.
- **Lombok**: Para anotaciones que facilitan la escritura del código.
- **Swagger/OpenAPI**: Para documentar y probar la API de forma interactiva.

### Otras herramientas y características

- **Asciidoctor**: Para generar documentación a partir de snippets de pruebas.
- **Swagger**: Permite la documentación interactiva de la API.
- **Pruebas con JUnit**: Uso de JUnit 5 para pruebas unitarias e integración.

### Script de construcción y configuración

- **build.gradle**: Define la configuración del proyecto, incluyendo el uso de `Java 23`, dependencias como Spring Boot y otras bibliotecas necesarias.
- **settings.gradle**: Nombre del proyecto definido como `GameSkins`.

---

## Instrucciones para la ejecución

- **Requisitos previos**: Java 23 y Gradle instalados en el sistema.
- **Ejecutar la aplicación**: Puedes clonar el repositorio y luego utilizar el comando `./gradlew bootRun` para iniciar la aplicación.

### Clona el repositorio

```bash
git clone https://github.com/marinucs/GameSkins.git
  ```

### Ejecuta la aplicación

Por defecto, la aplicación de Spring Boot con un servidor Tomcat integrado se ejecutará en el puerto 8080. Para cambiar el puerto, modifica la propiedad `server.port` en el archivo `application.properties` que se encuentra en la carpeta `src/main/resources`.

```bash
./gradlew bootRun
```

---

# Interactúa con la API

En esta sección se detallan los endpoints disponibles en la API, las herramientas disponibles para interactuar con ella, y las pruebas que puedes realizar.

## Herramientas disponibles

Aquí tienes algunas ideas.

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
  // Más skins...
]
```

---

### POST /skins/buy

Permite a los usuarios adquirir una skin específica mediante su ID.

```bash
curl -X POST "http://localhost:8080/skins/buy?id=1"
```

**Respuesta exitosa:**

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
  // Más skins compradas...
]
```

---

### PUT /skins/color

Permite a los usuarios cambiar el color de una skin comprada especificando su ID.

Las opciones disponibles son: RED, BLUE, GREEN, YELLOW, BLACK, WHITE, ORANGE, METALLIC, GOLDEN, PURPLE

```bash
curl -X PUT "http://localhost:8080/skins/color?id=1&color=Golden"
```

**Respuesta exitosa:**

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

**Respuesta exitosa:**

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

Hasta aquí la documentación de la API. ¡Gracias por leer! ^o^
