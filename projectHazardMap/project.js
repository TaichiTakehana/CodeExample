
var container = document.getElementById('popup');
var content = document.getElementById('popup-content');
var closer = document.getElementById('popup-closer');
var distance = document.getElementById('distance');
var buffer = document.getElementById('buffer');

var overlay = new ol.Overlay(
    ({
	element: container,
	autoPan: true,
	autoPanAnimation: {
	duration: 250
	}
    }));

closer.onclick = function() {
    overlay.setPosition(undefined);
    closer.blur();
    return false;
};

var placeStyle = new ol.style.Style({
    image: new ol.style.Circle({
	fill: new ol.style.Fill({
	color: 'rgba(0,255,255,0.9)'
	}),
	stroke: new ol.style.Stroke({
	color: 'black',
	width: 1
	}),
	radius: 5
    })
});

var placeStyleSelect = new ol.style.Style({
    image: new ol.style.Circle({
    fill: new ol.style.Fill({
    color: 'rgba(255,0,0,1)'
    }),
    stroke: new ol.style.Stroke({
	color: 'black',
	width: 1
    }),
    radius: 6
    })
});

var iconStyle = new ol.style.Style({
	image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
	anchor: [0.5, 46],
	anchorXUnits: 'fraction',
	anchorYUnits: 'pixels',
	opacity: 1,
	src: 'data/pngbarn.png',
	size:[20,20]
	}))
});

var nuclearStyleSelect = new ol.style.Style({
    image: new ol.style.Circle({
    fill: new ol.style.Fill({
    color: 'rgba(255,0,0,1)'
    }),
    stroke: new ol.style.Stroke({
	color: 'black',
	width: 1
    }),
    radius: 9
    })
});

var stateStyle = [new ol.style.Style({
    fill: new ol.style.Fill({
	color: 'rgba(0,0,0,0)'
	}),
    stroke: new ol.style.Stroke({
	color: 'black', width: 1,
	opacity: 0.2,
	})
	})
];

var stateStyleSelect = [new ol.style.Style({
    fill: new ol.style.Fill({
	color: 'rgba(0,0,0,0)'
	}),
    stroke: new ol.style.Stroke({
	color: 'red', width: 5
	})
	})
];

var hazardStyle = [new ol.style.Style({
    fill: new ol.style.Fill({
	color: 'rgba(0,0,0,0)'
	}),
    stroke: new ol.style.Stroke({
	color: 'black', width: 1
	})
	})
];

var hazardStyleSelect = [new ol.style.Style({
    fill: new ol.style.Fill({
	color: 'rgba(0,0,0,0)'
	}),
    stroke: new ol.style.Stroke({
	color: 'red', width: 5
	})
	})
];

var placeSource = new ol.source.Vector();
var placeVector = new ol.layer.Vector({
    source: placeSource
});

var nuclearSource = new ol.source.Vector();
var nuclearVector = new ol.layer.Vector({
    source: nuclearSource
});
nuclearVector.set('name', 'nucler');

var stateSource = new ol.source.Vector();
var stateVector = new ol.layer.Vector({
    source: stateSource
});

var hazardSource = new ol.source.Vector();
var hazardVector = new ol.layer.Vector({
    source: hazardSource
});

var placeRequest = new ol.format.WFS().writeGetFeature({
    srsName: 'EPSG:3857',
    featureNS: 'usa',
    featurePrefix: 'usa',
    featureTypes: ['places'],
    outputFormat: 'application/json',
	filter: ol.format.filter.or(
          ol.format.filter.greaterThan('POP_MAX', '90000'),
          ol.format.filter.greaterThan('POP_MIN', '90000')
        )
});

var nuclearRequest = new ol.format.WFS().writeGetFeature({
    srsName: 'EPSG:3857',
    featureNS: 'usa',
    featurePrefix: 'usa',
    featureTypes: ['nuclear'],
    outputFormat: 'application/json'
});

var stateRequest = new ol.format.WFS().writeGetFeature({
    srsName: 'EPSG:3857',
    featureNS: 'usa',
    featurePrefix: 'usa',
    featureTypes: ['states'],
    outputFormat: 'application/json'
});

var hazardRequest = new ol.format.WFS().writeGetFeature({
    srsName: 'EPSG:3857',
    featureNS: 'usa',
    featurePrefix: 'usa',
    featureTypes: ['hazard'],
    outputFormat: 'application/json'
});

placeVector.setStyle(placeStyle);
nuclearVector.setStyle(iconStyle);
stateVector.setStyle(stateStyle);
hazardVector.setStyle(hazardStyle);

