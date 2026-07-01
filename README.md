# BackEnd  IEEE Javeriana
Repositorio para el código de Backend correspondiente a las páginas web de IEEE Student Branch Javeriana, CS Javeriana y RAS Javeriana

El objetivo de este repositorio es manejar un BackEnd que funcionará para tres páginas web, esto debido a que dichas tres compartirán funciones similares

<br />

## **Respecto A Las Ramas**
Se espera manejar ramas por funcionalidad donde poseeran el nombre de la funcionalidad seguido por la palabra "general"  (por ejemplo "Login_Branch_General"), en caso en que se necesite una funcionalidad epecífica para una página específica será necesario especificar el nombre de la página en lugar de la palabra "general" (por ejemplo "Registro_Solicitudes_CS")\ 

Adicionalmente, se aclara que cualquier persona puede trabajar en cualquiera de las ramas que ya estén creadas sin importar que ellos no hayan sido los responsables de su origen

Finalmente, las ramas deben ser eliminadas progresivamente se implementen los cambios a la rama "main"; al final del proyecto solo debe quedar la rama "main"


<br />

## **Respecto Al Código**
### **Paquetes**
Respecto a los paquetes. Los paquetes deben iniciar en minúscula y deben tener un móvil de su existencia (controller, service, entity, repository, etc), y deben contener únicamente clases de dicho tipo (ej el paquete controller solo tiene clases de tipo controlador)

<br />

### **Clases**
Respecto al nombre de las clases. Dentro del código es necesario separar las clases con la siguiente estructura:
   * La clase debe iniciar con la primera letra en mayúscula
   * La clase debe especificar la funcionalidad que busca expresar
   * La clase debe contener la destinación de la clase (la página para la que va dirigida (IEEE, CS, RAS, GENERAL)
   * Se debe especificar el móvil de la clase (Controller, Service, Entity, ETC) 
   * No se deben poseer espacios entre las palabras, se separan considerando las mayúscula de la palabra
   * Ej: "ControllerLoginGeneral.java" --> representa el controller del sistema de lógin que van a manejar todas las páginas

<br />

### **Funciones**
Respecto a las funciones. Dentro del código es necesario seguir estos parámetros de estructura para las funciones: 
   * Las clases deben iniciar todas sus palabras con letra minúscula
   * Las clases no deben poseer acentos en sus palabras
   * Las palabras de una clase deben ser separadas con "_"
   * Ej: "funcion_sumar_dos_caracteres()" --> representa una función que subará dos caracteres

<br />

### **Variables**
Respecto a las variables. Dentro del código es necesario seguir estos parámetros de estructura para las variables:
   * Las variables deben iniciar con mayúscula cada una de sus palabras
   * Las variables no deben poseer espacios ni caracteres que representen espacios
   * Las variables deben ser lo más descriptivas posibles
   * Ej: "private int ContadorDeVistasObtenidasPorMes;" --> representa una variable que funciona como contador

<br />

### **Comentarios**
Respecto a los comentarios en el código. Es necesario agregar una pequeñas descripción previa a cada función con el objetivo de explicar el móvil de su existencia; se pueden agregar comentarios en partes específicas dentro del codigo según lo considere el programador (Sea para expresar una cuestión de lógica, motivo de existencia de una variable, algún posible cambio, etc). Dentro del código es necesario seguir estos parámetros de estructura para los comentarios
   * Los comentarios no deben poseer acentos
   * Los comentarios de explicación de función deben poser el marcador de texto "FUNCTEXPL:". Por ejemplo el siguiente comentario "// FUNCTEXPL: la siguiente funcion tiene la finalidad de permitir sumar dos numeros pares"
   * Los comentarios de explicación dentro de la función deben poseer uno de los siguientes marcadores_
     - "VAREXPL:" para expliación de variables --> Ej: "int ContadorAuxiliar; // VAREXPL: es una variable auxiliar que funciona para mantener el valor original del contador principal"
     - "METEXPL:" para explicación de métodos/funciones que se posean dentro de la función principal (como bucles o métodos tipo if) --> Ej: " while(true){ // METEXPL: se utiliza para mantener prendido el proceso dentro del codigo"
     - "METUSEEXPL:" para la expliación de uso de métodos/funciones externas dentro de la función actual --> Ej: " int resultado = SumarEnteros(); // METUSEEXPL: se utiliza el metodo SumarEnteros() para obtener un valor
     - "COM:" para otro tipo de comentario --> Ej: "// COM: se puede implementar una variable auxiliar para mejorar el rendimiento"

