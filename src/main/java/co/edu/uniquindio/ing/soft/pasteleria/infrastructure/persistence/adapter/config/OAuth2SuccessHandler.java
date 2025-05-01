package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter.config;

import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.UserEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.UserJpaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserJpaRepository userJpaRepository;
    private final JWTUtils jwtUtils;
    private final String url = "https://pasteleria-feliz.web.app";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        // 1. Obtener token de autenticación de Google
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        // 2. Obtener los atributos del perfil de Google
        Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();

        // 3. Extraer información importante (puedes obtener más si quieres)
        String email = (String) attributes.get("email");
        String nombre = (String) attributes.get("name");

        // 4. (Opcional) Guardar o registrar el usuario en tu base de datos aquí

        Optional<UserEntity> userEntity = userJpaRepository.findByEmail(email);

        if (userEntity.isEmpty()) {
            String errorRedirectUrl = url + "/login?mensaje=Usuario%20no%20registrado";
            response.sendRedirect(errorRedirectUrl);
            return;
        }

        Map<String, Object> map = construirClaims(userEntity.get());

        // 5. Generar JWT personalizado para tu aplicación
        String token = jwtUtils.generarToken(email, map); // Puedes cambiar el rol si aplica

        // 6. Redirigir al frontend con el token (Angular debe leerlo)
        String redirectUrl = url + "/login-success?token=" + token;

        response.sendRedirect(redirectUrl);
    }

    private Map<String, Object> construirClaims(UserEntity userEntity) {
        return Map.of(
                "user_id", userEntity.getId(),
                "email", userEntity.getEmail(),
                "isAdmin", userEntity.getIsAdmin()
        );
    }
}