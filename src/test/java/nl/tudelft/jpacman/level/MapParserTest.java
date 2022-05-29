package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MapParserTest {

    private MapParser mapParser;
    private final LevelFactory levelCreator = mock(LevelFactory.class);
    private final BoardFactory boardFactory = mock(BoardFactory.class);

    @BeforeEach
    void setup(){
        mapParser = new MapParser(levelCreator,boardFactory);
        when(boardFactory.createGround()).thenReturn(mock(Square.class));
        when(boardFactory.createWall()).thenReturn(mock(Square.class));

        when(levelCreator.createGhost()).thenReturn(mock(Ghost.class));
        when(levelCreator.createPellet()).thenReturn(mock(Pellet.class));


    }

    @Test
    @Order(1)
    @DisplayName("null文件名")
    void nullFile(){
        assertThrows(NullPointerException.class,() ->{
            mapParser.parseMap((String)null);
        });
    }
    @Test
    @Order(2)
    @DisplayName("读取不存在的文件")
    void nonExistFile(){
        String file = "/error.txt";
        String message = "Could not get resource for: " + file;
        assertThrows(PacmanConfigurationException.class,() ->{
            mapParser.parseMap(file);
        },message);
    }
    @Test
    @Order(3)
    @DisplayName("读取存在的文件")
    void existFile() throws IOException {
        String file = "/simplemap.txt";
        assertEquals(null,mapParser.parseMap(file));
    }
    @Test
    @Order(4)
    @DisplayName("读取无法识别的charmap")
    void unrecongnizedCharMap() throws IOException {
        String file  = "/unrecognizedcharmap.txt";
        assertThrows(PacmanConfigurationException.class,() ->{
            mapParser.parseMap(file);
        });
    }
    @Test
    @Order(5)
    @DisplayName("读取空文件")
    void empty() throws IOException {
        String file = "/empty.txt";
        assertThrows(PacmanConfigurationException.class,() ->{
            mapParser.parseMap(file);
        },"Input text must consist of at least 1 row");
    }



}
