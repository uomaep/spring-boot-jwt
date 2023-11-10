package uomaep.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import uomaep.dto.UserDTO;

@Mapper
@Repository
public interface UserDataMapper {
    UserDTO findUserByEmail(String email);

    void saveRefreshToken(int id, String token);

    int updateRefreshToken(int id, String token);
}
