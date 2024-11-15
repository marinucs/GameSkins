# GameSkinsAPI

API que permite a los usuarios consultar, adquirir, modificar y eliminar skins de un videojuego.

## Tabla de contenidos

- [Instrucciones](#instrucciones)
  - [Clona el repositorio](#clona-el-repositorio)
  - [Ejecuta la aplicación](#ejecuta-la-aplicación)
- [Documentación de la API](#documentación-de-la-api)
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


## Instrucciones

Sigue los pasos a continuación para clonar el repositorio y ejecutar la aplicación.

### Clona el repositorio

```bash
git clone https://github.com/marinucs/GameSkins.git
  ```

### Ejecuta la aplicación

Aquí tienes un par de sugerencias.

`Gradle`
```bash
./gradlew bootRun
```

`JAR`

```bash
java -jar build/libs/GameSkins-0.0.1-SNAPSHOT.jar
  ```

---

# Documentación de la API

En esta sección se detallan los endpoints disponibles en la API, las herramientas disponibles para interactuar con ella, y las pruebas que puedes realizar.

## Herramientas disponibles

Aquí tienes algunas ideas.

###  Opción 1. Usa `Swagger`
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

Hasta aquí la documentación de la API. ¡Gracias por leer! ^o^
