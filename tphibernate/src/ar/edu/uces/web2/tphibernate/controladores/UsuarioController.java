package ar.edu.uces.web2.tphibernate.controladores;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import ar.edu.uces.web2.tphibernate.dao.UsuarioDAO;
import ar.edu.uces.web2.tphibernate.modelo.base.Usuario;
import ar.edu.uces.web2.tphibernate.modelo.form.UsuarioAutenticacionForm;
import ar.edu.uces.web2.tphibernate.validadores.UsuarioAutenticacionFormValidator;


//@SessionAttributes("usuario")
@Controller
public class UsuarioController {

	@Autowired
	private UsuarioAutenticacionFormValidator usuarioAutenticacionFormValidator;

	private UsuarioDAO usuarioDAO;

	@Autowired
	public void setUsuarioDao(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	@Autowired
	private SessionLocaleResolver localeResolver;
	
	@RequestMapping(value = "/autenticacion/login")
	public ModelAndView identificar(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/views/autenticacion/login.jsp","usuarioAutenticacionForm",new UsuarioAutenticacionForm());
	}
	
	@RequestMapping(value = "/autenticacion/validar")
	public ModelAndView validar(  @ModelAttribute("usuarioAutenticacionForm") UsuarioAutenticacionForm usuarioAutenticacionForm, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

		//TODO:no sabe si jugador viene de sesion o de get?
		
		this.usuarioAutenticacionFormValidator.validate(usuarioAutenticacionForm, result);	
		if (result.hasErrors()) {
			return new ModelAndView("/views/autenticacion/login.jsp");
		}
		
		//autenticar
		//lo guarda en sesion

		Usuario usuario=usuarioDAO.autenticar(usuarioAutenticacionForm.getNombreUsuario(), usuarioAutenticacionForm.getContrasenia());
		
		if (usuario!=null) {
			request.getSession().setAttribute("usuario",usuario);//TODO: pasarlo a asentar como parametro y como atributo de sesion
			cookiear(usuario,request,response,usuarioAutenticacionForm.getRecordarme());
			//todo: separar lengua_pais
			localeResolver.setLocale(request, response, new Locale(usuario.getIdioma()));
			//return new ModelAndView("/views/agenda/mostrarCalendario.do");//usurio y contraseña no coinciden	
			return new ModelAndView("/views/index.jsp");//usurio y contraseña  coinciden
		}
		else {
			request.setAttribute("error", "login.error.noAutenticado"); 		//TODO: completar
			return new ModelAndView("/views/autenticacion/login.jsp");			//usurio y contraseña no coinciden	
		}
	}
	
	public void cookiear(Usuario usuario, HttpServletRequest request, HttpServletResponse response, boolean recordarme)
	{
		boolean encontrado=false;
		Cookie cookieUsuario = obtenerCookie(request, "nombreUsuario");
		Cookie cookieContraseña = obtenerCookie(request, "contrasenia");
		if ((cookieUsuario!=null)&&(cookieContraseña!=null))
			encontrado=true;
         
		//si esta marcado recordar, guarda una cookie
		if(recordarme)
		{
			//TODO: Si existe, actualizar fecha 
			//si no  existe
			if (!encontrado)
			{
				//crearla
				cookieUsuario=new Cookie("nombreUsuario",usuario.getNombreUsuario());
				cookieUsuario.setMaxAge(60*60*24*365);
				cookieUsuario.setPath("/");
				response.addCookie(cookieUsuario);
				
				cookieContraseña=new Cookie("contrasenia",usuario.getContrasenia().getValor());
				cookieContraseña.setMaxAge(60*60*24*365);
				cookieContraseña.setPath("/");
				response.addCookie(cookieContraseña);
			}
		}
		else //si no hay que recordar
		{
			
			//si esta la cookie
			if (encontrado)
			{
				//TODO: sacarla
				
			}
 
		}
	}
	
	
	@RequestMapping(value = "/logout/logout")
	public ModelAndView logout(  HttpServletRequest request, HttpServletResponse response) {
		//TODO: sacar usuario de sesion	
		//request.getSession().setAttribute("usuario",null);
		request.getSession().invalidate();
		
		//TODO: sacar usuario de cookie
		Cookie cookieUsuario = obtenerCookie(request, "nombreUsuario");
		if (cookieUsuario!=null) {
			cookieUsuario.setMaxAge(0);
			cookieUsuario.setPath("/");
			response.addCookie(cookieUsuario);
		}
		Cookie cookieContraseña = obtenerCookie(request, "contrasenia");
		if (cookieContraseña!=null) {
			cookieContraseña.setMaxAge(0);
			cookieContraseña.setPath("/");
			response.addCookie(cookieContraseña);
		}
		return new ModelAndView("/views/index.jsp");
	}
	
    public Cookie obtenerCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }
	

}
