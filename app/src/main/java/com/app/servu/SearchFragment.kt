package com.app.servu

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class SearchFragment : Fragment() {

    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var searchEditText: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        categoriesRecyclerView = view.findViewById(R.id.categories_recycler_view)
        searchEditText = view.findViewById(R.id.search_edit_text)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategoryList()

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().equals("eletricista", ignoreCase = true)) {
                    showSearchResults()
                } else {
                    setupCategoryList()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupCategoryList() {
        categoriesRecyclerView.layoutManager = GridLayoutManager(context, 2)
        val categories = listOf(
            Category("Designer Gráfico", R.drawable.designer_grafico2),
            Category("Técnico em Ar Condicionado", R.drawable.tecnico_de_ar_condicionado),
            Category("Chaveiro", R.drawable.chaveiro),
            Category("Cuidador de Idosos", R.drawable.cuidador_de_idosos),
            Category("Diarista", R.drawable.diarista),
            Category("Encanador", R.drawable.encanador1___principal_nas_partes_do_projeto),
            Category("Limpeza de Piscinas", R.drawable.limpador_de_piscina),
            Category("Costureira", R.drawable.costureira),
            Category("Eletricista", R.drawable.eletricista),
            Category("Jardineiro", R.drawable.jardineiro),
            Category("Bico para Reformas", R.drawable.pedreiro),
            Category("Montador de Móveis", R.drawable.montador_de_m_veis)
        )
        categoriesRecyclerView.adapter = CategoryAdapter(categories)
    }

    private fun showSearchResults() {
        categoriesRecyclerView.layoutManager = LinearLayoutManager(context)
        val searchResults = listOf(
            SearchResult("Antônio Souza - Eletricista", 5.0f, "Especialista em instalações", 41, R.drawable.ic_profile_placeholder),
            SearchResult("Matheus Silva - Eletricista", 4.8f, "Especialista em instalações", 35, R.drawable.ic_profile_placeholder),
            SearchResult("José Silva - Eletricista", 3.1f, "Especialista em instalações", 28, R.drawable.ic_profile_placeholder)
        )
        categoriesRecyclerView.adapter = SearchResultAdapter(searchResults) {
            val intent = Intent(context, ProviderProfileActivity::class.java)
            startActivity(intent)
        }
    }
}