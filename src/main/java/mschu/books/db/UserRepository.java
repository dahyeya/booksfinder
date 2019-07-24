package mschu.books.db;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<User, String> {
	
	@Query("from User where account=:account and (select password from User where account=:account)=HASH('SHA256', :password, 64)")
	public User findByValidUser(@Param("account") String account, @Param("password") String password);
	
	@Transactional
	@Modifying
	@Query(value = "insert into user (account, password) values (:account, HASH('SHA256', :password, 64))", nativeQuery = true)
	public void saveHashUser(@Param("account") String account, @Param("password") String password);
	
}
