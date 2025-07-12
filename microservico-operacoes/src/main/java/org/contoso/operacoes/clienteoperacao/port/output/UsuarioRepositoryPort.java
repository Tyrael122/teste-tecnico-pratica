package org.contoso.operacoes.clienteoperacao.port.output;

import org.contoso.operacoes.clienteoperacao.domain.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepositoryPort {
    Optional<Usuario> findById(String usuarioId);
}
