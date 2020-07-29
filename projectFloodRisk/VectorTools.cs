using ESRI.ArcGIS.ArcMapUI;
using ESRI.ArcGIS.Carto;
using ESRI.ArcGIS.GeoAnalyst;
using ESRI.ArcGIS.Geodatabase;
using ESRI.ArcGIS.Geometry;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Project {
    class VectorTools {
        private IMxDocument mxDoc;
        private IMap map;

        public VectorTools(IMxDocument mxDoc, IMap map) {
            this.mxDoc = mxDoc;
            this.map = map;
        }

        // Öppnar shapefil.
        public void AddShapefileUsingOpenFileDialog(ESRI.ArcGIS.Carto.IActiveView activeView) {
            //parameter check
            if (activeView == null) {
                return;
            }

            // Use the OpenFileDialog Class to choose which shapefile to load.
            System.Windows.Forms.OpenFileDialog openFileDialog = new System.Windows.Forms.OpenFileDialog();
            openFileDialog.InitialDirectory = @"H:\VT2019\GIS_App\Projekt\Program\Data\InData\Vektor";
            openFileDialog.Filter = "Shapefiles (*.shp)|*.shp";
            openFileDialog.FilterIndex = 2;
            openFileDialog.RestoreDirectory = true;
            openFileDialog.Multiselect = false;


            if (openFileDialog.ShowDialog() == System.Windows.Forms.DialogResult.OK) {
                // The user chose a particular shapefile.

                // The returned string will be the full path, filename and file-extension for the chosen shapefile. Example: "C:\test\cities.shp"
                string shapefileLocation = openFileDialog.FileName;

                if (shapefileLocation != "") {
                    // Ensure the user chooses a shapefile

                    // Create a new ShapefileWorkspaceFactory CoClass to create a new workspace
                    ESRI.ArcGIS.Geodatabase.IWorkspaceFactory workspaceFactory = new ESRI.ArcGIS.DataSourcesFile.ShapefileWorkspaceFactoryClass();

                    // System.IO.Path.GetDirectoryName(shapefileLocation) returns the directory part of the string. Example: "C:\test\"
                    ESRI.ArcGIS.Geodatabase.IFeatureWorkspace featureWorkspace = (ESRI.ArcGIS.Geodatabase.IFeatureWorkspace)workspaceFactory.OpenFromFile(System.IO.Path.GetDirectoryName(shapefileLocation), 0); // Explicit Cast

                    // System.IO.Path.GetFileNameWithoutExtension(shapefileLocation) returns the base filename (without extension). Example: "cities"
                    ESRI.ArcGIS.Geodatabase.IFeatureClass featureClass = featureWorkspace.OpenFeatureClass(System.IO.Path.GetFileNameWithoutExtension(shapefileLocation));

                    ESRI.ArcGIS.Carto.IFeatureLayer featureLayer = new ESRI.ArcGIS.Carto.FeatureLayerClass();
                    featureLayer.FeatureClass = featureClass;
                    featureLayer.Name = featureClass.AliasName;
                    featureLayer.Visible = true;
                    activeView.FocusMap.AddLayer(featureLayer);

                    // Zoom the display to the full extent of all layers in the map
                    activeView.Extent = activeView.FullExtent;
                    activeView.PartialRefresh(ESRI.ArcGIS.Carto.esriViewDrawPhase.esriViewGeography, null, null);
                } else {
                }
            } else {
            }
        }

        // Spatialt filtrerar de objekt e.g. vägar som korsar säkra platser, d.v.s. platser som inte kommer att påverkas av översvämning.
        public IFeatureSelection spatialFilter(IFeatureLayer safeLayer, IFeatureLayer roadLayer) {
            try {          
                IQueryFilter queryFilter = new QueryFilter();

                IFeatureClass safeFC = safeLayer.FeatureClass;
                IFeatureCursor safeCursor = safeFC.Search(queryFilter, true);
                IFeature safeFeature = safeCursor.NextFeature();

                ISpatialFilter spatialFilter = new SpatialFilter();
                spatialFilter.SpatialRel = esriSpatialRelEnum.esriSpatialRelIntersects;
                spatialFilter.Geometry = safeFeature.Shape;
                spatialFilter.GeometryField = safeFeature.Shape.ToString();

                IFeatureSelection fSelect = (IFeatureSelection)roadLayer;
                while (safeFeature != null) {
                    fSelect.SelectFeatures(spatialFilter, esriSelectionResultEnum.esriSelectionResultAdd, false);
                    safeFeature = safeCursor.NextFeature();
                }

                mxDoc.ActiveView.Refresh();
                return fSelect;
            } catch (Exception ex) {
                MessageBox.Show(ex.StackTrace);
                return null;
            }
        }

        // Tar fram statistik om utvalda features.
        public void showStatistics(IFeatureSelection fSelect, string fieldName) {
            if (fSelect.SelectionSet.Count != 0) {
                string roadCount = "Antal vägar: " + fSelect.SelectionSet.Count.ToString() + "\r";

                IFeatureLayer fLayer = (IFeatureLayer)fSelect;

                IDataStatistics dataStat = new DataStatistics();
                dataStat.Field = fieldName;
                dataStat.Cursor = (ICursor)fLayer.Search(null, false);

                IEnumerator enumVar = dataStat.UniqueValues;
                if (enumVar == null) {
                    MessageBox.Show("Kolumn tom.");
                } else {
                    enumVar.Reset();
                    string tempString = fieldName + ": \r";
                    while (enumVar.MoveNext() != false) {
                        object value = enumVar.Current;
                        tempString += value.ToString() + "\r";
                    }
                    MessageBox.Show(roadCount + tempString, "Närmaste vägar");
                }
            } else {
                MessageBox.Show("Inga objekt är utvalda.");
            }
        }
    }

}
