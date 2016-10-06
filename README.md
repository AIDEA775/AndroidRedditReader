# Android Reddit Reader

## Contexto

Actividades realizadas por Alejandro Ismael Silva para la materia optativa _"Programación en Android: Introducción"_ de la Facultad de Astronomía, Matemática, Física y Computación (FaMAF) perteneciente a la Universidad Nacional de Córdoba diseñada por [Diego Mercado](https://github.com/mercadodiego).



## Step 1

### Objetivos

* Conocer la comunicación entre Actividades y su ciclo de vida

### Enunciado

* Descargar el proyecto desde https://github.com/mercadodiego/ActivitiesAssignment
* En la acción del Sign inLogin invocar LoginActivity
* Una vez finalizado, debera mostrar el nombre del usuario logueado en pantalla



## Step 2

### Objetivos

* Conocer los principios básicos de Layouts, View y ViewGroups en Android
* Modificar y agregar recursos gráficos y de texto a la aplicación

### Enunciado
1. Descargar el tag "layout_assignment" del repositorio https://github.com/mercadodiego/RedditReader
2. Todos los textos que despliega la aplicación deben aparecer tanto en inglés como en castellano
3. Cambiar el nombre de la aplicación para que aparezca como "Reddit Reader" en inglés y "Lector de Reddit" en castellano
4. Cambiar el ícono de la aplicación por el de ./images/reddit_icon.png
5. Cambiar el nombre de paquete de ar.edu.unc.famaf.activitiesassignment a ar.edu.unc.famaf.redditreader
6. En la vista principal debe mostrarse una vista igual a ./images/screenshot1.jpg ![Alt text](/images/screenshot1.jpg?raw=true "captura de imagen")



## Step 3

## Objetivos

* Implementar una [ListView](https://developer.android.com/reference/android/widget/ListView) que obtenga su contenido desde un propio [ArrayAdapter](https://developer.android.com/reference/android/widget/ArrayAdapter.html)

## Enunciado

1. Descargar el tag "adapters_assignment" del repositorio https://github.com/mercadodiego/RedditReader
2. La clase `ar.edu.unc.famaf.redditreader.model.PostModel` representa un Post en Reddit. La misma ya está creada pero vacía, debe agregar los atributos relativos al título, autor, fecha de creación, numero de comentarios e imagen de *preview/thumbnail* con sus correspondientes *setter/getter*
3. Implementar el método `List<PostModel> getTopPosts()` de la clase `ar.edu.unc.famaf.redditreader.backend.Backend`. El mismo debe devolver siempre 5 instancias de `PostModel` con contenido falso o *dummy*
4. Crear la clase `ar.edu.unc.famaf.redditreader.ui.PostAdapter` que extienda de `android.widget.ArrayAdapter` e re-implementar los métodos necesarios
5. `NewsActivityFragment` debe mostrar una [ListView](https://developer.android.com/reference/android/widget/ListView) que ocupe todo su espacio y debe desplegar el contenido de cada uno de los Posts siguiendo el diseño implementado en la actividad previa de [LayoutAssignment](https://github.com/mercadodiego/RedditReader/blob/layout_assignment/README.md). Tener en cuenta que el título debe poder siempre mostrarse y la altura de cada fila debe ajustarse para permitirlo
6. Implementar un *ViewHolder* en nuestra clase `ar.edu.unc.famaf.redditreader.ui.PostAdapter` para mejorar la performance de la [ListView](https://developer.android.com/reference/android/widget/ListView). Mayor información en: [Hold View Objects in a View Holder](https://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder)



## Condiciones generales de entrega

* Se debe trabajar en un repositorio GIT propio. Mayor información en: [Git-Basics-Working-with-Remotes](https://git-scm.com/book/en/v2/Git-Basics-Working-with-Remotes)
* La entrega consistirá en indicar en que TAG fue subido el mismo 
* No debe contener carpetas/archivos autogenerados
* Debe compilar. De lo contrario no será considerada como una entrega valida
* Debe desarrollarse usando Android Studio 2.2 (o cualquier versión superior del canal estable)
* Conservar Minimum SDK: API Level 15 y Target SDK: API Level 23 
