var bikeLinesLayer, linesLayer, bikeLinesLayerNoElevation, poiLayer;
var map;
var mapPoint;
var markerTypPlats, markerTypPaddel, markerTypNoje, markerTypCafe, markerTypVatten, markerTypCamping, markerTypBadplats, markerTypNaturomrade, markerTypNaturreservat;
var naturomradeLayer, platsLayer, paddelLayer, nojeLayer, vattenLayer, campingLayer, badplatsLayer, naturreservatLayer;

require(["esri/map", "esri/geometry/Polyline", "esri/layers/GraphicsLayer", "esri/symbols/SimpleFillSymbol",
"esri/graphic", "esri/InfoTemplate", "esri/symbols/SimpleMarkerSymbol", "esri/symbols/PictureMarkerSymbol", "esri/symbols/SimpleLineSymbol", "esri/geometry/Point", "esri/Color",
 "esri/layers/FeatureLayer","esri/dijit/PopupTemplate","esri/dijit/Legend","dojo/_base/Color", "dojo/on","dojo/domReady!"],
function(Map, GraphicsLayer, SimpleMarkerSymbol, PictureMarkerSymbol, Graphic, InfoTemplate, SimpleLineSymbol, Point, Polyline, SimpleFillSymbol, FeatureLayer, PopupTemplate, Legend, Color, On){
    map = new Map("mapDiv", {
    basemap: "streets",
    center: [16.895881400000007, 60.288536],
    zoom: 10,
  });
var graphics = new esri.layers.GraphicsLayer();
map.addLayer(graphics);

naturomradeLayer = new esri.layers.GraphicsLayer();
map.addLayer(naturomradeLayer);

naturreservatLayer = new esri.layers.GraphicsLayer();
map.addLayer(naturreservatLayer);

platsLayer = new esri.layers.GraphicsLayer();
map.addLayer(platsLayer);

nojeLayer = new esri.layers.GraphicsLayer();
map.addLayer(nojeLayer);

vattenLayer = new esri.layers.GraphicsLayer();
map.addLayer(vattenLayer);

campingLayer = new esri.layers.GraphicsLayer();
map.addLayer(campingLayer);

badplatsLayer = new esri.layers.GraphicsLayer();
map.addLayer(badplatsLayer);

paddelLayer = new esri.layers.GraphicsLayer();
map.addLayer(paddelLayer);

nojeLayer = new esri.layers.GraphicsLayer();
map.addLayer(nojeLayer);

cafeLayer = new esri.layers.GraphicsLayer();
map.addLayer(cafeLayer);

linesLayer = new esri.layers.GraphicsLayer();
map.addLayer(linesLayer);

bikeLinesLayerNoElevation = new esri.layers.GraphicsLayer();
map.addLayer(bikeLinesLayerNoElevation);

getJsonDataWalkingElevation();
getPOI();
bikingNoElevationJson();
temporaryPOI();
hideOrShow()
});

function getPOI(){
  var poiOne = {url: "GysingeJSON.json", handleAs:"json", content: {}, load: showPoints};
  dojo.xhrGet(poiOne);

  var poiTwo = {url: "hedesundafjardenJSON.json", handleAs:"json", content: {}, load: showPoints};
  dojo.xhrGet(poiTwo);

  var poiThree = {url: "CanoeFallernebofjarrgardenJSON.json", handleAs:"json", content: {}, load: showPoints};
  dojo.xhrGet(poiThree);
}

