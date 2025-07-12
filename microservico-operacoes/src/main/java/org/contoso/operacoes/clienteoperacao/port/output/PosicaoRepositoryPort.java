package org.contoso.operacoes.clienteoperacao.port.output;

import org.contoso.operacoes.clienteoperacao.domain.entity.PosicaoUsuario;

import java.util.Optional;

public interface PosicaoRepositoryPort {

    Optional<PosicaoUsuario> consultarPosicaoUsuario(Integer usuarioId, Integer ativoId);

    PosicaoUsuario atualizarPosicaoUsuario(PosicaoUsuario posicaoUsuario);
}
