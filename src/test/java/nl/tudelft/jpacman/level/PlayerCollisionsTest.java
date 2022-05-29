package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerCollisionsTest {
    PlayerCollisions playerCollisions;
    @BeforeEach
    void setUp(){
        playerCollisions =new PlayerCollisions(new PointCalculator() {

            @Override
            public void consumedAPellet(Player player, Pellet pellet) {

            }

            @Override
            public void pacmanMoved(Player player, Direction direction) {

            }
            @Override
            public void collidedWithAGhost(Player player, Ghost ghost) {

            }


        });

    }

    @Test
    void nothingHappened(){
        Player player = mock(Player.class);
        Ghost ghost = mock(Ghost.class);
        Pellet pellet = mock(Pellet.class);
//        规则5
        playerCollisions.collide(ghost,ghost);
//        规则6
        playerCollisions.collide(ghost,pellet);
//        规则7
        playerCollisions.collide(pellet,ghost);
        doCallRealMethod().when(player).setAlive(anyBoolean());
        doCallRealMethod().when(player).setKiller(any(Unit.class));
        doNothing().when(pellet).leaveSquare();
        verify(player,times(0)).setAlive(false);
        verify(player,times(0)).setKiller(ghost);
        verify(pellet,times(0)).leaveSquare();
    }


    //    规则1
    @Test
    void playerVersusGhost(){
        Player player = mock(Player.class);
        Ghost ghost = mock(Ghost.class);
        playerCollisions.collide(player,ghost);
        doCallRealMethod().when(player).setAlive(anyBoolean());
        doCallRealMethod().when(player).setKiller(any(Unit.class));
        verify(player,times(1)).setAlive(false);
        verify(player,times(1)).setKiller(ghost);

    }



    //    规则2
    @Test
    void playerVersusPellet(){
        Player player = mock(Player.class);
        Pellet pellet = mock(Pellet.class);
        playerCollisions.collide(player,pellet);
        doNothing().when(pellet).leaveSquare();
        verify(pellet,times(1)).leaveSquare();

    }
    //    规则3
    @Test
    void ghostVersusPlayer(){
        Player player = mock(Player.class);
        Ghost ghost = mock(Ghost.class);
        playerCollisions.collide(ghost,player);
        doCallRealMethod().when(player).setAlive(anyBoolean());
        doCallRealMethod().when(player).setKiller(any(Unit.class));
        verify(player,times(1)).setAlive(false);
        verify(player,times(1)).setKiller(ghost);


    }
    //    规则4
    @Test
    void pelletVersusPlayer(){
        Player player = mock(Player.class);
        Pellet pellet = mock(Pellet.class);
        playerCollisions.collide(pellet,player);
        doNothing().when(pellet).leaveSquare();
        verify(pellet,times(1)).leaveSquare();
    }







}
