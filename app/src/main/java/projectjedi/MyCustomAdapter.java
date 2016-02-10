package projectjedi;

        import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.AdapterViewHolder>{

    ArrayList<Ranking> topscores;
    DBHelper dbHelper;

  MyCustomAdapter(Context context){

      dbHelper = new DBHelper(context);


      String users[] = dbHelper.getUsers();
      int scores[] = dbHelper.getScores();
      int nUsers = dbHelper.numberOfUsers();

      topscores = new ArrayList<>();
      for (int i = 0;i < nUsers-1;i++){
          if (scores[i]!=0)topscores.add(new Ranking(users[i],String.valueOf(scores[i])));
      }

  }

    @Override
    public MyCustomAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Instancia un layout XML en la correspondiente vista.
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //Inflamos en la vista el layout para cada elemento
        View view = inflater.inflate(R.layout.rowlayout, viewGroup, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCustomAdapter.AdapterViewHolder adapterViewholder, int position) {
        adapterViewholder.user.setText(topscores.get(position).getUser());
        adapterViewholder.score.setText("Min attempts: " + topscores.get(position).getScore());

    }

    @Override
    public int getItemCount() {
        //Debemos retornar el tamaño de todos los elementos contenidos en el viewholder
        //Por defecto es return 0 --> No se mostrará nada.
        return topscores.size();
    }



    //Definimos una clase viewholder que funciona como adapter para
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        /*
        *  Mantener una referencia a los elementos de nuestro ListView mientras el usuario realiza
        *  scrolling en nuestra aplicación. Así que cada vez que obtenemos la vista de un item,
        *  evitamos las frecuentes llamadas a findViewById, la cuál se realizaría únicamente la primera vez y el resto
        *  llamaríamos a la referencia en el ViewHolder, ahorrándonos procesamiento.
        */

        public TextView user;
        public TextView score;
        public View v;
        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.user = (TextView) itemView.findViewById(R.id.name);
            this.score = (TextView) itemView.findViewById(R.id.score);
        }
    }
}