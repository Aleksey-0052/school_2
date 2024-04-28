package hogwarts.school_2.service;

import hogwarts.school_2.model.Avatar;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AvatarService {

    Avatar findAvatar(Long studentId);

    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;


}
