package com.mersiades.awcdata.services.jpa;

//@ExtendWith(MockitoExtension.class)
//class ThreatJpaServiceTest {
//
//    @Mock
//    ThreatRepository threatRepository;
//
//    @InjectMocks
//    ThreatJpaService service;
//
//    Threat mockThreat;
//
//    @BeforeEach
//    void setUp() {
//        mockThreat = new Threat("threatId03");
//    }
//
//    @Test
//    void findAll() {
//        GameRole mockGameRole = new GameRole("threatId05", Roles.MC, new Game("threatId06", "741573502452105236", "741573503710527498", "mock game"),new User("discordId01"));
//        Set<Threat> returnThreats = new HashSet<>();
//        returnThreats.add(new Threat("threatId04"));
//        returnThreats.add(new Threat("Gritsnot", Threats.AFFLICTION, "to infect"));
//
//        when(threatRepository.findAll()).thenReturn(returnThreats);
//
//        Set<Threat> threats = service.findAll();
//
//        assertNotNull(threats);
//        assertEquals(2, threats.size());
//    }
//
//    @Test
//    void findById() {
//        when(threatRepository.findById(any())).thenReturn(Optional.of(mockThreat));
//
//        Threat threat = service.findById("threatId03");
//
//        assertNotNull(threat);
//
//        assertEquals(threat.getId(), mockThreat.getId());
//    }
//
//    @Test
//    void save() {
//        when(threatRepository.save(any())).thenReturn(mockThreat);
//
//        Threat threat = service.save(mockThreat);
//
//        assertNotNull(threat);
//        assertEquals(threat.getId(), mockThreat.getId());
//        verify(threatRepository).save(any());
//    }
//
//    @Test
//    void delete() {
//        service.delete(mockThreat);
//
//        verify(threatRepository).delete(any());
//    }
//
//    @Test
//    void deleteById() {
//        service.deleteById("threatId03");
//
//        verify(threatRepository).deleteById(any());
//    }
//}