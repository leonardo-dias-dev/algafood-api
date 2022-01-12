package com.algaworks.algafood.core.security.resourceserver;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    @interface Cozinhas {

        @PreAuthorize("@algaSecurity.podeConsultarCozinhas()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Consultar {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Editar {
        }

    }

    @interface Restaurantes {

        @PreAuthorize("@algaSecurity.podeConsultarRestaurantes()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Consultar {
        }

        @PreAuthorize("@algaSecurity.podeGerenciarCadastroRestaurantes()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Editar {
        }

        @PreAuthorize("@algaSecurity.podeGerenciarFuncionamentoRestaurantes(#restauranteId)")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Funcionamento {
        }

    }

    @interface Pedidos {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize(
                "hasAuthority('CONSULTAR_PEDIDOS') " +
                        "or @algaSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) " +
                        "or @algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)"
        )
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Buscar {
        }

        @PreAuthorize("@algaSecurity.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Pesquisar {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Criar {
        }

        @PreAuthorize("@algaSecurity.podeGerenciarPedidos(#codigoPedido)")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface GerenciarPedidos {
        }

    }

    @interface FormasPagamento {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Editar {
        }

        @PreAuthorize("@algaSecurity.podeConsultarFormasPagamento()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Consultar {
        }

    }

    @interface Cidades {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Editar {
        }

        @PreAuthorize("@algaSecurity.podeConsultarCidades()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Consultar {
        }

    }

    @interface Estados {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Editar {
        }

        @PreAuthorize("@algaSecurity.podeConsultarEstados()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Consultar {
        }

    }

    @interface UsuariosGruposPermissoes {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and @algaSecurity.usuarioAutenticadoIgual(#usuarioId)")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface AlterarPropriaSenha {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or @algaSecurity.usuarioAutenticadoIgual(#usuarioId))")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface AlterarUsuario {
        }

        @PreAuthorize("@algaSecurity.podeEditarUsuariosGruposPermissoes()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Editar {
        }


        @PreAuthorize("@algaSecurity.podeConsultarUsuariosGruposPermissoes()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Consultar {
        }

    }

    @interface Estatisticas {

        @PreAuthorize("@algaSecurity.podeConsultarEstatisticas()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Consultar {
        }

    }

}
