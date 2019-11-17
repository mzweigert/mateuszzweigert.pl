package pl.mateuszzweigert.site.info;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DynamicInfoRepository extends JpaRepository<DynamicInfo, String> {


    @Cacheable("dynamicValues")
    @Query("Select value from DynamicInfo where key = ?1 and language = ?2")
    String findValueByKeyAndLanguage(String key, String language);
}