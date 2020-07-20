package br.com.navegacao.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import br.com.navegacao.activity.AtualizarUsuario;
import br.com.navegacao.activity.PessoaAcesso;
import br.com.navegacao.R;
import br.com.navegacao.domain.PersonDBHelper;


public class PessoaAdapter extends RecyclerView.Adapter<PessoaAdapter.ViewHolder> {
    protected List<PessoaAcesso> pessoaLista;
    protected Context mContext;
    protected RecyclerView mRecyclerView;


    protected class ViewHolder extends RecyclerView.ViewHolder {
        public TextView pessoaNome;
        public TextView pessoaIdade;
        public TextView pessoaOcupacao;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            pessoaNome = (TextView) v.findViewById(R.id.nome);
            pessoaIdade = (TextView) v.findViewById(R.id.idade);
            pessoaOcupacao = (TextView) v.findViewById(R.id.ocupacao);
        }
    }

	//ADD NA LISTA
    public void add(int position, PessoaAcesso pessoa) {
        pessoaLista.add(position, pessoa);
        notifyItemInserted(position);
    }

	//REMOVE DA LISTA
    public void remove(int position) {
        pessoaLista.remove(position);
        notifyItemRemoved(position);
    }


    public PessoaAdapter(List<PessoaAcesso> myDataset, Context context, RecyclerView recyclerView) {
        pessoaLista = myDataset;
        recyclerView = mRecyclerView;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_lista, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final PessoaAcesso pessoa = pessoaLista.get(position);
        holder.pessoaNome.setText("Nome: " + pessoa.getNome());
        holder.pessoaIdade.setText("Idade: " + pessoa.getIdade());
        holder.pessoaOcupacao.setText("Ocupacao: " + pessoa.getOcupacao());

        holder.pessoaNome.setTextColor(Color.rgb(86, 171, 214));
        holder.pessoaIdade.setTextColor(Color.rgb(86, 171, 214));
        holder.pessoaOcupacao.setTextColor(Color.rgb(86, 171, 214));

        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);
                adb.setTitle("Escolha uma opção:");
                adb.setMessage("Atualizar ou deletar o usuário?");
                adb.setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //go to update activity
                        goToUpdateActivity(pessoa.getId());
                    }
                });

                adb.setNeutralButton("Deletar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PersonDBHelper dbHelper = new PersonDBHelper(mContext);
                        dbHelper.deletarCadastro(pessoa.getId(), mContext);

                        pessoaLista.remove(position);
//                        mRecyclerView.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, pessoaLista.size());
                        notifyDataSetChanged();
                        Toast.makeText(mContext, "Usuário: (" + pessoa.getNome() + ") deletado(a).", Toast.LENGTH_SHORT).show();
                    }
                });
                adb.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                adb.create().show();
            }
        });
    }

    private void goToUpdateActivity(long personId){
        Intent goToUpdate = new Intent(mContext, AtualizarUsuario.class);
        goToUpdate.putExtra("USER_ID", personId);
        mContext.startActivity(goToUpdate);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return pessoaLista.size();
    }
}
