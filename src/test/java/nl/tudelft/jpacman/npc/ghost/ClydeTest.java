package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClydeTest {

    private static MapParser mapParser;

    @BeforeAll
    public static void setup(){
        //上课老师讲解过
        /*实现这个ClydeTest测试类时，需要构建游戏运行的所有所需对象。
        这些对象包括PacManSprites（用于角色显示）、
        PlayerFactory（用于构造Player）、
        GhostFactory（提供给LevelFactory），
        BoardFactory（游戏场景）、
        GhostMapParser（地图解析*/
        PacManSprites sprites = new PacManSprites();
        LevelFactory levelFactory = new LevelFactory(
            sprites,
            new GhostFactory(sprites),
            mock(PointCalculator.class));
        BoardFactory boardFactory = new BoardFactory(sprites);
        GhostFactory ghostFactory = new GhostFactory(sprites);

        mapParser = new GhostMapParser(levelFactory,boardFactory,ghostFactory);
    }


    /*地图上Clyde和Player距离<8的4种情况
      设置地图使Clyde朝东西南北四个方向分别跑
      Clyde很害羞会尽量远离
      */
    @Test
    @DisplayName("距离<8+预测WEST")
    void Less8West(){
        //上课老师讲解例子
        //创建地图
        List<String> text = Lists.newArrayList(
            "##############",
            "#.#....C.....P",
            "##############");
        Level level = mapParser.parseMap(text);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class,level.getBoard());

        //创建Player:
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        player.setDirection(Direction.WEST);
        level.registerPlayer(player);

        //act:
        Optional<Direction> opt = clyde.nextAiMove();

        //assert:
        assertThat(opt.get()).isEqualTo(Direction.valueOf("WEST"));
    }

    @Test
    @DisplayName("距离<8+预测NORTH")
    void Less8North(){
        List<String> text = Lists.newArrayList(
            ".....####.....",
            "...........#C#",
            "############P#");
        Level level = mapParser.parseMap(text);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class,level.getBoard());
        //创建Player
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        player.setDirection(Direction.WEST);
        level.registerPlayer(player);
        //act
        Optional<Direction> opt = clyde.nextAiMove();
        //assert
        assertThat(opt.get()).isEqualTo(Direction.valueOf("NORTH"));
    }
    @Test
    @DisplayName("距离小于8+预测SOUTH")
    void Less8South(){
        //构造地图
        List<String> text = Lists.newArrayList(
            "....#P........",
            "....#C........",
            "##..#...######");
        Level level = mapParser.parseMap(text);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class,level.getBoard());
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        player.setDirection(Direction.EAST);
        level.registerPlayer(player);
        Optional<Direction> opt = clyde.nextAiMove();
        assertThat(opt.get()).isEqualTo(Direction.valueOf("SOUTH"));
    }

    @Test
    @DisplayName("距离小于8+预测EAST")
    void Less8East(){
        List<String> text = Lists.newArrayList(
            "##############",
            "#.#.#..P.C...#",
            "##############");
        Level level = mapParser.parseMap(text);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class,level.getBoard());

        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        player.setDirection(Direction.WEST);
        level.registerPlayer(player);

        Optional<Direction> opt = clyde.nextAiMove();

        assertThat(opt.get()).isEqualTo(Direction.valueOf("EAST"));
    }



    /*地图上Clyde和Player距离>8的4种情况
      测试东西南北跑
      Clyde会尽量靠近*/
    @Test
    @DisplayName("距离>8+预测WEST")
    void more8West(){
        //构造地图
        List<String> text = Lists.newArrayList(
            "####################",
            "..P..............C..",
            "####################");
        Level level = mapParser.parseMap(text);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class,level.getBoard());
        //创建Player
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        player.setDirection(Direction.WEST);
        level.registerPlayer(player);
        //act
        Optional<Direction> opt = clyde.nextAiMove();
        //assert
        assertThat(opt.get()).isEqualTo(Direction.valueOf("WEST"));
    }
    @Test
    @DisplayName("距离>8+预测EAST")
    void more8East(){
        //构造地图
        List<String> text = Lists.newArrayList(
            "####################",
            "..C..............P..",
            "####################");
        Level level = mapParser.parseMap(text);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class,level.getBoard());
        //创建Player:
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        player.setDirection(Direction.EAST);
        level.registerPlayer(player);
        //act:
        Optional<Direction> opt = clyde.nextAiMove();
        //assert:
        assertThat(opt.get()).isEqualTo(Direction.valueOf("EAST"));
    }
    @Test
    @DisplayName("距离>8+预测SOUTH")
    void more8South(){
        //构造地图
        List<String> text = Lists.newArrayList(
            "####################",
            "P...................",
            "................C..."
        );
        Level level = mapParser.parseMap(text);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class,level.getBoard());
        //创建Player
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        player.setDirection(Direction.NORTH);
        level.registerPlayer(player);
        //act
        Optional<Direction> opt = clyde.nextAiMove();
        //assert
        assertThat(opt.get()).isEqualTo(Direction.valueOf("SOUTH"));
    }
    @Test
    @DisplayName("距离>8+预测NORTH")
    void more8North(){
        //构造地图
        List<String> text = Lists.newArrayList(
            "..###..###..###..##",
            "C..................",
            "................P.."
        );
        Level level = mapParser.parseMap(text);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class,level.getBoard());
        //创建Player
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        player.setDirection(Direction.NORTH);
        level.registerPlayer(player);
        //act
        Optional<Direction> opt = clyde.nextAiMove();
        //assert
        assertThat(opt.get()).isEqualTo(Direction.valueOf("NORTH"));
    }

}
