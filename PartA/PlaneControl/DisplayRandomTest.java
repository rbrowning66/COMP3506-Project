package PlaneControl;

import org.junit.jupiter.api.Test;

public class DisplayRandomTest {
    @Test
    void fivePlanesAllDifferentUnsorted() {
        String[] csvLines = {"XYJ3442,8:44", "SJK2217,18:46", "ADZ8264,2:03", "FGI4927,15:35",
                "RPT2964,18:12"};
        DisplayRandom display = new DisplayRandom(csvLines);
        display.sort();

    }

    @Test
    void fivePlanesDifferentPartiallySorted() {
        String[] csvLines = {"XYJ3442,8:44", "SJK2217,18:46", "ADZ8264,2:03", "FGI4927,15:35",
                "RPT2964,18:12"};
        Displ
    }
}
