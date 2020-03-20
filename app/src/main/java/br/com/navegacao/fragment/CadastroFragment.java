package br.com.navegacao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.navegacao.R;
import br.com.navegacao.adapter.PessoaAdapter;
import br.com.navegacao.domain.PersonDBHelper;

public class CadastroFragment extends BaseFragment {

	private RecyclerView recyclerView;
	private PersonDBHelper dbHelper;
	private LinearLayoutManager linearLayoutManager;

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

		return view;
	}


	public void onActivityCreated(@Nullable Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		buscarDados();
	}

	private void buscarDados(){
		dbHelper = new PersonDBHelper(getContext());
		recyclerView.setAdapter(new PessoaAdapter(dbHelper.cadastroList(), getContext(), recyclerView));
	}
}