package br.com.navegacao.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import br.com.navegacao.AtualizarUsuario;
import br.com.navegacao.PessoaAcesso;
import br.com.navegacao.R;
import br.com.navegacao.domain.PersonDBHelper;


public class PessoaAdapter extends RecyclerView.Adapter<PessoaAdapter.ViewHolder> {
    private List<PessoaAcesso> pessoaLista;
    private Context newContext;
    private RecyclerView newRecyclerView;


    public class ViewHolder extends RecyclerView.ViewHolder {
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
        context = newContext;
        recyclerView = newRecyclerView;
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

        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(newContext);
                adb.setTitle("Escolha uma opção:");
                adb.setMessage("Atualiza ou deleta usuário?");
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
                        PersonDBHelper dbHelper = new PersonDBHelper(newContext);
                        dbHelper.deletarCadastro(pessoa.getId(), newContext);

                        pessoaLista.remove(position);
                        newRecyclerView.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, pessoaLista.size());
                        notifyDataSetChanged();
                        Toast.makeText(newContext, "Usuário: " + pessoa.getNome() + " excluída.", Toast.LENGTH_SHORT).show();
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
        Intent goToUpdate = new Intent(newContext, AtualizarUsuario.class);
        goToUpdate.putExtra("USER_ID", personId);
        newContext.startActivity(goToUpdate);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return pessoaLista.size();
    }
}
