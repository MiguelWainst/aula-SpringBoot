package com.dev_curso.libraryapi.security;

import com.dev_curso.libraryapi.model.Usuario;
import com.dev_curso.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService usuarioService;
    private final PasswordEncoder encoder;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaDigitada = authentication.getCredentials().toString();

        Usuario usuario = usuarioService.obterPorLogin(login);
        if (usuario == null) {
            throw getErroCredencialNaoEncontrado();
        }
        String senhaCripto = usuario.getSenha();
        boolean senhasBatem = encoder.matches(senhaDigitada, senhaCripto);

        if (senhasBatem) {
            return new CustomAuthentication(usuario);
        }
        throw getErroCredencialNaoEncontrado();
    }

    private @NonNull UsernameNotFoundException getErroCredencialNaoEncontrado() {
        return new UsernameNotFoundException("Usuário e/ou senha incorreto!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
