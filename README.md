# BackEnd_IEEE_Javeriana
Repositorio para el código de Backend correspondiente a las páginas web de IEEE Student Branch Javeriana, CS Javeriana y RAS Javeriana

El objetivo de este repositorio es manejar un BackEnd que funcionará para tres páginas web, esto debido a que dichas tres compartirán funciones similares

Se espera manejar ramas por funcionalidad donde poseeran el nombre de la funcionalidad seguido por la palabra "general"  (por ejemplo "Login_Branch_General"), en caso en que se necesite una funcionalidad epecífica para una página específica será necesario especificar el nombre de la página en lugar de la palabra "general" (por ejemplo "Registro_Solicitudes_CS")

**Paquetes**
Respecto a los paquetes. Los paquetes deben iniciar en minúscula y deben tener un móvil de su existencia (controller, service, entity, repository, etc), y deben contener únicamente clases de dicho tipo (ej el paquete controller solo tiene clases de tipo controlador)

**Clases**
Respecto al nombre de las clases. Dentro del código es necesario separar las clases con la siguiente estructura:
   * La clase debe iniciar con la primera letra en mayúscula
   * La clase debe especificar la funcionalidad que busca expresar
   * La clase debe contener la destinación de la clase (la página para la que va dirigida (IEEE, CS, RAS, GENERAL)
   * Se debe especificar el móvil de la clase (Controller, Service, Entity, ETC) 
   * No se deben poseer espacios entre las palabras, se separan considerando las mayúscula de la palabra
   * Ej ControllerLoginGeneral --> representa el controller del sistema de lógin que van a manejar todas las páginas

**Funciones**
Respecto a las funciones. Las funciones contenidas dentro de las distintas clases deben iniciar todas sus palabras con letra minúscula, no deben poseer acentos y ser separadas con "_"; ejemplo: "funcion_sumar_dos_caracteres"

**Variables**
Respecto a las variables. Las variables deben iniciar con mayúscula cada una de sus palabras, no deben poseer espacios y deben ser lo más descriptivas posibles; ejemplo: "private int ContadorDeVistasObtenidasPorMes;"

**Comentarios**
Respecto a los comentarios en el código. Es necesario agregar una pequeñas descripción previa a cada función con el objetivo de explicar el móvil de su existencia; se pueden agregar comentarios en partes específicas dentro del codigo según lo considere el programador (Sea para expresar una cuestión de lógica, motivo de existencia de una variable, algún posible cambio, etc)

