# Practica 1 - Implementación Java
La practica usa JDBC para manejar la base de datos. El programa asume que en el
sistema esta instalado mariadb y que se ha creado un usuario con todos los
privilegios. 

También se hace uso de una clase para imprimir de mejor forma los campos de las
tablas, la clase se puede encontrar [aqui](https://github.com/htorun/dbtableprinter).
# Ejecución
Para poder ejecutar exitosamente el programa, usa el siguiente comando:
```bash
mvn install
mvn exec:java
```
# Requisitos
- MariaDB v15.1
- Maven v3.8.4
- Java v11.0.4.1
