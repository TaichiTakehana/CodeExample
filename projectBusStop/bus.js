var map;
var markers = new Array(4);
var positions = new Array(4);
var stopsLayer;
var bussLayer;
var lineIndex;
var current;
var busInterval;
var linje;
		require
		(["esri/map", "esri/layers/GraphicsLayer", "esri/InfoTemplate", "esri/geometry/Point",
		 "esri/symbols/PictureMarkerSymbol", "esri/graphic", "esri/Color", "dojo/domReady!"],
		
		function(Map, GraphicsLayer, InfoTemplate, Point, PictureMarkerSymbol, Graphic, Color)
		{
			map = new Map ("mapDiv",{
					center: [17.151189,60.676245],
					zoom: 13,
					basemap: "streets"
			});
			
			var graphics = new esri.layers.GraphicsLayer();
			map.addLayer(graphics);
		
		getStopsData();
		getBusData();
		initButtons();
		makeBusMarkers();
		});
		
		function getStopsData()
		{
			var stopsData = {url: "points.json", handleAs: "json", content: {}, load: showStops};
			dojo.xhrGet(stopsData);
		}
		
		function showStops(stopsData)
		{
			stopsLayer = new esri.layers.GraphicsLayer();
			map.addLayer(stopsLayer);
			var symbol = new esri.symbol.PictureMarkerSymbol("xx.png",7,7);
			dojo.forEach(stopsData.stations, function(station)
			{				
				var lng = station.x;
				var lat = station.y;
				var name = station.name;
				var point = new esri.geometry.Point(lng,lat);
				var graphic = new esri.Graphic(point,symbol);
				graphic.setInfoTemplate(new esri.InfoTemplate("Busshållsplats",name));
				stopsLayer.add(graphic);
				
			}); 
		}
		
		function getBusData()
		{
			var busData = {url: "gdata.json", handleAs: "json", content: {}, load: setBusPositions};
			dojo.xhrGet(busData);
		}
		
		function setBusPositions(busData)
		{
			for(i = 0; i < positions.length; i++)
			{
				positions[i] = [];
			}
			dojo.forEach(busData.gdata, function(data)
			{				
				var index = data.line - 1;
				var lng = data.long;
				var lat = data.lat;
				positions[index].push([lat, lng]);
			}); 
		}
		
		function makeBusMarkers()
		{  
			bussLayer = new esri.layers.GraphicsLayer();
			map.addLayer(bussLayer);
			var images = new Array(5);
			for(i = 1; i < images.length; i++)
			{
				images[i-1] = "xx" + i + ".png";
			}
			for(i = 0; i < markers.length; i++)
			{
				var point = new esri.geometry.Point(0,0);
				var symbol = new esri.symbol.PictureMarkerSymbol(images[i],40,20);
				var graphic = new esri.Graphic(point,symbol);
				markers[i] = graphic;
				bussLayer.add(markers[i]);
			}  
		}
		
		function initButtons()
		{ 
			require(["dojo/on"], function(on)
			{ 
				dojo.query(".panelButton").forEach(function(entry, i)
				{ 
					entry.addEventListener("click", function(evt)
					{ 
						clearInterval(busInterval);
						showBus(i); 
					}); 
				}); 
			}); 
		} 
		
		function showBus(lineIndex)
		{  
			linje = lineIndex;
			for(i = 0; i < markers.length; i++)
			{
				markers[i].hide();
			}
			markers[linje].show();
			current = positions[linje].length - 1;  
			setInterval(function()
			{ 
				if(current >= 0)
				{ 
					markers[linje].setGeometry(new esri.geometry.Point(positions[linje][current][0], positions[linje][current][1]));
					current--;
					console.log(current);
					
				}
				if(current < 0)
				  { 
					clearInterval(busInterval);
					markers[linje].hide();
				  } 				
			}, 300); 
		}