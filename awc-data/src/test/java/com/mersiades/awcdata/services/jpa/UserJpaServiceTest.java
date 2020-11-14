package com.mersiades.awcdata.services.jpa;

//@ExtendWith(MockitoExtension.class)
//class UserJpaServiceTest {
//
//    @Mock
//    UserRepository userRepository;
//
//    @InjectMocks
//    UserJpaService service;
//
//    User mockUser;
//
//    @BeforeEach
//    void setUp() {
//        mockUser = new User("discordId01");
//    }
//
//    @Test
//    void findAll() {
//        Set<User> returnUsers = new HashSet<>();
//        returnUsers.add(new User("discordId02"));
//        returnUsers.add(new User("696484065859076146"));
//
//        when(userRepository.findAll()).thenReturn(returnUsers);
//
//        Set<User> users = service.findAll();
//
//        assertNotNull(users);
//        assertEquals(2, users.size());
//    }
//
//    @Test
//    void findById() {
//        when(userRepository.findById(any())).thenReturn(Optional.of(mockUser));
//
//        User user = service.findById("discordId01");
//
//        assertNotNull(user);
//        assertEquals(user.getId(), mockUser.getId());
//    }
//
//    @Test
//    void findByIdNotFound() {
//        when(userRepository.findById(any())).thenReturn(Optional.empty());
//
//        User user = service.findById("discordId01");
//
//        assertNull(user);
//    }
//
//    @Test
//    void save() {
//        when(userRepository.save(any())).thenReturn(mockUser);
//
//        User user = service.save(mockUser);
//
//        assertNotNull(user);
//        verify(userRepository).save((any()));
//    }
//
//    @Test
//    void delete() {
//        service.delete(mockUser);
//
//        verify(userRepository).delete(any());
//    }
//
//    @Test
//    void deleteById() {
//        service.deleteById("discordId01");
//
//        verify(userRepository).deleteById(any());
//    }
//}