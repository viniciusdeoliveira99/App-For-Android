package br.com.navegacao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.navegacao.R;
import br.com.navegacao.adapter.PessoaAdapter;
import br.com.navegacao.domain.PersonDBHelper;

public class CadastroFragment extends Fragment {
	protected RecyclerView recyclerView;
	private PersonDBHelper dbHelper;
	private PessoaAdapter pessoaAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
		
		dbHelper = new PersonDBHelper(getContext());
		pessoaAdapter = new PessoaAdapter(dbHelper.cadastroList(), getContext(), recyclerView);
        recyclerView.setAdapter(pessoaAdapter);
		
		return view;
	}
}