function showPoints(poiOne){
  markerTypNaturomrade = new esri.symbol.PictureMarkerSymbol("https://png.icons8.com/metro/1600/organic-food.png", 16,16);
  markerTypNoje = new esri.symbol.PictureMarkerSymbol("https://cdn.onlinewebfonts.com/svg/img_558961.png", 16,16);
  markerTypPlats = new esri.symbol.PictureMarkerSymbol("https://png.icons8.com/ios/1600/house-with-a-garden-filled.png", 16,16);
  markerTypCamping = new esri.symbol.PictureMarkerSymbol("https://www.shareicon.net/data/128x128/2016/08/06/807743_tools_512x512.png", 16,16);
  markerTypNaturreservat = new esri.symbol.PictureMarkerSymbol("https://cdn.onlinewebfonts.com/svg/img_389579.png", 16,16);
  markerTypCafe = new esri.symbol.PictureMarkerSymbol("https://cdn.onlinewebfonts.com/svg/img_180524.png", 16,16);
  markerTypPaddel = new esri.symbol.PictureMarkerSymbol("https://png.icons8.com/metro/1600/sup.png", 16,16);
  markerTypVatten = new esri.symbol.PictureMarkerSymbol("https://d30y9cdsu7xlg0.cloudfront.net/png/16107-200.png", 16,16);
  markerTypBadplats = new esri.symbol.PictureMarkerSymbol("https://www.shareicon.net/data/128x128/2015/09/22/644534_sports_512x512.png", 16,16);

dojo.forEach(poiOne.stations, function(station){
  var lng = station.x;
  var lat = station.y;
  var lngString = lng.toString();
  var latString = lat.toString();

  lng = parseFloat(lngString.substring(0,2)) + (parseFloat(lngString.substring(3,5) + "." + lngString.substring(5))/60);
  lat = parseFloat(latString.substring(0,2)) + (parseFloat(latString.substring(3,5) + "." + latString.substring(5))/60);

  var name = station.name;
  var typ = station.typ;
  var info = station.info;
  var picture = station.picture;
  var point = new esri.geometry.Point(lng,lat);

  //-----------------------------if sats för olika markörer---------------------
    if(typ == "naturomrade"){
    var grafik = new esri.Graphic(point,markerTypNaturomrade);
    grafik.setInfoTemplate(new esri.InfoTemplate("POI","Namn: "+ name +"<br/>"+ "<br/>"
    +"Info: "+ info + "<br/>" +"<br/>"+"Typ: "+ typ + "<br/>" + "<img src=infoPictures/"+picture+">"));
    naturomradeLayer.add(grafik);
  }else if (typ == "noje") {
    var grafik = new esri.Graphic(point,markerTypNoje);
    grafik.setInfoTemplate(new esri.InfoTemplate("POI","Namn: "+ name +"<br/>"+ "<br/>"
    +"Info: "+ info + "<br/>" +"<br/>"+"Typ: "+ typ + "<br/>" + "<img src=infoPictures/"+picture+">"));
    nojeLayer.add(grafik);
  }else if (typ == "plats") {
    var grafik = new esri.Graphic(point,markerTypPlats);
    grafik.setInfoTemplate(new esri.InfoTemplate("POI","Namn: "+ name +"<br/>"+ "<br/>"
    +"Info: "+ info + "<br/>" +"<br/>"+"Typ: "+ typ + "<br/>" + "<img src=infoPictures/"+picture+">"));
    platsLayer.add(grafik);
  }else if (typ == "camping") {
    var grafik = new esri.Graphic(point,markerTypCamping);
    grafik.setInfoTemplate(new esri.InfoTemplate("POI","Namn: "+ name +"<br/>"+ "<br/>"
    +"Info: "+ info + "<br/>" +"<br/>"+"Typ: "+ typ + "<br/>" + "<img src=infoPictures/"+picture+">"));
    campingLayer.add(grafik);
  }else if (typ == "naturreservat") {
    var grafik = new esri.Graphic(point,markerTypNaturreservat);
    grafik.setInfoTemplate(new esri.InfoTemplate("POI","Namn: "+ name +"<br/>"+ "<br/>"
    +"Info: "+ info + "<br/>" +"<br/>"+"Typ: "+ typ + "<br/>" + "<img src=infoPictures/"+picture+">"));
    naturreservatLayer.add(grafik);
  }else if (typ == "cafe") {
    var grafik = new esri.Graphic(point,markerTypCafe);
    grafik.setInfoTemplate(new esri.InfoTemplate("POI","Namn: "+ name +"<br/>"+ "<br/>"
    +"Info: "+ info + "<br/>" +"<br/>"+"Typ: "+ typ + "<br/>" + "<img src=infoPictures/"+picture+">"));
    cafeLayer.add(grafik);
  }else if (typ == "paddel") {
    var grafik = new esri.Graphic(point,markerTypPaddel);
    grafik.setInfoTemplate(new esri.InfoTemplate("POI","Namn: "+ name +"<br/>"+ "<br/>"
    +"Info: "+ info + "<br/>" +"<br/>"+"Typ: "+ typ + "<br/>" + "<img src=infoPictures/"+picture+">"));
    paddelLayer.add(grafik);
  }else if (typ == "vatten") {
    var grafik = new esri.Graphic(point,markerTypVatten);
    grafik.setInfoTemplate(new esri.InfoTemplate("POI","Namn: "+ name +"<br/>"+ "<br/>"
    +"Info: "+ info + "<br/>" +"<br/>"+"Typ: "+ typ + "<br/>" + "<img src=infoPictures/"+picture+">"));
    vattenLayer.add(grafik);
  }else if (typ == "badplats") {
    var grafik = new esri.Graphic(point,markerTypBadplats);
    grafik.setInfoTemplate(new esri.InfoTemplate("POI","Namn: "+ name +"<br/>"+ "<br/>"
    +"Info: "+ info + "<br/>" +"<br/>"+"Typ: "+ typ + "<br/>" + "<img src=infoPictures/"+picture+">"));
    badplatsLayer.add(grafik);
  }
});
}

