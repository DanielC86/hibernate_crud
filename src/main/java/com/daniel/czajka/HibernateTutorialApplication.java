package com.daniel.czajka;

import entity.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

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
			Users newUser = new Users("James", "james@mail");

			//start a transaction
			session.beginTransaction();

			//save the users object
			session.save(newUser);

			System.out.println("Saving new user: " + newUser);
			//commit transaction
			session.getTransaction().commit();

			//find out user id
			System.out.println("Getting saved user ID (Primary key): " + newUser.getId());


			//get new session
			//start transaction
			session = factory.getCurrentSession();
			session.beginTransaction();

			//retrieve user based on ID
			System.out.println("\n Getting saved user ID (Primary key): " + newUser.getId());

			Users readUser = session.get(Users.class, newUser.getId());

			System.out.println("\n Reading complete: " + readUser);

			//commit the transaction
			session.getTransaction().commit();


			//query all users
			session = factory.getCurrentSession();
			session.beginTransaction();

			List<Users> theUsers = (List<Users>) session.createQuery("from Users").list();

			//display users
			displayUsers(theUsers);

			//query all users who's name starts with M
			theUsers = session.createQuery("from Users u where u.name like 'M%'").list();

			//display users w name starts with M letter
			displayUsers(theUsers);

			//query users which name starts with M or D
			theUsers = session.createQuery("from Users u where u.name like 'M%' OR u.name like 'D%'").list();

			//display users which name starts from D or M
			displayUsers(theUsers);

			//commit the transaction
			session.getTransaction().commit();


			System.out.println("all done");

		}
		finally {
			factory.close();
		}



	}

	private static void displayUsers(List<Users> theUsers) {
		System.out.println("Here is a list of users: ");
		for(Users tempUser : theUsers){
			System.out.println(tempUser);
		}
	}

}
