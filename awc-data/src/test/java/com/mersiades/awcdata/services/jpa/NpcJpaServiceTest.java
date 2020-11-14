package com.mersiades.awcdata.services.jpa;

//@ExtendWith(MockitoExtension.class)
//class NpcJpaServiceTest {
//
//    @Mock
//    NpcRepository npcRepository;
//
//    @InjectMocks
//    NpcJpaService service;
//
//    Npc mockNpc;
//
//    @BeforeEach
//    void setUp() {
//        mockNpc = new Npc("npcId01", "Butch");
//    }
//
//    @Test
//    void findAll() {
//        Set<Npc> returnNpcs = new HashSet<>();
//        returnNpcs.add(new Npc("npcId02", "Sarah"));
//        returnNpcs.add(new Npc("npcId03", "Reginald"));
//
//        when(npcRepository.findAll()).thenReturn(returnNpcs);
//
//        Set<Npc> npcs = service.findAll();
//
//        assertNotNull(npcs);
//        assertEquals(2, npcs.size());
//    }
//
//    @Test
//    void findById() {
//        when(npcRepository.findById(any())).thenReturn(Optional.of(mockNpc));
//
//        Npc npc = service.findById("npcId01");
//
//        assertNotNull(npc);
//    }
//
//    @Test
//    void save() {
//        when(npcRepository.save(any())).thenReturn(mockNpc);
//
//        Npc npc = npcRepository.save(mockNpc);
//
//        assertNotNull(npc);
//        assertEquals(npc.getId(), mockNpc.getId());
//        verify(npcRepository).save(any());
//    }
//
//    @Test
//    void delete() {
//        npcRepository.delete(mockNpc);
//
//        verify(npcRepository).delete(any());
//    }
//
//    @Test
//    void deleteById() {
//        npcRepository.deleteById("npcId01");
//
//        verify(npcRepository).deleteById(any());
//    }
//}