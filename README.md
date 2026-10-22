# Utilización de Programación Orientada en Aspectos (AOP) en SpringBoot

### En el siguiente código esta relacionado al manejo de la AOP, donde dentro de la carpera *aop* encontraremos diferentes archivos donde se profundiza mediante comentarios la funcionalidad y formas de trabajo mediante esta modalidad.

#### *Todos los archivos cuentan con sus respectivos comentarios para facilitar la comprensión *

#### Dentro del archivo GreetingAspect.java podremos ver bien sus diferentes implementaciones.



<p>
Todas las Evaluaciones se hacen con PostMan
</p>


<p align="centerr">
<img src="https://velog.velcdn.com/images/uijeong/post/ef2d7fd1-d34e-4505-94b3-d69ac6341851/image.png" alt="Programación orientada a aspectos">
</p>

---

## Objetivo

Proyecto de práctica del curso de Spring Boot 3 cuyo objetivo es aprender a separar las responsabilidades transversales (en este caso, el *logging*) de la lógica de negocio mediante **Programación Orientada a Aspectos**. Un servicio sencillo de saludos (`GreetingService`) es interceptado por distintos aspectos que registran en el log qué método se invoca, con qué argumentos, qué retorna y si lanza una excepción, sin modificar el código del servicio.

## Tecnologías

| Tecnología | Versión |
|---|---|
| Java | 21 |
| Spring Boot (starter parent) | 3.2.1 |
| Spring Web (`spring-boot-starter-web`) | gestionada por Spring Boot |
| Spring AOP / AspectJ (`spring-boot-starter-aop`) | gestionada por Spring Boot |
| Spring Boot Actuator | gestionada por Spring Boot |
| Spring Boot DevTools | gestionada por Spring Boot |
| SLF4J (Logger) | incluido en Spring Boot |
| Maven (Maven Wrapper incluido) | - |

No utiliza base de datos.

## Estructura del proyecto

```
src/main/java/com/joaquin/curso/springboot/aop/springbootaop
├── SpringbootAopApplication.java      # Clase principal de Spring Boot
├── aop
│   ├── GreetingServicePointcuts.java  # Define los @Pointcut reutilizables
│   ├── GreetingAspect.java            # Aspecto con @Before, @After, @AfterReturning, @AfterThrowing y @Around (@Order(2))
│   └── GreetingFooAspect.java         # Segundo aspecto con @Before y @After (@Order(1))
├── controllers
│   └── GreetingController.java        # Endpoints REST que invocan al servicio
└── services
    ├── GreetingService.java           # Interfaz: sayHello y sayHelloError
    └── GreetingServiceImp.java        # Implementación: arma el saludo / lanza una RuntimeException
```

- **GreetingServiceImp**: `sayHello(person, phrase)` concatena la persona y la frase y la retorna; `sayHelloError(...)` lanza `RuntimeException("Algún error")` para probar el aspecto de excepciones.
- **GreetingServicePointcuts**: centraliza las expresiones `execution(String ...GreetingService.sayHello(..))` en los métodos `greetingLoggerPointCut()` y `greetingFooLoggerPointCut()` para reutilizarlas desde los aspectos (los nombres deben ser distintos entre clases).
- **GreetingAspect**: registra mensajes "Antes", "Después", "Después de retornar", "Después de lanzar la excepción" y, con `@Around`, el método/parámetros antes de ejecutarlo y el resultado después (capturando errores con `joinPoint.proceed()` dentro de un `try/catch`).
- **GreetingFooAspect**: registra "Antes Foo" y "Después Foo" sobre el mismo método, para demostrar el orden de ejecución entre aspectos.

## Conceptos aplicados

- **`@Aspect` + `@Component`**: declarar una clase como aspecto gestionado por Spring (requiere la dependencia `spring-boot-starter-aop` en el `pom.xml`).
- **Advices**:
  - `@Before`: se ejecuta antes del método enlazado.
  - `@After`: se ejecuta después del método enlazado.
  - `@AfterReturning`: se ejecuta después de que el método retorna.
  - `@AfterThrowing`: se ejecuta después de que el método lanza una excepción (enlazado a `sayHelloError`).
  - `@Around`: combina antes y después; con `ProceedingJoinPoint.proceed()` se ejecuta el método y se obtiene su resultado.
- **`JoinPoint`**: une el aspecto con la llamada al método y permite obtener su nombre (`getSignature().getName()`) y argumentos (`getArgs()`).
- **`@Pointcut`**: expresiones `execution(...)` reutilizables definidas en una clase aparte.
- **`@Order`**: determina el orden de ejecución de varios aspectos aplicados a las mismas funciones (`GreetingFooAspect` = 1, `GreetingAspect` = 2).
- **Logging con SLF4J** (`LoggerFactory.getLogger(...)`) para registrar los eventos.

## Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/greeting` | Llama a `sayHello("Pepe", "Hola cómo estás!")` y retorna `{"greeting": "Pepe Hola cómo estás!"}`. En consola se ven los logs de los aspectos. |
| GET | `/greeting-error` | Llama a `sayHelloError(...)`, que lanza una `RuntimeException`; permite ver el log del advice `@AfterThrowing`. |

## Configuración

`application.properties` está vacío: no requiere configuración adicional. La aplicación levanta en el puerto por defecto `8080`.

## Cómo ejecutarlo

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

Luego probar con Postman (o el navegador):

```
GET http://localhost:8080/greeting
GET http://localhost:8080/greeting-error
```

y revisar en la consola los mensajes registrados por cada aspecto.
