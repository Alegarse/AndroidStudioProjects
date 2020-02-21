package com.example.ex3tags;

public class SecondaryActivity {
}

























/**  MENU
 * import androidx.annotation.NonNull;
 * import androidx.appcompat.app.AppCompatActivity;
 *
 * import android.os.Bundle;
 * import android.view.Menu;
 * import android.view.MenuItem;
 * import android.view.View;
 *
 * import com.google.android.material.snackbar.Snackbar;
 *
 * public class MainActivity extends AppCompatActivity {
 *
 *
 *     @Override
 *     protected void onCreate(Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *         setContentView(R.layout.activity_main);
 *
 *     }
 *
 *     /* Para meter el menú en la pantalla lo inflamos/
 *@Override
 *public boolean onCreateOptionsMenu(Menu menu)
        *{
        *
        *getMenuInflater().inflate(R.menu.main_menu,menu);
        *
        *         //
        *return true; // Hemos creado el menú correctamente
        *}
        *
        *     /* Para cuando se usa una de lasopciones del menu/
        *@Override
 *public boolean onOptionsItemSelected(@NonNull MenuItem item)
        *{
        *
        *
        *View vista=getWindow().getDecorView().getRootView();
        *switch(item.getItemId())
        *
        *{
        *case R.id.mnu_add:
        *Snackbar.make(vista,"Añadir entrada",Snackbar.LENGTH_LONG).show();
        *break;
        *
        *case R.id.mnu_del:
        *Snackbar.make(vista,"Borrar entrada",Snackbar.LENGTH_LONG).show();
        *break;
        *
        *case R.id.mnu_logout:
        *Snackbar.make(vista,"Bye bye!!",Snackbar.LENGTH_LONG).show();
        *break;
        *}
        *
        *return super.onOptionsItemSelected(item);
        *}
        *}
 *
 * En menu_xml
 *
 * <menu xmlns:android="http://schemas.android.com/apk/res/android">
 *
 *     <item android:id="@+id/mnu_add"
 *         android:title="@string/menu_add"
 *         android:icon="@drawable/ic_add_24dp"/>
 *
 *     <item android:id="@+id/mnu_del"
 *         android:title="@string/menu_del"
 *         android:icon="@drawable/ic_del_24dp"/>
 *
 *     <item android:id="@+id/mnu_logout"
 *         android:title="@string/menu_logout"
 *         android:icon="@drawable/ic_logout_24dp"/>
 *
 * </menu>
 */




