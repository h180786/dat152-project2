/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.OrderNotFoundException;
import no.hvl.dat152.rest.ws.exceptions.UserNotFoundException;
import no.hvl.dat152.rest.ws.model.Order;
import no.hvl.dat152.rest.ws.model.User;
import no.hvl.dat152.rest.ws.repository.UserRepository;

/**
 * @author tdoy
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	
	public List<User> findAllUsers(){
		
		List<User> allUsers = (List<User>) userRepository.findAll();
		
		return allUsers;
	}
	
	public User findUser(Long userid) throws UserNotFoundException {
		
		User user = userRepository.findById(userid)
				.orElseThrow(()-> new UserNotFoundException("User with id: "+userid+" not found"));
		
		return user;
	}
	
	
	// TODO public User saveUser(User user)
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	// TODO public void deleteUser(Long id) throws UserNotFoundException
	public void deleteUser(Long id) throws UserNotFoundException {
			User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User with id: "+id+" not found"));
			userRepository.delete(user);
	}
	
	// TODO public User updateUser(User user, Long id)
	public User updateUser(User user, Long id) {
		User userToUpdate = userRepository.findById(id).orElse(null);
		if (userToUpdate != null) {
			userToUpdate.setFirstname(user.getFirstname());
			userToUpdate.setLastname(user.getLastname());
			return userRepository.save(userToUpdate);
		}
		return null;
	}
	
	// TODO public Set<Order> getUserOrders(Long userid)
	public Set<Order> getUserOrders(Long userid) {

		User user = userRepository.findById(userid).orElse(null);
		if (user != null) {
			return user.getOrders();
		}
		return null;
	}
	
	// TODO public Order getUserOrder(Long userid, Long oid)
	public Order getUserOrder(Long userid, Long oid) {
		User user = userRepository.findById(userid).orElse(null);
		if (user != null) {
			Set<Order> orders = user.getOrders();
			Iterator<Order> it = orders.iterator();
			while (it.hasNext()) {
				Order order = it.next();
				if (order.getId() == oid) {
					return order;
				}
			}
		}
		return null;
	}
	
	// TODO public void deleteOrderForUser(Long userid, Long oid)
	public void deleteOrderForUser(Long userid, Long oid) {
		User user = userRepository.findById(userid).orElse(null);
		if (user != null) {
			Set<Order> orders = user.getOrders();
			Iterator<Order> it = orders.iterator();
			while (it.hasNext()) {
				Order order = it.next();
				if (order.getId() == oid) {
					orders.remove(order);
					userRepository.save(user);
					return;
				}
			}
		}
	}
	
	// TODO public User createOrdersForUser(Long userid, Order order)
	public User createOrdersForUser(Long userid, Order order) {
		User user = userRepository.findById(userid).orElse(null);
		if (user != null) {
			Set<Order> orders = user.getOrders();
			orders.add(order);
			userRepository.save(user);
			return user;
		}
		return null;
	}
}