var osm = new ol.layer.Tile({
    source: new ol.source.Stamen({
	layer: 'toner'
	})
});
var osmLabel = new ol.layer.Tile({
	source: new ol.source.Stamen({
	layer: 'terrain-labels'
	})
});
var hazard = new ol.layer.Image({
		//extent: boundsMercator,
        source: new ol.source.ImageWMS({
          url: 'http://localhost:8086/geoserver/usa/wms',
          params: {LAYERS: 'usa:hazard'},
		  serverType: 'geoserver'
        }),
		    name: 'hazard',
			opacity: 0.5,
			stroke: new ol.style.Stroke({
			color: 'black',
			width: 1,
			opacity: 0.2
			})
      });

var map = new ol.Map({
    layers: [osm, hazard, hazardVector, stateVector, nuclearVector, placeVector, 
	  ],
    target: document.getElementById('map'),
    view: new ol.View({
        center: ol.proj.transform([-119.417931, 36.778259],'EPSG:4326','EPSG:3857'),
        maxZoom: 15,
        zoom: 9
    }),
    interactions: ol.interaction.defaults({
        doubleClickZoom: false
    })
});

fetch('http://localhost:8086/geoserver/wfs', {
	method: 'POST',
	body: new XMLSerializer().serializeToString(placeRequest)
}).then(function(response) {
	return response.json();
}).then(function(json) {
	var features = new ol.format.GeoJSON().readFeatures(json);
	placeSource.addFeatures(features);
	extent_swe=placeSource.getExtent();
	extent_merk=ol.proj.transformExtent(extent_swe,'EPSG:4326','EPSG:900913' );
	map.getView().fit(placeSource.getExtent(), (map.getSize()));
});

fetch('http://localhost:8086/geoserver/wfs', {
	method: 'POST',
	body: new XMLSerializer().serializeToString(nuclearRequest)
}).then(function(response2) {
	return response2.json();
}).then(function(json2) {
	var features2 = new ol.format.GeoJSON().readFeatures(json2);
	nuclearSource.addFeatures(features2);
	extent_swe=nuclearSource.getExtent();
	extent_merk=ol.proj.transformExtent(extent_swe,'EPSG:4326','EPSG:900913' );
	map.getView().fit(nuclearSource.getExtent(), (map.getSize()));
});

fetch('http://localhost:8086/geoserver/wfs', {
	method: 'POST',
	body: new XMLSerializer().serializeToString(stateRequest)
}).then(function(response3) {
	return response3.json();
}).then(function(json3) {
	var features3 = new ol.format.GeoJSON().readFeatures(json3);
	stateSource.addFeatures(features3);
	extent_swe=stateSource.getExtent();
	extent_merk=ol.proj.transformExtent(extent_swe,'EPSG:4326','EPSG:900913' );
	map.getView().fit(stateSource.getExtent(), (map.getSize()));
});

fetch('http://localhost:8086/geoserver/wfs', {
	method: 'POST',
	body: new XMLSerializer().serializeToString(hazardRequest)
}).then(function(response4) {
	return response4.json();
}).then(function(json4) {
	var features4 = new ol.format.GeoJSON().readFeatures(json4);
	hazardSource.addFeatures(features4);
	extent_swe=hazardSource.getExtent();
	extent_merk=ol.proj.transformExtent(extent_swe,'EPSG:4326','EPSG:900913' );
	map.getView().fit(hazardSource.getExtent(), (map.getSize()));
});


var source = new ol.source.Vector();
var circleLayer = new ol.layer.Vector({
    source: source
});
var bufferSource = new ol.source.Vector();
var bufferLayer = new ol.layer.Vector({
	source: bufferSource,
	style: placeStyleSelect
});
map.addLayer(circleLayer);

