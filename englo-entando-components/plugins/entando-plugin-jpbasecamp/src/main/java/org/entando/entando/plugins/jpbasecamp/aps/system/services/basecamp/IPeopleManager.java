package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp;

import java.util.List;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.people.Person;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.people.PersonReference;

import com.agiletec.aps.system.exception.ApsSystemException;

public interface IPeopleManager {

	
	/**
	 * Get the people list
	 * @param serviceData
	 * @return
	 * @throws Throwable
	 */
	public List<PersonReference> getUsers(BasecampService serviceData) throws ApsSystemException;

	/**
	 * Load person details
	 * @param reference
	 * @param serviceData
	 * @return
	 * @throws Throwable
	 */
	public Person getPerson(PersonReference reference, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Gather currently logged user information
	 * @param serviceData
	 * @return
	 * @throws Throwable
	 */
	public Person whoAmI(BasecampService serviceData) throws ApsSystemException;

	/**
	 * Delete the given person
	 * @param reference
	 * @param serviceData
	 * @throws Throwable
	 */
	void deletePerson(PersonReference reference, BasecampService serviceData) throws ApsSystemException;
	
	/**
	 * Delete the given person
	 * @param reference
	 * @param serviceData
	 * @throws Throwable
	 */
	void deletePerson(Person reference, BasecampService serviceData) throws Throwable;
	
}
