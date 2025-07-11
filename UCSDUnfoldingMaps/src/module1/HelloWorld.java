package module1;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class HelloWorld extends PApplet {
    private static final long serialVersionUID = 1L;
    public static String mbTilesString = "blankLight-1-3.mbtiles";
    private static final boolean offline = true;
    UnfoldingMap map1;
    UnfoldingMap map2;

    public void setup() {
        this.size(800, 600, "processing.opengl.PGraphics2D");
        this.background(200.0F, 200.0F, 200.0F);
        new Google.GoogleTerrainProvider();
        int zoomLevel = 10;
        AbstractMapProvider provider = new MBTilesMapProvider(mbTilesString);
        zoomLevel = 3;
        this.map1 = new UnfoldingMap(this, 50.0F, 50.0F, 350.0F, 500.0F, provider);
        this.map1.zoomAndPanTo(zoomLevel, new Location(32.9F, -117.2F));
        MapUtils.createDefaultEventDispatcher(this, new UnfoldingMap[]{this.map1});
        this.map2 = new UnfoldingMap(this, 415.0F, 50.0F, 350.0F, 500.0F, provider);
        this.map2.zoomAndPanTo(zoomLevel, new Location(48.526606134691576, 1.3486342514308163));
        MapUtils.createDefaultEventDispatcher(this, new UnfoldingMap[]{this.map2});
    }

    public void draw() {
        this.map1.draw();
        this.map2.draw();
    }
}
