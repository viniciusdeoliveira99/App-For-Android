package br.com.navegacao.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.navegacao.AddUsuario;
import br.com.navegacao.R;
import br.com.navegacao.adapter.PessoaAdapter;
import br.com.navegacao.domain.PersonDBHelper;

public class CadastroFragment extends BaseFragment {

	private RecyclerView recyclerView;
	private PersonDBHelper dbHelper;
	private LinearLayoutManager linearLayoutManager;
	private PessoaAdapter adapter;
	private String filter = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_cadastro, container, false);

		//FAB
		final FloatingActionButton fab = view.findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getContext(), AddUsuario.class);
				startActivity(intent);
			}
		});

		recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
		linearLayoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setHasFixedSize(true);
		setHasOptionsMenu(true);

		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
				if(newState == RecyclerView.SCROLL_STATE_IDLE){
					fab.show();
				}
				super.onScrollStateChanged(recyclerView, newState);
			}

			@Override
			public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

				if(dy > 0 || dy < 0 && fab.isShown()){
					fab.hide();
				}
			}
		});

		//CHAMA MÉTODO PARA POPULAR O FILTRO
		populaterecyclerView(filter);

		return view;
	}

	private void populaterecyclerView(String filter){
		dbHelper = new PersonDBHelper(getContext());
		adapter = new PessoaAdapter(dbHelper.peopleList(filter), getContext(), recyclerView);
		recyclerView.setAdapter(adapter);
	}

	//SPINNER
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		inflater.inflate(R.menu.home_menu, menu);

		MenuItem item = menu.findItem(R.id.filterSpinner);
		final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
		final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
				R.array.filterOptions, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String filter = parent.getSelectedItem().toString();
				((TextView) spinner.getSelectedView()).setTextColor(Color.WHITE);
				populaterecyclerView(filter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				populaterecyclerView(filter);
			}
		});

		spinner.setAdapter(adapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}
}