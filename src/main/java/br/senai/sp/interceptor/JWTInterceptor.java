package br.senai.sp.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.auth0.jwt.JWTVerifier;

import br.senai.sp.controller.UsuarioController;

public class JWTInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Atributos
		HandlerMethod method = (HandlerMethod) handler;

		// Acesse nome de metodo, classe, parametros...
		/*
		 * System.out.println("Método chamado : " +
		 * method.getMethod().getName());
		 * System.out.println("Controller chamado : " +
		 * method.getBean().getClass().getSimpleName());
		 */

		if (request.getRequestURI().contains("login") || request.getRequestURI().contains("usuario")) {
			return true;
		} else {
			// Atributo
			String token = null;

			try {
				token = request.getHeader("Authorization");

				// Criar verificador JWT
				JWTVerifier verificador = new JWTVerifier(UsuarioController.SECRET);
				Map<String, Object> claims = verificador.verify(token);
				System.out.println("Nome do Usuario: " + claims.get("nome_user"));
				return true;
			} catch (Exception e) {
				e.printStackTrace();

				// Verifica se token é null
				if (token == null) {
					// Resposta de não autorizado
					response.sendError(HttpStatus.UNAUTHORIZED.value());
				} else {
					// Resposta de proibido
					response.sendError(HttpStatus.FORBIDDEN.value());
				}


			}
			// Retornando...
			return false;
		}
	}
}
