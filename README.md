# Proyecto: Evaluación DEVSU Java

Este proyecto es un entregable de un ejercicio Práctico java

Contratos:
- http://localhost:8080/swagger-ui/index.html
- http://localhost:8081/swagger-ui/index.html

Utiliza una BD Postgresql deployada en Azure, ya con la conexion lista en los properties (Temporal por una semana o hasta ser considerado para su eliminación)., En caso de ser una nueva conexion se tiene el archivo: **_BaseDatos.sql**_ con la creación de la BD, tablas y data inicial de los catalogos como tipo de movimiento y tipo de cuenta.

Contiene un archivo  **_DevSup-PT-DarwinQuispe.postman_collection.json**_ el cual es una colección de Postman con las apis a consumir segun indicaciones de la prueba, y con data dummy necesaria, en la documentación swagger se podra tener el detalle de lo que se requiere para cada servicios, así como la respuesta del mismo.

Los proyectos necesarios para correr el proyecto pueden ser clonados desde este repositorio o mediante el uso de las imagenes subidas a dockerHub:
https://hub.docker.com/u/dardan09

- dardan09/ddqs-devsu-client
- dardan09/ddqs-devsu-movement
- dardan09/ddqs-devsu-discovery-server
- dardan09/ddqs-devsu-cloud-gateway

Apis principales
## Servicio 1: Client
Este servicio permite la gestion de clientes

### Ruta de Consumo
POST:  localhost:8080/v1/client

Payload :
```json
{
    "name": "Darwin Quispe",
    "gender": "Masculino",
    "age": 30,
    "identification": "12345670",
    "address": "Arequipa, Perú",
    "telephone": "+51 93057519615",
    "password": "123456",
    "state": true
}
```

Respuesta esperada:
```json
{
    "mensaje": "Operación Exitosa",
    "data": null
}
```

## Servicio 2: Crear Cuenta
Este servicio permite crear cuenta asociada a un cliente

### Ruta de Consumo
POST:  localhost:8081/v1/account

Payload:
```json
{
    "idClient": 1,
    "idAccountType": 2,
    "accountNumber": "478759",
    "initialBalance": 45.5,
    "status": true
}
```

Respuesta esperada:
```json
{
    "mensaje": "Operación Exitosa",
    "data": null
}
```

## Servicio 3: Crear Movimiento
Este servicio permite crear un movimiento asociado a una cuenta 

### Ruta de Consumo
POST:  localhost:8081/v1/movement

Payload:
```json
{
    "movementDate": "31/08/2024",
    "idAccount": 1,
    "idMovementType": 2,
    "value": 15.00
}

```

Respuesta esperada:
```json
{
    "mensaje": "Operación Exitosa",
    "data": null
}
```

## Servicio 4: Reportes
Este servicio permit mostrar los reporte de movimientos

### Ruta de Consumo
POST:  localhost:8081/v1/movement/reportes?idClient=1&fromDate=20/07/2024&toDate=03/09/2024

Payload:
```json
{
    "idClient": 1,
    "idAccountType": 2,
    "accountNumber": "478759",
    "initialBalance": 45.5,
    "status": true
}
```

Respuesta esperada:
```json
{
    "mensaje": "Operación Exitosa",
    "data": [
        {
            "idMovement": 6,
            "idAccount": 1,
            "idClient": 1,
            "fecha": "31/08/2024",
            "cliente": "Darwin Quispe",
            "numeroCuenta": "478759",
            "tipo": "Corriente",
            "saldoInicial": 45.50,
            "estado": true,
            "tipoMovimiento": "Deposito",
            "movimiento": 15.00,
            "saldoDisponible": 135.50
        },
        {
            "idMovement": 5,
            "idAccount": 1,
            "idClient": 1,
            "fecha": "31/08/2024",
            "cliente": "Darwin Quispe",
            "numeroCuenta": "478759",
            "tipo": "Corriente",
            "saldoInicial": 45.50,
            "estado": true,
            "tipoMovimiento": "Deposito",
            "movimiento": 15.00,
            "saldoDisponible": 120.50
        }
    ]
}
```


## Ejecución del Proyecto

1. Clona el repositorio o descarga los archivos.

2. Abre una terminal en el directorio raíz del proyecto.

3. Ejecuta el siguiente comando para compilar y ejecutar la aplicación:

4. mvn spring-boot:run