function bikingNoElevationJson(){
  for(var i=1;i<=23;i++){
  var bikingNoElevation = {url: "Etapp_" + i + ".json", handleAs: "json", content: {}, load: bikeNoElevation};
	dojo.xhrGet(bikingNoElevation); //lila färg
  }
}

function bikeNoElevation(bikingNoElevation){
  var points = [];
  var testPath = [
[  // first path
[-97.06138,32.837,5],
[-97.06133,32.836,6],
[-97.06124,32.834,7]
], [  // second path
[-97.06326,32.759],
[-97.06298,32.755]
]
];
var line = new esri.geometry.Polyline({
  hasZ: false,
  hasM: false,
  paths: testPath,
  spatialReference: { wkid: 4326 }});

dojo.forEach(bikingNoElevation.posts, function(post){
  var long = post.longitude;
  var lat = post.latitude;
  points.push(new esri.geometry.Point(long,lat));
});
line.addPath(points);

var symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new esri.Color([153,0,204]),2);
var grafik = new esri.Graphic(line, symbol);
bikeLinesLayerNoElevation.add(grafik);
}

function getJsonDataWalkingElevation(){
	var bikingElevation = {url: "Biking_elevation161008.json", handleAs: "json", content: {}, load: bikeElevation};
	dojo.xhrGet(bikingElevation); //röd färg

  var walkingElevationOne = {url: "Walk_elevation_123547.json", handleAs: "json", content: {}, load: walkingElevation};
  dojo.xhrGet(walkingElevationOne); //grön färg

  var walkingElevationTwo = {url: "Walk_elevation_151851.json", handleAs: "json", content: {}, load: walkingElevation};
  dojo.xhrGet(walkingElevationTwo);
}

function walkingElevation(walkingElevationOne){
    var points = [];
    var testPath = [
[  // first path
 [-97.06138,32.837,5],
 [-97.06133,32.836,6],
 [-97.06124,32.834,7]
], [  // second path
 [-97.06326,32.759],
 [-97.06298,32.755]
]
];
  var line = new esri.geometry.Polyline({
    hasZ: false,
    hasM: false,
    paths: testPath,
    spatialReference: { wkid: 4326 }});

  dojo.forEach(walkingElevationOne.posts, function(post){
    var long = post.longitude;
    var lat = post.latitude;
    points.push(new esri.geometry.Point(long,lat));
});
line.addPath(points);

var symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new esri.Color([0,153,51]),3);
var grafik = new esri.Graphic(line, symbol);
linesLayer.add(grafik);
}

function bikeElevation(bikingElevation){
  bikeLinesLayer = new esri.layers.GraphicsLayer();
  map.addLayer(bikeLinesLayer);
  var points = [];
  var testPath = [
[  // first path
[-97.06138,32.837,5],
[-97.06133,32.836,6],
[-97.06124,32.834,7]
], [  // second path
[-97.06326,32.759],
[-97.06298,32.755]
]
];
var line = new esri.geometry.Polyline({
  hasZ: false,
  hasM: false,
  paths: testPath,
  spatialReference: { wkid: 4326 }});

dojo.forEach(bikingElevation.posts, function(post){
  var long = post.longitude;
  var lat = post.latitude;
  points.push(new esri.geometry.Point(long,lat));
});
line.addPath(points);

var symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new esri.Color([255,40,41]),2);
var grafik = new esri.Graphic(line, symbol);
bikeLinesLayer.add(grafik);
}

