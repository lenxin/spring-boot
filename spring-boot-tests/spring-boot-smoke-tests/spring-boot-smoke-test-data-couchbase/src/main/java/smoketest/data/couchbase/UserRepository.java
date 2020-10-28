package smoketest.data.couchbase;

import org.springframework.data.couchbase.repository.CouchbaseRepository;

public interface UserRepository extends CouchbaseRepository<User, String> {

}
