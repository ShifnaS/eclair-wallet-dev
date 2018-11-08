package fr.acinq.eclair.wallet.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.acinq.eclair.wallet.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
  JSONArray list;
  Context context;

  public NotificationAdapter(JSONArray list, Context context) {
    this.list = list;
    this.context = context;
  }

  @NonNull
  @Override
  public NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.list_row_notification, parent, false);

    return new NotificationAdapter.MyViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder holder, int position) {
    try
    {

      JSONObject data=list.getJSONObject(position);
      holder.service.setText(data.getString("service"));
      holder.date.setText(data.getString("date"));
      holder.amount.setText(data.getString("amount"));
      String invoice_id=data.getString("invoice_id");

      holder.bt_pay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Toast.makeText(context, "invoice id "+invoice_id, Toast.LENGTH_SHORT).show();
        }
      });
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }


  }

  @Override
  public int getItemCount() {
    return list.length();
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView service, date,amount;
    Button bt_pay;
    public MyViewHolder(View itemView) {
      super(itemView);
      service =  itemView.findViewById(R.id.service);
      date =  itemView.findViewById(R.id.date);
      amount =  itemView.findViewById(R.id.amount);
      bt_pay=itemView.findViewById(R.id.pay);

    }
  }
}
