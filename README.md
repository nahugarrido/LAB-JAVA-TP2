# LAB-JAVA-TP2

<h1 align = "center">🕑 Jornadas Laborales 👨‍💻</h1>
<p align="center">
<img src="https://wallpapercave.com/wp/wp1842342.jpg" style="max-width: 100%; display: inline-block;" />
</p>

## 🍕 Resumen:

La aplicación está desarrollada en Java 11 utilizando Spring Boot 2.7.6.

Cuenta con base de datos H2 en memoria.


## 🤓 Cómo ejecutarla:

Debes tener instalado un JDK de Java 11.

Puedes ejecutar la aplicación desde tu IDE de preferencia.

## 🌟 Aclaraciones:

- Algunas validaciones no se muestran en el arreglo de message (ejemplo: horasTrabajadas de JornadaSaveDTO) pero están implementadas y funcionando.


- Los DTOs que no cuentan con validaciones son aquellos que se utilizan para mostrar información.


- He creado múltiples excepciones con un formato similar para poder darles tratamiento diferente en caso de que se requiera en el futuro.


- En HU-006 algunas de las validaciones podrían haberse realizado basándose en el booleano "laborable", pero opté por limitarme a lo que decían los criterios de aceptación.


- He creado algunos métodos auxiliares para limitar el uso de los repositorios a sus servicios.


- El TP esta resuelto en su totalidad.