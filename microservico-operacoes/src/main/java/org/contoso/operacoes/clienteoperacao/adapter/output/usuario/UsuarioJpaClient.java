package org.contoso.operacoes.clienteoperacao.adapter.output.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioJpaClient extends JpaRepository<UsuarioDatabase, Integer> {
}
