package com.android.parfume_jualanqoo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.BreakIterator;

public class adapter_transaksi extends FirestoreRecyclerAdapter<model_transaksi, adapter_transaksi.holder_transaksi>{
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private OnItemClickListener listener;
    public adapter_transaksi(@NonNull FirestoreRecyclerOptions<model_transaksi> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull holder_transaksi holder, int position, @NonNull model_transaksi model) {
        holder.produk.setText(model.getNama_produk());
        holder.nama.setText(model.getNama_user());
        holder.alamat.setText(model.getAlamat_user());
        holder.id_transaksi.setText(model.getId_transaksi());
        holder.tipe.setText(model.getTipe_produk());
        holder.jumlah.setText(model.getJumlah_pesanan()+"");
        holder.total.setText(model.getTotal()+"");
        holder.tanggal.setText(model.getTanggal_pesan());
        holder.telp.setText(model.getTelp_user());
    }

    @NonNull
    @Override
    public holder_transaksi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_act_list_transaksi,parent,false);
        return new holder_transaksi(view);
    }

    class holder_transaksi extends RecyclerView.ViewHolder{
        TextView produk,nama,alamat,telp,tanggal,tipe,id_transaksi,jumlah,total;
        public holder_transaksi(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            produk = itemView.findViewById(R.id.produk);
            alamat = itemView.findViewById(R.id.alamat);
            telp = itemView.findViewById(R.id.telp);
            tanggal = itemView.findViewById(R.id.tanggal);
            tipe = itemView.findViewById(R.id.tipe);
            id_transaksi = itemView.findViewById(R.id.id_transaksi);
            jumlah = itemView.findViewById(R.id.jumlah);
            total = itemView.findViewById(R.id.total);
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });*/
        }
    }
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