map.getViewport().addEventListener("dblclick", function(e) {
    var coordinate = map.getEventCoordinate(e);
    var dist = parseInt(distance.value);
    var circle = new ol.geom.Circle(coordinate, dist);
    var circPoly = ol.geom.Polygon.fromCircle(circle,500);
    var feature = new ol.Feature(circPoly);

    source.clear();
    source.addFeature(feature);
/////////////////////////////////////////////////
    if(buffer.value == "places"){
		bufferSource.clear();
	var featureRequest = new ol.format.WFS().writeGetFeature({
		srsName: 'EPSG:900913',
		featureNS: 'usa',
		featurePrefix: 'usa',
		featureTypes: ['places'],
		outputFormat: 'application/json',
		filter: new ol.format.filter.intersects('the_geom', feature.getGeometry(), 'EPSG:900913')
	});
	fetch('http://localhost:8086/geoserver/wfs', {
		method: 'POST',
		body: new XMLSerializer().serializeToString(featureRequest)
	}).then(function(response) {
		return response.json();
	}).then(function(json) {
		var features = new ol.format.GeoJSON().readFeatures(json);
		bufferSource.addFeatures(features);
	});
	map.addLayer(bufferLayer);
	
    } else if(buffer.value == "nuclear"){
		bufferSource.clear();
	var featureRequest2 = new ol.format.WFS().writeGetFeature({
		srsName: 'EPSG:900913',
		featureNS: 'usa',
		featurePrefix: 'usa',
		featureTypes: ['nuclear'],
		outputFormat: 'application/json',
		filter: new ol.format.filter.intersects('the_geom', feature.getGeometry(), 'EPSG:900913')
	});
	fetch('http://localhost:8086/geoserver/wfs', {
		method: 'POST',
		body: new XMLSerializer().serializeToString(featureRequest2)
	}).then(function(response2) {
		return response2.json();
	}).then(function(json2) {
		var features2 = new ol.format.GeoJSON().readFeatures(json2);
		bufferSource.addFeatures(features2);
	});
	map.addLayer(bufferLayer);
    }
	
});

map.addOverlay(overlay);

var checkboxPlaces = document.querySelector('#places');
checkboxPlaces.addEventListener('change', function() {
	var checked = this.checked;
	if (checked !== placeVector.getVisible()) {
		placeVector.setVisible(checked);
	}
});
var checkboxHazardImage = document.querySelector('#hazard');
checkboxHazardImage.addEventListener('change', function() {
	var checked = this.checked;
	if (checked !== hazard.getVisible()) {
		hazard.setVisible(checked);
	}
});
var checkboxHazard = document.querySelector('#hazard');
checkboxHazard.addEventListener('change', function() {
	var checked = this.checked;
	if (checked !== hazardVector.getVisible()) {
		hazardVector.setVisible(checked);
	}
});
var checkboxStates = document.querySelector('#states');
checkboxStates.addEventListener('change', function() {
	var checked = this.checked;
	if (checked !== stateVector.getVisible()) {
		stateVector.setVisible(checked);
	}
});
var checkboxNuclear = document.querySelector('#nuclear');
checkboxNuclear.addEventListener('change', function() {
	var checked = this.checked;
	if (checked !== nuclearVector.getVisible()) {
		nuclearVector.setVisible(checked);
	}
});

placeVector.on('change:visible', function() {
	var visible = this.getVisible();
	if (visible !== checkboxPlaces.checked) {
		checkboxPlaces.checked = visible;
	}
});

hazard.on('change:visible', function() {
	var visible = this.getVisible();
	if (visible !== checkboxHazardImage.checked) {
		checkboxHazardImage.checked = visible;
	}
});

hazardVector.on('change:visible', function() {
	var visible = this.getVisible();
	if (visible !== checkboxHazard.checked) {
		checkboxHazard.checked = visible;
	}
});

stateVector.on('change:visible', function() {
	var visible = this.getVisible();
	if(visible !== checkboxStates.checked){
		checkboxStates.checked = visible;
	}
});

nuclearVector.on('change:visible', function() {
	var visible = this.getVisible();
	if(visible !== checkboxNuclear.checked){
		checkboxNuclear.checked = visible;
	}
});
placeVector.set('selectable', true);
nuclearVector.set('selectable', false);
hazardVector.set('selectable', true);
stateVector.set('selectable', true);

var selectedFeature = null;
map.on('singleclick', function(evt) {
	
    var coordinate = evt.coordinate;
    var stringifyFunc = ol.coordinate.createStringXY(2);
    var out = stringifyFunc(coordinate);
	var feature = map.forEachFeatureAtPixel(evt.pixel,
    function(feature, layer){
		map.getLayers().forEach(function(el) {
		if (el.get('name') === 'nuclear') {
		console.log(el);
		}
		})
		selectedFeature = feature;
		if(feature.getGeometry().getType() == "Point"){
			feature.setStyle(placeStyleSelect);
			content.innerHTML = '<p>Information:</p><code>' +
			'<p>Coordinates: ' + out + '</p>' + 'Max population: ' + feature.get('POP_MAX')
			+ '<p>NAME: ' + feature.get('NAME') +'</p>'+'</code>';
		} else if(feature.getGeometry().getType() == "MultiPolygon"){
			feature.setStyle(stateStyleSelect);
			content.innerHTML = '<p>Information:</p><code>' +
			'<p>Coordinates: ' + out + '</p>' + 'Seismic hazard: ' + feature.get('SEIHAZM020')
			+ '<p>NAME: ' + feature.get('NAME')+'</p>'
			+ '</code>';
		}
		overlay.setPosition(coordinate);
    });
});