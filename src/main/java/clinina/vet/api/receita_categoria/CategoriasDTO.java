package clinina.vet.api.receita_categoria;

import clinina.vet.api.despesa_categoria.DespesaCategoriaDTO;

import java.util.List;

public record CategoriasDTO(
     List<ReceitaCategoriaDTO> receitas,
     List<DespesaCategoriaDTO> despesas
) { }
