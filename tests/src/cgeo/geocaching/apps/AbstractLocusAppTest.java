package cgeo.geocaching.apps;

import cgeo.geocaching.enumerations.WaypointType;

import java.util.ArrayList;
import java.util.HashMap;

import locus.api.objects.geocaching.GeocachingAttribute;
import locus.api.objects.geocaching.GeocachingWaypoint;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AbstractLocusAppTest {

    @Test
    public void testToLocusWaypoint() {

        // should detect new WaypointType
        assertEquals(8, WaypointType.values().length);

        final HashMap<WaypointType, String> testWaypoinList = new HashMap<>();
        testWaypoinList.put(WaypointType.FINAL, GeocachingWaypoint.CACHE_WAYPOINT_TYPE_FINAL);
        testWaypoinList.put(WaypointType.ORIGINAL, GeocachingWaypoint.CACHE_WAYPOINT_TYPE_REFERENCE);

        final ArrayList<WaypointType> testCgeoWpts = new ArrayList<>(testWaypoinList.keySet());
        final ArrayList<String> testLoWapts = new ArrayList<>(testWaypoinList.values());

        for (int i = 0; i < testCgeoWpts.size(); i++) {
            final String loWaypoint = AbstractLocusApp.toLocusWaypoint(testCgeoWpts.get(i));
            assertEquals(loWaypoint, testLoWapts.get(i));
        }
    }

    @Test
    // positive
    public void testToLocusAttributesOk() {

        final HashMap<String, Integer> testAttributes = new HashMap<>();
        testAttributes.put("onehour_no", 7);
        testAttributes.put("dangerousanimals_yes", 118);
        testAttributes.put("picnic_no", 30);
        testAttributes.put("thorn_yes", 139);
        testAttributes.put("uv_no", 48);
        testAttributes.put("abandonedbuilding_yes", 154);
        testAttributes.put("powertrail_yes", 170);
        testAttributes.put("hqsolutionchecker_yes", 172);
        testAttributes.put("hqsolutionchecker_no", 72);

        final ArrayList<String> testAttributesKeys = new ArrayList<>(testAttributes.keySet());
        final ArrayList<GeocachingAttribute> gaTests = AbstractLocusApp.toLocusAttributes(testAttributesKeys);
        final ArrayList<Integer> testAttributesValues = new ArrayList<>(testAttributes.values());

        assertEquals(testAttributes.size(), gaTests.size());

        for (int i = 0; i < gaTests.size(); i++) {
            assertEquals(gaTests.get(i).getId(), testAttributesValues.get(i).longValue());
        }
    }

    @Test
    // negative
    public void testToLocusAttributesKo() {

        final ArrayList<String> testAttributes = new ArrayList<>();
        testAttributes.add("nothing_yes");
        testAttributes.add("nothing_no");
        testAttributes.add("bla");
        testAttributes.add("");
        testAttributes.add(null);

        final ArrayList<GeocachingAttribute> gaTests = AbstractLocusApp.toLocusAttributes(testAttributes);

        assertEquals(0, gaTests.size());
    }
}