package edu.byu.edge.person.basic;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 4/9/13
 * Time: 11:28 AM
 */
public class PersonAddress {

	protected BasicPerson basicPerson;

	protected Address address;

	public PersonAddress() {
	}

	public PersonAddress(BasicPerson basicPerson, Address address) {
		this.basicPerson = basicPerson;
		this.address = address;
	}

	public BasicPerson getBasicPerson() {
		return basicPerson;
	}

	public void setBasicPerson(BasicPerson basicPerson) {
		this.basicPerson = basicPerson;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
