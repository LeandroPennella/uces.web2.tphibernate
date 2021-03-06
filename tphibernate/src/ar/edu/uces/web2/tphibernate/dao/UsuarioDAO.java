package ar.edu.uces.web2.tphibernate.dao;



import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.uces.web2.tphibernate.modelo.base.Usuario;


@Transactional(readOnly = true)
@Component
public class UsuarioDAO {
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Usuario get(long id) {
		Session session = sessionFactory.getCurrentSession();
		return (Usuario) session.get(Usuario.class, id);// no tira excepcion si no lo encuentra	
		}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(Usuario usuario) {
		Session session = sessionFactory.getCurrentSession();
		
		//guarda el id en la instancia
		session.saveOrUpdate(usuario);
		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Usuario autenticar(String nombreUsuario, String contrasenia) {
		Session session = sessionFactory.getCurrentSession();
		Usuario usuario=null;
		String hql="from " +Usuario.class.getName()+"  as u where u.nombreUsuario = '"+nombreUsuario+"' and u.contrasenia.valor='"+contrasenia+"'";
				
		Query q=session.createQuery(hql);
		//.setString("pU", nombreUsuario)
		//.setString("pC", contrasenia);
		usuario= (Usuario)q.uniqueResult();
		return usuario;
	
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Usuario> getAll()
	{
		Session session = sessionFactory.getCurrentSession();
		Query q=session.createQuery("from " +Usuario.class.getName());
		List<Usuario>usuarios=(List<Usuario>)q.list();
		return usuarios;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Usuario> find(String parte)
	{
		Session session = sessionFactory.getCurrentSession();
		Query q=session.createQuery(""
				+ "from " +Usuario.class.getName()+"  as u "
						+ "where "
							+ "u.nombreUsuario like '%"+parte+"%' or "
							+ "u.nombre like '%"+parte+"%' or "
							+ "u.apellido like '%"+parte+"%'");
		
		List<Usuario>usuarios=(List<Usuario>)q.list();
		return usuarios;
	}
}
