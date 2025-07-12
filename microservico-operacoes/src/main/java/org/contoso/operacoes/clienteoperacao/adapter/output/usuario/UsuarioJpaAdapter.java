package org.contoso.operacoes.clienteoperacao.adapter.output.usuario;

import org.contoso.operacoes.clienteoperacao.domain.entity.Usuario;
import org.contoso.operacoes.clienteoperacao.port.output.UsuarioRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioJpaAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaClient usuarioJpaClient;

    public UsuarioJpaAdapter(UsuarioJpaClient usuarioJpaClient) {
        this.usuarioJpaClient = usuarioJpaClient;
    }

    @Override
    public Optional<Usuario> findById(String usuarioId) {
        return usuarioJpaClient.findById(Integer.valueOf(usuarioId))
                .map(usuarioDatabase -> Usuario.builder()
                        .id(usuarioDatabase.getId())
                        .nome(usuarioDatabase.getNome())
                        .email(usuarioDatabase.getEmail())
                        .porcentagemCorretagem(usuarioDatabase.getPorcentagemCorretagem())
                        .build()
                );
    }
}
