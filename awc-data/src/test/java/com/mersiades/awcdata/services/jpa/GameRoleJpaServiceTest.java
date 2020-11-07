package com.mersiades.awcdata.services.jpa;

//@ExtendWith(MockitoExtension.class)
//class GameRoleJpaServiceTest {
//
//    @Mock
//    GameRoleRepository gameRoleRepository;
//
//    @InjectMocks
//    GameRoleJpaService service;
//
//    GameRole mockGameRole;
//
//    Game mockGame;
//
//    User mockUser;
//
//    @BeforeEach
//    void setUp() {
//        mockGame = new Game("gameId02", "823458920374529070", "123876129847590347", "Mock Game");
//        mockUser = new User("discordId03");
//        mockGameRole = new GameRole("gameRoleId01", Roles.MC, mockGame, mockUser);
//    }
//
//    @Test
//    void findAll() {
//        Set<GameRole> returnGameRoleSet = new HashSet<>();
//        returnGameRoleSet.add(new GameRole("gameRoleId02", Roles.PLAYER, mockGame, mockUser));
//        returnGameRoleSet.add(new GameRole("gameRoleId03", Roles.MC, mockGame, mockUser));
//
//        when(gameRoleRepository.findAll()).thenReturn(returnGameRoleSet);
//
//        Set<GameRole> gameRoles = service.findAll();
//
//        assertNotNull(gameRoles);
//        assertEquals(2, gameRoles.size());
//    }
//
//    @Test
//    void findById() {
//        when(gameRoleRepository.findById(any())).thenReturn(Optional.of(mockGameRole));
//
//        GameRole gameRole = service.findById("gameRoleId01");
//
//        assertNotNull(gameRole);
//    }
//
//    @Test
//    void save() {
//        when(gameRoleRepository.save(any())).thenReturn(mockGameRole);
//
//        GameRole gameRole = service.save(mockGameRole);
//
//        assertNotNull(gameRole);
//        verify(gameRoleRepository).save(any());
//    }
//
//    @Test
//    void delete() {
//        gameRoleRepository.delete(mockGameRole);
//
//        verify(gameRoleRepository).delete(any());
//    }
//
//    @Test
//    void deleteById() {
//        gameRoleRepository.deleteById("gameRoleId01");
//
//        verify(gameRoleRepository).deleteById(any());
//    }
//}