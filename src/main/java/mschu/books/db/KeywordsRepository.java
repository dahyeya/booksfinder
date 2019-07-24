package mschu.books.db;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface KeywordsRepository extends CrudRepository<Keywords, KeywordsIdentity> {
	
	@Query(value = "select * from keywords where id=:id ORDER BY issue desc ", nativeQuery = true)
	public List<Keywords> findViewKeywordHistory(@Param("id") int id);
}