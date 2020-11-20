package alegarse.mail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivity extends AppCompatActivity {

    String correo, contraseña,mensaje;
    Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Seteamos variables
        correo = "infornotification@gmail.com";
        contraseña = "J9hkazbxrsuy2.";
        mensaje = "Este es un correo de prueba para la notificación vía Email de sustituciones";

        enviar = findViewById(R.id.button);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicializamos las propiedades del envío
                Properties properties = new Properties();
                properties.put("mail.smtp.auth","true");
                properties.put("mail.smtp.starttls.enable","true");
                properties.put("mail.smtp.host","smtp.gmail.com");
                properties.put("mail.smtp.port","587");

                try {
                    // Inicializo la sesión
                    Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(correo,contraseña);
                        }
                    });

                    // Verificamos que la sesión no sea nula
                    if (session != null) {
                        // Inicializo el contenido del mensaje
                        Message message = new MimeMessage(session);
                        // Quien envía
                        message.setFrom(new InternetAddress(correo));
                        // Destinatario
                        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("aleboy80@gmail.com")); //toString().trim();
                        // Asunto
                        message.setSubject("Envío sustitución"); //toString().trim();
                        // Cuerpo del mensaje
                        message.setText(mensaje);
                        //message.setContent(mensaje, "text/html; charset=utf-8");

                        // Enviamos email
                        new SendMail().execute(message);
                    }
                } catch (MessagingException e) {
                    e.printStackTrace();
                }



            }
        });
    }

    private class SendMail extends AsyncTask<Message,String, String> {

        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                return "Success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}