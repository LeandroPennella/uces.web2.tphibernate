package ar.edu.uces.web2.tphibernate.modelo.form;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ar.edu.uces.web2.tphibernate.modelo.base.Evento;
import ar.edu.uces.web2.tphibernate.modelo.base.Invitacion;
import ar.edu.uces.web2.tphibernate.modelo.base.Reunion;
import ar.edu.uces.web2.tphibernate.modelo.base.Sala;
import ar.edu.uces.web2.tphibernate.modelo.base.Usuario;
//import ar.edu.uces.web2.tphibernate.modelo.base.UsuarioInvitado;

public class ReunionForm extends EventoForm {

	private String temario;
	private long idSala;																	//la sala elegida
	private List<Sala>salas=new ArrayList<Sala>(); 											//todas las salas posibles
	
	private int idEstado;																	//noConfirmado, aceptado o cancelado
	private String estado;																	//reunionAutor//reunionNoConfirmado//reunionConfirmada//reunionCancelada
	
	private Set<Invitacion> invitaciones=new HashSet<Invitacion>();							//invitaciones actuales solo para mostrar
	private int[] invitados;																//invitados a establecer
	
	
//	private List<Invitacion> usuariosInvitaciones=new ArrayList<Invitacion>();
	
	//private List<Integer> idsInvitados=new ArrayList<Integer>() ;							//los usuarios elegidos	en el formulario ////TODO: reemplazar por mapa invitados
	//private List<UsuarioInvitado> usuariosInvitados=new ArrayList<UsuarioInvitado>();//	//todos los usuarios posibles, y seteados los agregados //TODO: listar solo los usuarios que no estar invitados 
	
	/*
	 * en desuso por autocommpletar
	 
	 * private List<String> tokensInvitadosMasConfirmacion=new ArrayList<String>();							//lista de pares idUsuario|confirmacion elegidoen el formulario//todos los usuarios posibles, y seteados los agregados //TODO: Ajax: listar solo los usuarios que no estar invitados
	 * private Map<Usuario,Integer> mapaUsuariosMasConfirmacion=new TreeMap<Usuario,Integer>();			//usuario/idConfirmacion
	*/
	
	public ReunionForm(){}

	//public ReunionForm(Evento evento){super (evento);}//para visualizar en Calendario > no, x invitaciones
	
	public ReunionForm(Reunion reunion)
	{
		this.setIdEvento(Long.toString(reunion.getId()));
		this.setTitulo(reunion.getTitulo());
		SimpleDateFormat dateFormatter=new SimpleDateFormat("dd/MM/yyyy");
		this.setFecha(dateFormatter.format(reunion.getFecha()));			
			
		this.setHoraInicio(reunion.getHoraInicio());		
		this.setHoraFin(reunion.getHoraFin());	
		
		this.setAutor(reunion.getAutor());
		
		//this.setEstado(this.obtenerEstado(usuarioLogueado)); usuarioLogueado ==usuarioActual???
	
		//reunionForm.setEstado(reunion.obtenerEstado(usuarioLogueado));
		
		//---------------------------------------------
		this.setTemario(reunion.getTemario());
		this.setIdSala(reunion.getSala().getId());
		//this.setSalas(salaDAO.getAll());
		
		Set<Invitacion>invitacionesHechas=reunion.getInvitaciones();
		this.setInvitaciones(invitacionesHechas);
	}
	
	public String getTemario() {
		return temario;
	}
	public void setTemario(String temario) {
		this.temario = temario;
	}
	public long getIdSala() {
		return idSala;
	}
	public void setIdSala(long idSala) {
		this.idSala = idSala;
	}

	public List<Sala> getSalas() {
		return salas;
	}
	public void setSalas(List<Sala> salas) {
		this.salas = salas;
	}

	public Set<Invitacion> getInvitaciones() {
		return invitaciones;
	}
	public void setInvitaciones(Set<Invitacion> invitaciones) {
		this.invitaciones = invitaciones;
	}
	
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int[] getInvitados() {
		return invitados;
	}

	public void setInvitados(int[] invitados) {
		this.invitados = invitados;
	}

/*
 * en desuso por autocommpletar
 
	public List<String> getTokensInvitadosMasConfirmacion() {
		return tokensInvitadosMasConfirmacion;
	}

	public void setTokensInvitadosMasConfirmacion(List<String> tokensInvitadosMasConfirmacion) {
		this.tokensInvitadosMasConfirmacion = tokensInvitadosMasConfirmacion;
	}

	public Map<Usuario, Integer> getMapaUsuariosMasConfirmacion() {
		return mapaUsuariosMasConfirmacion;
	}

	public void setMapaUsuariosMasConfirmacion(Map<Usuario, Integer> mapaUsuariosMasConfirmacion) {
		this.mapaUsuariosMasConfirmacion = mapaUsuariosMasConfirmacion;
	}
*/	

	
	/*
	public List<Invitacion> getUsuariosInvitaciones() {
		return usuariosInvitaciones;
	}

	public void setUsuariosInvitaciones(List<Invitacion> usuariosInvitaciones) {
		usuariosInvitaciones = usuariosInvitaciones;
	}
*/
	

	@Override
	public String obtenerEstado(Usuario usuario){
		/*
		System.out.println("obteniendo estado de: " + this.getTitulo());
		System.out.println("| usuario:"+usuario.getNombre());
		System.out.println("| autor:"+this.getAutor().getNombre());
		System.out.println("| usuario.getId: "+ usuario.getId());
		System.out.println("| getAutor.getId: "+ this.getAutor().getId());
		*/
		String sEstado="reunion";
		
		
		
		//System.out.println("| invitaciones: "+this.getInvitaciones().size());
		if (this.getAutor().getId()==usuario.getId())
			sEstado="reunionAutor";
		else  {
			
			//System.out.println("| - invitaciones...");			
			for(Invitacion invitacion: this.getInvitaciones())
			{
				//System.out.println("| - getUsuario.getId: "+ invitacion.getUsuario().getId());
				//System.out.println("| - invitacion.getAceptado(): "+ invitacion.getAceptado());
				if (invitacion.getUsuario().getId()==usuario.getId())
				{
					switch(invitacion.getAceptado()){
						case 0:
							sEstado="reunionNoConfirmado";
							break;
						case 1:
							sEstado="reunionConfirmada";
							break;
						default :
							sEstado="reunionCancelada";
							break;
					}		
				}
			}
		}
		//System.out.println("| estado: "+ sEstado);
		
		return sEstado;
	}

}