/**   NOTIS Y SERVICE
 *
 * MANIFEST:
 *
 *         <service android:name=".miTempo" />
 *
 * MAIN:
 *
 * public class MainActivity extends AppCompatActivity {
 *
 *     private Button boton;
 *     private Intent tempo;
 *
 *     @Override
 *     protected void onCreate(Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *         setContentView(R.layout.activity_main);
 *
 *         boton = findViewById(R.id.boton);
 *
 *         tempo = new Intent(this, miTempo.class);
 *
 *         boton.setOnClickListener((v) -> startService(tempo));
 *     }
 *
 *     @Override
 *     protected void onDestroy()
 *     {
 *         super.onDestroy() ;
 *         stopService(tempo) ;
 *     }
 * }
 *
 *
 * SERVICE CON NOTI:
 *
 * public class miTempo extends Service {
 *
 *     private NotificationManager nm;
 *     private final String CHANNEL_ID = "Channel2";
 *     private NotificationCompat.Builder not;
 *     private final int NOTIFICATION_ID = 666;
 *
 *     // Para versiones de API >= 26
 *     private NotificationChannel ch;
 *     private final String CHANNEL_NAME = "Temporizador";
 *
 *     // Sonido notificación
 *     private Uri uri;
 *
 *
 *     @Override
 *     public void onCreate()
 *     {
 *         Log.i("TEMPO", "Servicio creado") ;
 *     }
 *
 *
 *     public int onStartCommand(Intent tempo, int flags, int startId)
 *     {
 *
 *         Log.i("TEMPO", "Count Down");
 *         new CountDownTimer(1000, 1000) {
 *
 *             @Override
 *             public void onTick(long millisUntilFinished) {}
 *
 *             @Override
 *             public void onFinish() {
 *                 Log.i("NOTI","Ha terminado");
 *                 execN();
 *             }
 *         }.start();
 *
 *         return START_NOT_STICKY;
 *     }
 *
 *     @Override
 *     public void onDestroy()
 *     {
 *         Log.i("TEMPO", "Servicio destruido") ;
 *     }
 *
 *     @Nullable
 *     @Override
 *     public IBinder onBind(Intent intent) {return null;}
 *
 *     // Método que contruye y lanza la notificacion
 *     private void execN () {
 *
 *         // Ruta archivo audio para notificacion
 *         // uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sound);
 *         uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.sound);
 *
 *         nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
 *         createNotificationChannel();
 *
 *
 *         not = new NotificationCompat.Builder(miTempo.this, CHANNEL_ID);
 *         not.setSmallIcon(R.drawable.icon); // Obligatorio
 *         not.setContentTitle("Contador finalizado!");
 *         not.setContentText("Ha finalizado la cuenta atrás de 10 segundos!");
 *         // Asignamos sonido a la notificación
 *         // not.setDefaults(Notification.DEFAULT_SOUND) ;
 *         not.setSound(uri);
 *         // Fijamosla prioridad de la notificacion
 *         not.setPriority(NotificationCompat.PRIORITY_HIGH);
 *         // Enviamos la notificacion por el canal
 *         nm.notify(NOTIFICATION_ID, not.build());
 *     }
 *
 *     private void createNotificationChannel ()
 *     {
 *         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
 *         {
 *             // Definimos los atributos de audio
 *             AudioAttributes att = new AudioAttributes.Builder()
 *                     .setContentType((AudioAttributes.CONTENT_TYPE_SONIFICATION))
 *                     .setUsage((AudioAttributes.USAGE_NOTIFICATION))
 *                     .build();
 *
 *             // Creamos nuestro canal de comunicacion
 *             ch = new NotificationChannel(CHANNEL_ID,
 *                     CHANNEL_NAME,
 *                     NotificationManager.IMPORTANCE_HIGH );
 *
 *             // Le anexamos la descripción
 *             ch.setDescription(CHANNEL_NAME);
 *
 *             // Asocio el audio al canal creado con sus atributos
 *             ch.setSound(uri,att);
 *
 *             // Incorporamos vibracion en la notificación
 *             ch.enableVibration(true);
 *             ch.setVibrationPattern(new long [] {300l} );
 *
 *             // Creamos el canal
 *             nm.createNotificationChannel(ch);
 *
 *             // Para poder verlo con la pantalla bloqueada
 *             ch.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
 *         }
 *     }
 * }
 *
 *
 *
 *  TOAST
 *
 *  Toast.makeText(getApplicationContext(), R.string.log_nok, Toast.LENGTH_LONG).show();
 *
 *
 *  OBSERVABLE
 *
 *
 *  Creamos el observable:
 *  ====================
 *
 *  import java.util.Observable;
 *
 * public class ObservableInteger extends Observable implements Serializable {
 *
 *     private int value;
 *
 *     public int getValue() {
 *         return value;
 *     }
 *
 *     public void setValue(int value) {
 *         this.value = value;
 *         this.setChanged();
 *         this.notifyObservers(value);
 *     }
 * }
 *
 * AHORA EN EL MAIN===============
 *
 * public class MainActivity extends Activity {
 *
 *     private ObservableInteger a;
 *     private Button button;
 *
 *     @Override
 *     protected void onCreate(Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *         setContentView(R.layout.activity_main);
 *
 *         // Create observable int
 *         a = new ObservableInteger();
 *         // Set a default value to it
 *         a.setValue(0);
 *
 *         // Create frag1
 *         Frag1 frag1 = new Frag1();
 *         Bundle args = new Bundle();
 *         // Put observable int (That why ObservableInteger implements Serializable)
 *         args.putSerializable(Frag1.PARAM, a);
 *         frag1.setArguments(args);
 *
 *         // Add frag1 on screen
 *         getFragmentManager().beginTransaction().add(R.id.container, frag1).commit();
 *
 *         // Add a button to change value of a dynamically
 *         button = (Button) findViewById(R.id.button);
 *         button.setOnClickListener(new View.OnClickListener() {
 *             @Override
 *             public void onClick(View v) {
 *                 // Set a new value in a
 *                 a.setValue(a.getValue() + 1);
 *             }
 *         });
 *     }
 * }
 *
 * CREAMOS UN FRAGMENTO:?=====================
 *
 * ...
 * import java.util.Observer;
 *
 * public class Frag1 extends Fragment {
 *     public static final String PARAM = "param";
 *
 *     private ObservableInteger a1;
 *     private Observer a1Changed = new Observer() {
 *         @Override
 *         public void update(Observable o, Object newValue) {
 *             // a1 changed! (aka a changed)
 *             // newValue is the observable int value (it's the same as a1.getValue())
 *             Log.d(Frag1.class.getSimpleName(), "a1 has changed, new value:"+ (int) newValue);
 *         }
 *     };
 *
 *     public Frag1() {
 *     }
 *
 *     @Override
 *     public void onCreate(Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *         if (getArguments() != null) {
 *             // Get ObservableInteger created in activity
 *             a1 = (ObservableInteger) getArguments().getSerializable(PARAM);
 *             // Add listener for value change
 *             a1.addObserver(a1Changed);
 *         }
 *     }
 *
 *     @Override
 *     public View onCreateView(LayoutInflater inflater, ViewGroup container,
 *                              Bundle savedInstanceState) {
 *         return inflater.inflate(R.layout.fragment_blank, container, false);
 *     }
 * }
 */
