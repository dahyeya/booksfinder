package mschu.books.db;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface Top10Repository extends CrudRepository<Top10, String> {
	@Query(value = "select top 10 * from Top10 order by search desc", nativeQuery = true)
	public List<Top10> findAllSortTop10();
}