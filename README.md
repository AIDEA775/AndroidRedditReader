# Android Reddit Reader

## Contexto

Actividades realizadas por Alejandro Ismael Silva para la materia optativa
_"Programación en Android: Introducción"_ de la Facultad de Astronomía, Matemática, Física y Computación (FaMAF)
perteneciente a la Universidad Nacional de Córdoba diseñada por [Diego Mercado](https://github.com/mercadodiego).


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
6. En la vista principal debe mostrarse una vista igual a ./images/screenshot1.jpg


## Step 3

### Objetivos

* Implementar una [ListView](https://developer.android.com/reference/android/widget/ListView) que
obtenga su contenido desde un propio [ArrayAdapter](https://developer.android.com/reference/android/widget/ArrayAdapter.html)

### Enunciado

1. Descargar el tag "adapters_assignment" del repositorio https://github.com/mercadodiego/RedditReader
2. La clase `ar.edu.unc.famaf.redditreader.model.PostModel` representa un Post en Reddit.
La misma ya está creada pero vacía, debe agregar los atributos relativos al título, autor,
fecha de creación, numero de comentarios e imagen de *preview/thumbnail* con sus correspondientes *setter/getter*
3. Implementar el método `List<PostModel> getTopPosts()` de la clase `ar.edu.unc.famaf.redditreader.backend.Backend`.
El mismo debe devolver siempre 5 instancias de `PostModel` con contenido falso o *dummy*
4. Crear la clase `ar.edu.unc.famaf.redditreader.ui.PostAdapter` que extienda de `android.widget.ArrayAdapter` e
 re-implementar los métodos necesarios
5. `NewsActivityFragment` debe mostrar una [ListView](https://developer.android.com/reference/android/widget/ListView)
que ocupe completamente su espacio y debe desplegar el contenido de cada uno de los Posts siguiendo el diseño implementado
en la actividad previa de [LayoutAssignment](https://github.com/mercadodiego/RedditReader/blob/layout_assignment/README.md).
Tener en cuenta que el título debe poder siempre mostrarse y la altura de cada fila debe ajustarse para permitirlo
6. Implementar un *ViewHolder* en nuestra clase `ar.edu.unc.famaf.redditreader.ui.PostAdapter`
para mejorar la performance de la [ListView](https://developer.android.com/reference/android/widget/ListView). Mayor información en:
[Hold View Objects in a View Holder](https://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder)


## Step 4

### Objetivos

* Implementar una [AsyncTask](https://developer.android.com/reference/android/os/AsyncTask.html)
dentro de una [ListView](https://developer.android.com/reference/android/widget/ListView) para ir
descargando el contenido de las _thumbnails_ desde Internet

### Enunciado

1. La clase `ar.edu.unc.famaf.redditreader.ui.PostAdapter` debe implementar una AsyncTask que dada
una URL previamente definida en la clase  `ar.edu.unc.famaf.redditreader.model.PostModel`, permita
descargar dicha imagen y que sea parte de la ImageView representativa del *preview/thumbnail*
2. Debe emplearse un `android.widget.ProgressBar` animado mientras la imagen está siendo descargada


## Step 5

### Objetivos

* Realizar una llamada a un servicio REST, interpretar el Json devuelto y mostrar los resultados en pantalla.

### Enunciado

1. Crear la clase `ar.edu.unc.famaf.redditreader.backend.GetTopPostsTask` que obtenga el contenido vía HTTP
en formato JSon de los primeros 50 Top posts de Reddit, lo interprete y devuelva como resultado un `List<PostModel>`
2. El contenido debe mostrarse el la `ListView` de la clase `NewsActivityFragment`
3. Cuando no hay conexión a INTERNET mostrar un error que lo indique en forma de
[AlertDialog](https://developer.android.com/reference/android/app/AlertDialog.html)
4. El interprete de JSON debe estar definido en una nueva clase `ar.edu.unc.famaf.redditreader.backend.Parser`
y debe implementar el siguiente método de entrada, empleando internamente una instancia de
[JsonReader](https://developer.android.com/reference/android/util/JsonReader.html) que devuelva una
nueva clase llamada `ar.edu.unc.famaf.redditreader.model.Listing` (acorde a la estructura de objetos propia de la API de Reddit)
```Java
    public Listing readJsonStream(InputStream in) throws IOException {....}
```

### Tips

* La documentación oficial de la API de Reddit está disponible en [Reddit API](https://www.reddit.com/dev/api/)
* Para realizar una llamada REST HTTP (GET) puede emplear el siguiente snippet de código
```Java
    HttpURLConnection conn = (HttpURLConnection) new URL("...").openConnection();
    conn.setRequestMethod("GET");
    conn.getInputStream();
```


## Step 6

### Objetivos

* Implementar una pequeña base de datos SQLite

### Enunciado

1. La clase `ar.edu.unc.famaf.redditreader.backend.GetTopPostsTask` debe implementar ahora el siguiente comportamiento
 1. Invocar al servicio REST de Reddit para obtener los primeros 50 TOP posts
 2. Persistir los resultados en una base de datos interna
 3. Devolver los resultados desde la base de datos interna
 4. En caso de que no haya conexión a internet, se deben devolver los últimos resultados obtenidos desde la base de datos interna
2. La base de datos interna debe estar implementada en una nueva clase:
`ar.edu.unc.famaf.redditreader.backend.RedditDBHelper` de tipo `SQLiteOpenHelper`
 * Solo almacena los últimos 50 posts. El resto deben borrarse.
3. Los _thumbnails/preview_ a medida que se descargan deben también almacenarse. Recordar que pueden almacenarse como un arreglo de bytes:

```Java
   public static byte[] getBytes(Bitmap bitmap)
   {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG,0, stream);
        return stream.toByteArray();
   }
   public static Bitmap getImage(byte[] image)
   {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
   }
```


## Step 7

### Objetivos

* Implementar un scroll infinito donde se vayan mostrando 5 posts y mientras tanto se vayan obteniendo los próximos 5 posts.

### Enunciado

1-) Implementar el siguiente método en nuestra clase `ar.edu.unc.famaf.redditreader.backend.Backend`
```Java
public void getNextPosts(final PostsIteratorListener listener) {...}
```

... donde `PostsIteratorListener` es:

```Java
public interface PostsIteratorListener {
    void nextPosts(List<PostModel> posts);
}
```
* La misma debe invocar al método nextPosts devolviendo los próximos 5 posts. Internamente la
primera vez consulta al servicio web y persiste los primeros 50 resultados. Una vez que llega a
dicho límite debe consultar por los próximos 50 posts y así sucesivamente.

2-) Crear la clase `ar.edu.unc.famaf.redditreader.backend.EndlessScrollListener`:
Fuente:  [guides.codepath.com](https://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView)

 * Nuestra `ListView` debe reimplementar
 [onScrollListener](https://developer.android.com/reference/android/widget/AbsListView.OnScrollListener.html)
 tal como indica la fuente y `NewsActivityFragment` implementar `PostsIteratorListener`. Una vez que haya nuevos
 resultados se debe invocar al método `notifyDataSetChanged()` de nuestro propio adapter.


## Step 8

### Objetivos

* Al seleccionar una celda de la lista, mostrar el detalle del post.

### Enunciado

#### Primera parte

 1-) Para "escuchar" por los eventos de selección de celda debe reimplementar el método
  `ListView.setOnItemClickListener(OnItemClickListener listener)`

 2-) Emplear la siguiente interfaz para la comunicación entre `NewsActivity` y `NewsActivityFragment`

 ```Java
 public interface OnPostItemSelectedListener{
     void onPostItemPicked(PostModel post);
 }
 ```

 3-) La actividad de detalle debe llevar de nombre `ar.edu.unc.famaf.redditreader.ui.NewsDetailActivity`,
  con su correspondiente fragmento `ar.edu.unc.famaf.redditreader.ui.NewsDetailActivityFragment`

 4-) Mostrar el título del post en un `TextView`


#### Segunda parte

1-) Mostrar el siguiente detalle:
* Subrredit al que pertenece
* Fecha
* Titulo
* Usuario
* Preview (si está presente)
* Link a sitio web (si corresponde)

2-) Al seleccionar link el mismo debe abrir en una nueva actividad con una
[WebView](https://developer.android.com/reference/android/webkit/WebView.html) que despliegue el
contenido web, sin abandonar la aplicación

### Tip

* Para poder emplear PostModel como parte del Intent, dicho objeto debe implementar la interfaz
Serializable y emplear los métodos putExtra(String name, Serializable value) y Serializable
getSerializableExtra(String name) de la clase android.content.Intent


## Condiciones generales de entrega

* **No deben emplearse frameworks que no sean los provistos oficialmente por la SDK de Android**
* Se debe trabajar en un repositorio GIT propio. Mayor información en:
[Git-Basics-Working-with-Remotes](https://git-scm.com/book/en/v2/Git-Basics-Working-with-Remotes)
* La entrega consistirá en indicar en que TAG fue subido el mismo 
* No debe contener carpetas/archivos autogenerados
* Debe compilar. De lo contrario no será considerada como una entrega valida
* Debe desarrollarse usando Android Studio 2.2 (o cualquier versión superior del canal estable)
* Conservar Minimum SDK: API Level 15 y Target SDK: API Level 23 
