package smoketest.hateoas.web;

import smoketest.hateoas.domain.Customer;
import smoketest.hateoas.domain.CustomerRepository;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customers")
@ExposesResourceFor(Customer.class)
public class CustomerController {

	private final CustomerRepository repository;

	private final EntityLinks entityLinks;

	public CustomerController(CustomerRepository repository, EntityLinks entityLinks) {
		this.repository = repository;
		this.entityLinks = entityLinks;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	HttpEntity<CollectionModel<Customer>> showCustomers() {
		CollectionModel<Customer> resources = CollectionModel.of(this.repository.findAll());
		resources.add(this.entityLinks.linkToCollectionResource(Customer.class));
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	HttpEntity<EntityModel<Customer>> showCustomer(@PathVariable Long id) {
		EntityModel<Customer> resource = EntityModel.of(this.repository.findOne(id));
		resource.add(this.entityLinks.linkToItemResource(Customer.class, id));
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

}
