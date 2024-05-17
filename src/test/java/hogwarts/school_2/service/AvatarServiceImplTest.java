package hogwarts.school_2.service;

import hogwarts.school_2.model.Avatar;
import hogwarts.school_2.repository.AvatarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

import static hogwarts.school_2.controller.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvatarServiceImplTest {

    @Mock
    private AvatarRepository avatarRepositoryMock;

    @Mock
    private StudentService studentServiceMock;

    @InjectMocks
    private AvatarServiceImpl out;

    @Test
    void getAllAvatars() {

//        List<Avatar> expected = MOCK_AVATARS_PAGE_SIZE;
//        int pageNumber = 2;
//        int pageSize = 2;
//        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
//
//        when(avatarRepositoryMock.findAll(pageRequest).getContent())
//                .thenReturn(expected);
//
//        List<Avatar> actual = out.getAllAvatars(pageNumber, pageSize);
//
//        assertIterableEquals(expected, actual);
//        assertEquals(expected.size(), actual.size());
//        verify(avatarRepositoryMock, times(2)).findAll(pageRequest);

    }

}