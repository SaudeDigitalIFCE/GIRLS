package ifce.lara.vite.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;

import ifce.lara.vite.R;
import ifce.lara.vite.object.UserInfo;

public class NotificationActivity extends AppCompatActivity {

    static final String inf[] ={"Nome: ","Problema: ","Alergia: ","Medicamentos: ","Grupo Sanguineo: ","Contato: "};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SugarContext.init(NotificationActivity.this);
        Intent resultIntent = new Intent(this,CancelProtocolActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );




        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(NotificationActivity.this)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setSmallIcon(R.drawable.menu)
                .setContentTitle("Emergência")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setColor(152251)
                .addAction(R.drawable.cancelar_f,"STOP",resultPendingIntent)
                .setContentText("Informções sobre a saúde do usuário");
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        String[] events = new String[6];
        //////////////////////////////////////////////////////////q////////////////////////////////////////q
        ArrayList<String> informacoes_usuario = new ArrayList<String>();
        //GestaoBanco busca_informacoes;
        ///busca_informacoes = new GestaoBanco(this);
        System.out.println("Valores ");
        ///informacoes_usuario.addAll(busca_informacoes.Consulta_informacoes_usuario());


        UserInfo userInfo = null; try {
            userInfo = UserInfo.findById(UserInfo.class,0);
            System.out.println("================>>Resultado = "+userInfo.allergy.toString());
                informacoes_usuario.add(userInfo.name.toString());
                informacoes_usuario.add(userInfo.disiase.toString());
                informacoes_usuario.add(userInfo.allergy.toString());
                informacoes_usuario.add(userInfo.medicines.toString());
                informacoes_usuario.add(userInfo.blood.toString());
                informacoes_usuario.add(userInfo.phone.toString());
                //System.out.println("Valores " + a.toString());

                //////////////////////////////////////////////////////////////////////////////////////////////////qq


                for (int i = 0; i < events.length; i++) {

                    events[i] = informacoes_usuario.get(i).toString();
                    inboxStyle.addLine(inf[i] + events[i]);

                }

///////////////////////////////////////////////////////////////////////////////////////////////

                // Moves the expanded layout object into the notification object.
                builder.setStyle(inboxStyle);
                builder.setContentIntent(resultPendingIntent);


                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
                //mId allows you to update the notification later on.
                mNotificationManager.notify(20000, builder.build());
                System.exit(0);


        } catch (Exception e) {

            Toast.makeText(NotificationActivity.this, "Informações ainda não Preenchidas", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
}
