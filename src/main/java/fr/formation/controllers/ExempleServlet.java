package fr.formation.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.formation.models.Comment;
import fr.formation.models.Personne;
import fr.formation.models.Topic;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class ExempleServlet
 */
public class ExempleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ExempleServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int nbrTopic = 0;
		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU_JPA");
		EntityManager entityManager = emf.createEntityManager();
		if(entityManager.isOpen()) {
			
			Topic topic = new Topic();
			topic.setName("Un sujet");
			topic.setDescription("Une description");
			
			
			
			boolean transactionOk = false;
			entityManager.getTransaction().begin();
			entityManager.persist(topic);
			transactionOk = true;
			if(transactionOk) {
		        entityManager.getTransaction().commit();
		    }
		    else {
		        entityManager.getTransaction().rollback();
		    }
			
			
			 
			
			Comment comment = new Comment();
			entityManager.refresh(topic);
			comment.setCommentaire("Un commentaire");
			comment.setTopic(topic);
			
			transactionOk = false;
			entityManager.getTransaction().begin();
			
			entityManager.persist(comment);
			transactionOk = true;
			if(transactionOk) {
		        entityManager.getTransaction().commit();
		    }
		    else {
		        entityManager.getTransaction().rollback();
		    }
			
			
			
			
			Optional<List> topics = Optional.ofNullable(entityManager.createNativeQuery("select * from topic", Topic.class).getResultList());
			entityManager.close();
			nbrTopic = topics.get().size();
		
		}
		
		response.getWriter().append("Nombre de topic : " + nbrTopic);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
