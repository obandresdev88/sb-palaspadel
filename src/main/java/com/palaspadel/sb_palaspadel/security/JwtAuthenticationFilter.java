package com.palaspadel.sb_palaspadel.security;

import com.palaspadel.sb_palaspadel.entities.Usu;
import com.palaspadel.sb_palaspadel.repositories.UsuRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Filtro de autenticación JWT que intercepta las solicitudes HTTP para validar tokens JWT
 * y establecer el contexto de seguridad si el token es válido.
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsuRepository usuRepository;

    /**
     * Filtra las solicitudes HTTP para autenticar usuarios basándose en tokens JWT.
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        try {
            if (SecurityContextHolder.getContext().getAuthentication() != null || !jwtUtil.isValid(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = jwtUtil.parseClaims(token);
            Long usuarioId = claims.get("id", Number.class).longValue();
            List<String> usuarioRoles = claims.get("roles", List.class);

//           Construir lista de autoridades (roles) a partir de los roles del token
//           Si la lista de roles es nula, se asigna una lista vacía
//           si no es nula, se mapea cada rol a una instancia de SimpleGrantedAuthority
            List<SimpleGrantedAuthority> authorities = (usuarioRoles == null)
                    ? List.of()
                    : usuarioRoles.stream()
                    .map(r -> new SimpleGrantedAuthority("ROLE_" + r.toUpperCase()))
                    .collect(Collectors.toList());

            Optional<Usu> optUsu = usuRepository.findById(usuarioId.intValue());
            if (optUsu.isPresent()) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(optUsu.get().getUsuema(), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
