using ESRI.ArcGIS.ArcMapUI;
using ESRI.ArcGIS.Carto;
using ESRI.ArcGIS.DataSourcesRaster;
using ESRI.ArcGIS.Geodatabase;
using ESRI.ArcGIS.GeoAnalyst;
using ESRI.ArcGIS.Geoprocessor;
using ESRI.ArcGIS.SpatialAnalystTools;
using ESRI.ArcGIS.Geoprocessing;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using ESRI.ArcGIS.DataSourcesFile;
using ESRI.ArcGIS.Display;

namespace Project {
    class RasterTools {
        private IMxDocument mxDoc;
        private IMap map;

        private IRasterLayer costDist = null, backLink = null, slope = null, 
            aspect = null, source = null, dest = null, leastCost = null, cost = null;

        public RasterTools(IMxDocument mxDoc, IMap map) {
            this.mxDoc = mxDoc;
            this.map = map;
        }

        // Öppnar rasterlager.
        public void AddRasterUsingOpenFileDialog(IActiveView activeView) {

            if (activeView == null) {
                return;
            }

            System.Windows.Forms.OpenFileDialog openFileDialog = new
            System.Windows.Forms.OpenFileDialog();
            openFileDialog.InitialDirectory = @"H:\VT2019\GIS_App\Projekt\Program\Data\InData";
            openFileDialog.Filter = "Rasterfiles (*.tif)|*.tif";
            openFileDialog.FilterIndex = 2;
            openFileDialog.RestoreDirectory = true;
            openFileDialog.Multiselect = false;

            if (openFileDialog.ShowDialog() == System.Windows.Forms.DialogResult.OK) {

                IRasterLayer rasterLayer = new RasterLayer();
                rasterLayer.CreateFromFilePath(openFileDialog.FileName);
                mxDoc.AddLayer(rasterLayer);
            }
        }

        // Konverterar raster till vektor.
        public IFeatureLayer convertRasterToVector(string outPath, string fileName) {
            IGeoDataset pLayerDataset = (IGeoDataset)dest;

            IConversionOp conversionOp = new RasterConversionOpClass();
            IRasterAnalysisEnvironment env = (IRasterAnalysisEnvironment)conversionOp;

            IWorkspaceFactory workspaceFactory = new RasterWorkspaceFactoryClass();
            IWorkspace workspace = workspaceFactory.OpenFromFile(outPath, 0);
            env.OutWorkspace = workspace;

            IWorkspaceFactory wsFactory = new ShapefileWorkspaceFactoryClass();
            IWorkspace shapeWS = wsFactory.OpenFromFile(outPath, 0);

            IFeatureWorkspace fWorkspace = (IFeatureWorkspace)wsFactory.OpenFromFile(outPath, 0);
            IWorkspace2 workTemp = (IWorkspace2)fWorkspace;
            if (workTemp.NameExists[esriDatasetType.esriDTFeatureClass, fileName]) {
                IDataset dataset = (IDataset)fWorkspace.OpenFeatureClass(fileName);
                dataset.Delete();
            }

            IGeoDataset featPolClassOut = conversionOp.RasterDataToPolygonFeatureData(pLayerDataset, shapeWS, fileName + ".shp", true);
            IFeatureClass fClass = fWorkspace.OpenFeatureClass(fileName);
            IFeatureLayer fLayer = new FeatureLayerClass();

            fLayer.FeatureClass = fClass;
            fLayer.Name = fClass.AliasName;
            fLayer.Visible = true;

            map.AddLayer(fLayer);

            return fLayer;
        }

        // Hämtar maxvärdet i rasterlager.
        public double rasterStatMax(IRaster inputRaster) {
            IRasterBandCollection bands = (IRasterBandCollection)inputRaster;
            IRasterBand rasterBand = bands.Item(0);
            IRasterStatistics rs = rasterBand.Statistics;

            return rs.Maximum;
        }

        // Hämtar minvärdet i rasterlager.
        public double rasterStatMin(IRaster inputRaster) {
            IRasterBandCollection bands = (IRasterBandCollection)inputRaster;
            IRasterBand rasterBand = bands.Item(0);
            IRasterStatistics rs = rasterBand.Statistics;

            return rs.Minimum;
        }

        // Samling av metoder för Geoprocessor.
        #region Geoproccesing Methods

        // Skapar lutningslager.
        public IRasterLayer createSlope(System.String inRaster, System.String outPath) {
            if (slope != null)
                return slope;

            IRasterLayer rLayer = new RasterLayer();
            if (inRaster == "" || outPath == "") {
                MessageBox.Show("Check your rasters");
            } else {
                Geoprocessor gp = new Geoprocessor();
                gp.OverwriteOutput = true;
                //gp.AddOutputsToMap = false;

                Slope slopeTool = new Slope();
                slopeTool.in_raster = inRaster;
                slopeTool.out_raster = outPath;

                IGeoProcessorResult geoProcessorResult = (IGeoProcessorResult)gp.Execute(slopeTool, null);
                rLayer.CreateFromFilePath(outPath);
                slope = rLayer;
            }
            return rLayer;
        }

