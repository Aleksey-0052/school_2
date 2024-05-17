package hogwarts.school_2.service;

import hogwarts.school_2.model.Avatar;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AvatarService {

    Avatar findAvatar(Long studentId);

    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;


    // добавляем пагинацию
    List<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize);


}
