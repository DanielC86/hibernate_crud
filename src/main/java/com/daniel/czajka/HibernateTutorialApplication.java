package com.daniel.czajka;

import entity.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HibernateTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(HibernateTutorialApplication.class, args);

		//create session factory
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Users.class)
				.buildSessionFactory();

		//create session
		Session session = factory.getCurrentSession();

		try {

			//create a student object
			System.out.println("creating new user object");
			Users newUser = new Users("monika", "monika@mail");
			//start a transaction
			session.beginTransaction();

			//save the users object
			session.save(newUser);
			System.out.println("Saving new user");
			//commit transaction
			session.getTransaction().commit();

			System.out.println("all done");

		}
		finally {
			factory.close();
		}



	}

}