        // Skapar aspektlager.
        public IRasterLayer createAspect(System.String inRaster, System.String outPath) {
            if (aspect != null)
                return aspect;

            IRasterLayer rLayer = new RasterLayer();
            if (inRaster == "" || outPath == "") {
                MessageBox.Show("Check your rasters");
            } else {
                Geoprocessor gp = new Geoprocessor();
                gp.OverwriteOutput = true;
                //gp.AddOutputsToMap = false;

                Aspect aspectTool = new Aspect();
                aspectTool.in_raster = inRaster;
                aspectTool.out_raster = outPath;

                IGeoProcessorResult geoProcessorResult = (IGeoProcessorResult)gp.Execute(aspectTool, null);
                rLayer.CreateFromFilePath(outPath);
                aspect = rLayer;
            }
            return rLayer;
        }

        // Skapar backlinklager.
        public void createBackLink(System.String costRaster, System.String sourceRaster, System.String outPath) {
            if (costRaster == "" || sourceRaster == "" || outPath == "") {
                MessageBox.Show("Check your rasters");
            } else {
                Geoprocessor gp = new Geoprocessor();
                gp.OverwriteOutput = true;
                //gp.AddOutputsToMap = false;

                CostBackLink backLinkTool = new CostBackLink();
                backLinkTool.in_cost_raster = cost;
                backLinkTool.in_source_data = source;
                backLinkTool.out_backlink_raster = outPath;

                IGeoProcessorResult geoProcessorResult = (IGeoProcessorResult)gp.Execute(backLinkTool, null);
                backLink = new RasterLayer();
                backLink.CreateFromFilePath(outPath);
            }
        }

        // Skapar distanslager.
        public void createDistance(System.String costPath, System.String sourcePath, System.String outPath) {
            if (costPath == "" || sourcePath == "" || outPath == "") {
                MessageBox.Show("Check your rasters");
            } else {
                Geoprocessor gp = new Geoprocessor();
                gp.OverwriteOutput = true;
                //gp.AddOutputsToMap = false;

                CostDistance distanceTool = new CostDistance();
                distanceTool.in_cost_raster = cost;
                distanceTool.in_source_data = source;
                distanceTool.out_distance_raster = outPath;

                IGeoProcessorResult geoProcessorResult = (IGeoProcessorResult)gp.Execute(distanceTool, null);
                costDist = new RasterLayer();
                costDist.CreateFromFilePath(outPath);
            }
        }

        // Skapar minstakostnadslager.
        public IRasterLayer createLeastCost(System.String destinationRaster, System.String outPath) {
            if (destinationRaster == "" || outPath == "") {
                MessageBox.Show("Check your rasters");
            } else {
                Geoprocessor gp = new Geoprocessor();
                gp.OverwriteOutput = true;
                //gp.AddOutputsToMap = false;

                CostPath costPathTool = new CostPath();
                costPathTool.in_cost_backlink_raster = backLink;
                costPathTool.in_cost_distance_raster = costDist;
                costPathTool.in_destination_data = dest;
                costPathTool.out_raster = outPath;

                IGeoProcessorResult geoProcessorResult = (IGeoProcessorResult)gp.Execute(costPathTool, null);
                leastCost = new RasterLayer();
                leastCost.CreateFromFilePath(outPath);
            }
            return leastCost;
        }
        #endregion

        // RasterModel ger möjlighet för klassificering, ändring av värden m.m.
        public void rasterModel(List<IRasterLayer> rList, List<string> sList1, List<string> sList2, string script, string rName) {
            IRasterModel rasterModel = new RasterModelClass();

            IRasterAnalysisEnvironment env = (IRasterAnalysisEnvironment)rasterModel;
            IWorkspaceFactory workspaceFactory = new RasterWorkspaceFactoryClass();
            IWorkspace workspace = workspaceFactory.OpenFromFile(@"H:\VT2019\GIS_App\Projekt\Program\Data\UtData\Raster", 0);
            env.OutWorkspace = workspace;

            rasterModel.Script = script;

            for (int i = 0; i < rList.Count; i++) {
                rasterModel.BindRaster(rList[i].Raster, sList1[i]);
            }

            rasterModel.Execute();
            IRaster rResult1 = rasterModel.get_BoundRaster(sList2[sList2.Count - 1]);

            for (int i = 0; i < sList1.Count; i++) {
                rasterModel.UnbindSymbol(sList1[i]);
            }

            IRasterLayer newRaster = new RasterLayer();
            newRaster.CreateFromRaster(rResult1);
            newRaster.Name = rName;
            map.AddLayer(newRaster);

            switch (rName) {
                case "Kostnadsraster":
                    cost = newRaster;
                    //map.DeleteLayer(newRaster);
                    break;
                case "Osäkra platser":
                    source = newRaster;
                    break;
                case "Säkra platser":
                    dest = newRaster;
                    break;
            }
        }
    }


}