function temporaryPOI(){
  var graphics = new esri.layers.GraphicsLayer();
  map.addLayer(graphics);

  map.on("dbl-click", function(evt){
  		mapPoint = esri.geometry.webMercatorToGeographic(evt.mapPoint);
      var name = prompt("Skriv in namn på punkten", "");
      if (name == "") {
        alert("Du har inte fyllt i ett namn, försök igen.");
        system.exit();
      }
      var info = prompt("Skriv lite info om punkten", "");
      if (info == "") {
        alert("Du har inte fyllt i någon information, försök igen.");
        system.exit();
      }
      var typ = prompt("Skriv vilken typ punkten är, dina alternativ är: naturomrade, noje, plats, camping, naturreservat, cafe, paddel, vatten, badplats","");


      if(typ == "naturomrade"){
      var grafik = new esri.Graphic(new esri.geometry.Point(mapPoint.x,mapPoint.y),markerTypNaturomrade);
    }else if (typ == "noje") {
      var grafik = new esri.Graphic(new esri.geometry.Point(mapPoint.x,mapPoint.y),markerTypNoje);
    }else if (typ == "plats") {
      var grafik = new esri.Graphic(new esri.geometry.Point(mapPoint.x,mapPoint.y),markerTypPlats);
    }else if (typ == "camping") {
      var grafik = new esri.Graphic(new esri.geometry.Point(mapPoint.x,mapPoint.y),markerTypCamping);
    }else if (typ == "naturreservat") {
      var grafik = new esri.Graphic(new esri.geometry.Point(mapPoint.x,mapPoint.y),markerTypNaturreservat);
    }else if (typ == "cafe") {
      var grafik = new esri.Graphic(new esri.geometry.Point(mapPoint.x,mapPoint.y),markerTypCafe);
    }else if (typ == "paddel") {
      var grafik = new esri.Graphic(new esri.geometry.Point(mapPoint.x,mapPoint.y),markerTypPaddel);
    }else if (typ == "vatten") {
      var grafik = new esri.Graphic(new esri.geometry.Point(mapPoint.x,mapPoint.y),markerTypVatten);
    }else if (typ == "badplats") {
      var grafik = new esri.Graphic(new esri.geometry.Point(mapPoint.x,mapPoint.y),markerTypBadplats);
    }else {
      alert("Du skrev inte in rätt typ");
      system.exit();
    }

    var bild = prompt("lägg in en url för en bild (valfritt)", "");
    grafik.setInfoTemplate(new esri.InfoTemplate("POI","Namn: "+ name +"<br/>"+ "<br/>"
    +"Info: "+ info + "<br/>" +"<br/>"+"Typ: "+ typ + "<br/>" + "<img src="+bild+">"));
      graphics.add(grafik);
      });
}
function hideOrShow(){
  var promenadLeder = document.getElementById("promenadLeder");
  var cykelLeder = document.getElementById("cykelLeder");
  var cykelLederIngenBacke = document.getElementById("cykelLederIngenBacke");

  var plats = document.getElementById("plats");
  var badplats = document.getElementById("badplats");
  var naturomrade = document.getElementById("naturomrade");
  var naturreservat = document.getElementById("naturreservat");
  var cafe = document.getElementById("cafe");
  var camping = document.getElementById("camping");
  var noje = document.getElementById("noje");
  var paddel = document.getElementById("paddel");
  var vatten = document.getElementById("vatten");

  promenadLeder.addEventListener("change", function(event){
    var newValue = event.target.value;

    if(newValue=="dolj"){
      linesLayer.hide();
    }else if (newValue=="visa") {
      linesLayer.show();
    }
  });

  cykelLeder.addEventListener("change", function(event){
    var newValue = event.target.value;

    if(newValue=="dolj"){
      bikeLinesLayer.hide();
    }else if (newValue=="visa") {
      bikeLinesLayer.show();
    }
  });

  cykelLederIngenBacke.addEventListener("change", function(event){
    var newValue = event.target.value;

    if(newValue=="dolj"){
      bikeLinesLayerNoElevation.hide();
    }else if (newValue=="visa") {
      bikeLinesLayerNoElevation.show();
    }
  });

  plats.addEventListener("change", function(event){
    var newValue = event.target.value;

    if(newValue=="dolj"){
      platsLayer.hide();
    }else if (newValue=="visa") {
      platsLayer.show();
    }
  });

  badplats.addEventListener("change", function(event){
    var newValue = event.target.value;

    if(newValue=="dolj"){
      badplatsLayer.hide();
    }else if (newValue=="visa") {
      badplatsLayer.show();
    }
  });

  naturomrade.addEventListener("change", function(event){
    var newValue = event.target.value;

    if(newValue=="dolj"){
      naturomradeLayer.hide();
    }else if (newValue=="visa") {
      naturomradeLayer.show();
    }
  });

  naturreservat.addEventListener("change", function(event){
    var newValue = event.target.value;

    if(newValue=="dolj"){
      naturreservatLayer.hide();
    }else if (newValue=="visa") {
      naturreservatLayer.show();
    }
  });

  cafe.addEventListener("change", function(event){
    var newValue = event.target.value;

    if(newValue=="dolj"){
      cafeLayer.hide();
    }else if (newValue=="visa") {
      cafeLayer.show();
    }
  });

  camping.addEventListener("change", function(event){
    var newValue = event.target.value;

    if(newValue=="dolj"){
      campingLayer.hide();
    }else if (newValue=="visa") {
      campingLayer.show();
    }
  });

  noje.addEventListener("change", function(event){
    var newValue = event.target.value;

    if(newValue=="dolj"){
      nojeLayer.hide();
    }else if (newValue=="visa") {
      nojeLayer.show();
    }
  });

  paddel.addEventListener("change", function(event){
    var newValue = event.target.value;

    if(newValue=="dolj"){
      paddelLayer.hide();
    }else if (newValue=="visa") {
      paddelLayer.show();
    }
  });

  vatten.addEventListener("change", function(event){
    var newValue = event.target.value;

    if(newValue=="dolj"){
      vattenLayer.hide();
    }else if (newValue=="visa") {
      vattenLayer.show();
    }
  });
}
