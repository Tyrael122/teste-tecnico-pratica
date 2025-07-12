package org.contoso.operacoes.clienteoperacao.adapter.output.posicao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PosicaoJpaClient extends JpaRepository<PosicaoUsuarioDatabase, Integer> {
    void deleteByUsuarioIdAndAtivoId(Integer usuarioId, Integer ativoId);

    Optional<PosicaoUsuarioDatabase> findByUsuarioIdAndAtivoId(Integer usuarioId, Integer ativoId);
}
