using ESRI.ArcGIS.ArcMapUI;
using ESRI.ArcGIS.Carto;
using ESRI.ArcGIS.DataSourcesFile;
using ESRI.ArcGIS.DataSourcesRaster;
using ESRI.ArcGIS.Geodatabase;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Project {
    public partial class FloodForm : Form {
        private String rasterPath = @"H:\VT2019\GIS_App\Projekt\Program\Data\UtData\Raster";
        private String vectorPath = @"H:\VT2019\GIS_App\Projekt\Program\Data\UtData\Vektor";

        private IMxDocument mxDoc;
        private IMap map;

        private Utilities utilities;
        private VectorTools vectorTools;
        private RasterTools rasterTools;

        public FloodForm() {
            InitializeComponent();
            initVariables();
        }

        // Initierar global variabler.
        private void initVariables() {
            mxDoc = (IMxDocument)ArcMap.Application.Document;
            map = mxDoc.FocusMap;

            utilities = new Utilities(mxDoc, map);
            vectorTools = new VectorTools(mxDoc, map);
            rasterTools = new RasterTools(mxDoc, map);
        }

        // Uppdaterar fältlistan med alla fältnamn från utvald shapefil.
        private void cmbShapeList_SelectedIndexChanged(object sender, EventArgs e) {
            IFeatureLayer fLayer = utilities.searchVectorLayer(cmbShapeList.SelectedItem.ToString());
            utilities.updateFieldList(cmbFieldList, fLayer);
        }

        // Lägg till raster, listener.
        private void btnAddRaster_Click(object sender, EventArgs e) {
            rasterTools.AddRasterUsingOpenFileDialog(mxDoc.ActiveView);
            utilities.updateLayerList(cmbRasterList, true);
        }

        // Lägg till shape, listener.
        private void btnAddShape_Click(object sender, EventArgs e) {
            vectorTools.AddShapefileUsingOpenFileDialog(mxDoc.ActiveView);
            utilities.updateLayerList(cmbShapeList, false);
        }

        // Individuella funktioner som för tillfället används innan implementering till slutgiltiga produkten.
        #region GroupOfTempFunctions

        // Reagerar om användaren vill beräkna lutningen.
        #region CalcSlope Listener
        private void btnCalcSlope_Click(object sender, EventArgs e) {
            IRasterLayer rLayer = utilities.searchRasterLayer(cmbRasterList.SelectedItem.ToString());
            IDataset dataset = (IDataset)rLayer;

            string inputPath = dataset.Workspace.PathName + rLayer.Name;
            string outputPath = rasterPath + "\\slope.tif";
            rLayer = rasterTools.createSlope(inputPath, outputPath);

            double rMax = rasterTools.rasterStatMax(rLayer.Raster);

            List<IRasterLayer> rList = new List<IRasterLayer>();
            rList.Add(rLayer);

            List<string> sList1 = new List<string>();
            sList1.Add("Slope");

            List<string> sList2 = new List<string>();
            sList2.Add("out1");

            string script = string.Format("[{2}] = SetNull([{1}] > {0}, [{1}])\r", rMax * 0.2, sList1[0], sList2[0]);
            string rName = "Slopes";

            rasterTools.rasterModel(rList, sList1, sList2, script, rName);
        }
        #endregion

        // Reagerar om användaren vill beräkna aspekt.
        #region CalcAspect Listener
        private void btnCalcAspect_Click(object sender, EventArgs e) {
            IRasterLayer rLayer = utilities.searchRasterLayer(cmbRasterList.SelectedItem.ToString());
            IDataset dataset = (IDataset)rLayer;

            string inputPath = dataset.Workspace.PathName + rLayer.Name;
            string outputPath = rasterPath + "\\aspect.tif";
            rLayer = rasterTools.createAspect(inputPath, outputPath);
        }
        #endregion

        // Reagerar om användaren vill klassificera markdata.
        #region Classify Listener
        private void btnClassify_Click(object sender, EventArgs e) {
            List<IRasterLayer> rList = new List<IRasterLayer>();
            IRasterLayer rLayer = utilities.searchRasterLayer(cmbRasterList.SelectedItem.ToString());
            rList.Add(rLayer);

            List<string> sList1 = new List<string>();
            sList1.Add("LandUse");

            List<string> sList2 = new List<string>();
            sList2.Add("out1");
            sList2.Add("out2");
            sList2.Add("out3");

            string script = string.Format("[{1}] = Slice([{0}], Naturalbreaks, 6)\r" +
                "[{2}] = SetNull([{1}] == 1, [{1}])\r" + "[{3}] = Con([{2}] >= 2, 1, [{2}])\r", sList1[0], sList2[0], sList2[1], sList2[2]);
            string rName = "Classify";

            rasterTools.rasterModel(rList, sList1, sList2, script, rName);
        }
        #endregion

        // Beräkna DEM, d.v.s. SetNull.
        #region CalcDem Listener
        private void btnCalcDem_Click(object sender, EventArgs e) {
            #region Variables Setup
            string script = null, rName = null;
            List<string> sList1 = new List<string>();
            List<string> sList2 = new List<string>();

            List<IRasterLayer> rList = new List<IRasterLayer>();
            #endregion

            IRasterLayer rLayer = utilities.searchRasterLayer(cmbRasterList.SelectedItem.ToString());
            double rMax = rasterTools.rasterStatMax(rLayer.Raster);
            double rMin = rasterTools.rasterStatMin(rLayer.Raster);

            MessageBox.Show("Max: " + rMax.ToString() + " Min: " + rMin.ToString() + "Value: " + (rMax + rMin) / 2);

            rList.Add(rLayer);
            sList1.Add("DEM"); // index 0
            sList2.Add("out1"); // index 0

            script = string.Format("[{2}] = SetNull([{1}] > {0}, [{1}])\r", (rMax + rMin) / 2, sList1[0], sList2[0]);
            rName = "DEM";

            rasterTools.rasterModel(rList, sList1, sList2, script, rName);
        }
        #endregion

        // Steg 3, kostnadsraster.
        #region CalcCost Listener
        private void btnCalcCost_Click(object sender, EventArgs e) {
            #region Variables Setup
            string inputPath = null, outputPath = null, 
                script = null, rName = null;
            List<string> sList1 = new List<string>();
            List<string> sList2 = new List<string>();

            List<IRasterLayer> rList = new List<IRasterLayer>();
            IRasterLayer rLayer = null;
            IDataset dataset = null;
            #endregion

            #region CalcSlope for CostRaster
            rLayer = utilities.searchRasterLayer(cmbRasterList.SelectedItem.ToString());
            dataset = (IDataset)rLayer;

            inputPath = dataset.Workspace.PathName + rLayer.Name;
            outputPath = rasterPath + "\\slope.tif";
            rasterTools.createSlope(inputPath, outputPath);

            rLayer = utilities.searchRasterLayer(map.Layer[0].Name);
            double rMax = rasterTools.rasterStatMax(rLayer.Raster);

            rList.Add(rLayer); // index 0
            sList1.Add("Slope"); // index 0
            sList2.Add("out1"); // index 0

            script = string.Format("[{0}] = SetNull([{1}] == 0, [{1}])\r", sList2[0], sList1[0]);
            #endregion

            #region Classify land for CostRaster
            rLayer = utilities.searchRasterLayer("ortoRgb025.tif");
            rList.Add(rLayer); // index 1

            sList1.Add("LandUse"); // index 1
            sList2.Add("out2"); // index 1

            script += string.Format("[{0}] = Slice([{1}], Naturalbreaks, 6)\r", sList2[1], sList1[1]);
            #endregion

            #region Overlay CostRaster
            sList2.Add("out3"); // index 2
            rName = "Kostnadsraster";

            script += string.Format("[{0}] = Int(([{1}] * 0.66) + ([{2}] * 0.34))\r", sList2[2], sList2[0], sList2[1]);

            rasterTools.rasterModel(rList, sList1, sList2, script, rName);
            #endregion
        }
        #endregion

        // Steg 4, distansraster.
        #region CalcDist Listener, step 4
        private void btnCalcDistance_Click(object sender, EventArgs e) {
            #region Variables Setup
            string costPath = null, sourcePath = null, outPath = null;

            IRasterLayer rLayer = null;
            IDataset dataset = null;
            #endregion

            #region CostDistance preperation
            rLayer = utilities.searchRasterLayer("Kostnadsraster");
            dataset = (IDataset)rLayer;
            costPath = dataset.Workspace.PathName + dataset.BrowseName;

            rLayer = utilities.searchRasterLayer("Osäkra platser");
            dataset = (IDataset)rLayer;
            sourcePath = dataset.Workspace.PathName + dataset.BrowseName;

            outPath = rasterPath + "\\distance.tif";

            rasterTools.createDistance(costPath, sourcePath, outPath);
            #endregion
        }
        #endregion

        // Steg 4, allokationsraster.
        #region CalcAlloc Listener, step 4
        private void btnCalcAllocation_Click(object sender, EventArgs e) {
            #region Variables Setup
            string costPath = null, sourcePath = null, outPath = null;

            IRasterLayer rLayer = null;
            IDataset dataset = null;
            #endregion

            #region CostAllocation preperation
            rLayer = utilities.searchRasterLayer("Kostnadsraster");
            dataset = (IDataset)rLayer;
            costPath = dataset.Workspace.PathName + dataset.BrowseName;

            rLayer = utilities.searchRasterLayer("Osäkra platser");
            dataset = (IDataset)rLayer;
            sourcePath = dataset.Workspace.PathName + dataset.BrowseName;

            outPath = rasterPath + "\\backlink.tif";

            rasterTools.createBackLink(costPath, sourcePath, outPath);
            //rasterTools.createAllocation(costPath, sourcePath, outPath);
            #endregion
        }
        #endregion

        // Steg 5, least cost path.
        #region LeastCost Listener, step 5
        private void btnCalcLeastCost_Click(object sender, EventArgs e) {
            #region Variables Setup
            string destPath = null, outPath = null;

            IRasterLayer rLayer = null;
            IDataset dataset = null;
            #endregion

            #region CostPath preperation
            rLayer = utilities.searchRasterLayer("Säkra platser");
            dataset = (IDataset)rLayer;
            destPath = dataset.Workspace.PathName + dataset.BrowseName;

            outPath = rasterPath + "\\leastcost.tif";

            rasterTools.createLeastCost(destPath, outPath);
            #endregion
        }
        #endregion
        #endregion

        // Steg 1-2, listener.
        private void btnCalcSpatialData_Click(object sender, EventArgs e) {
            if (cbxUnsafe.Checked) {
                calcUnsafeArea();
            }

            if (cbxSafe.Checked) {
                calcSafeArea();
            }
        }

        // Steg 3-5, listener.
        private void btnFindWay_Click(object sender, EventArgs e) {
            IRasterLayer safeLayer = utilities.searchRasterLayer("Säkra platser");
            IRasterLayer unsafeLayer = utilities.searchRasterLayer("Osäkra platser");
            if (safeLayer != null && unsafeLayer != null)
                calcPath();
            else
                MessageBox.Show("Lager: säkra och osäkra platser måste existera.");
        }

        // Steg 6-7, listener.
        private void btnCalcStat_Click(object sender, EventArgs e) {
            IRasterLayer safeLayer = utilities.searchRasterLayer("Säkra platser");
            if (cmbShapeList.Items.Count != 0 && safeLayer != null) {
                calcStat();
            } else
                MessageBox.Show("Inga shapefiler tillagda eller säkra platser har inte beräknats.");
        }

        // Steg 1, slutgiltiga. D.v.s. anropar metoder som beräknar osäkra platser.
        private void calcUnsafeArea() {
            #region Variables Setup
            string inputPath = null, outputPath = null,
                script = null, rName = null;
            List<string> sList1 = new List<string>();
            List<string> sList2 = new List<string>();

            List<IRasterLayer> rList = new List<IRasterLayer>();
            IRasterLayer rLayer = null;
            IDataset dataset = null;
            #endregion

            #region AddDEM
            rLayer = utilities.searchRasterLayer(cmbRasterList.SelectedItem.ToString());
            double rMax = rasterTools.rasterStatMax(rLayer.Raster);
            double rMin = rasterTools.rasterStatMin(rLayer.Raster);

            rList.Add(rLayer); // index 0
            sList1.Add("Slope"); // index 0
            sList2.Add("out1"); // index 0

            // Hårdkodat rMax värde vilket filterar bort allt över en viss höjd. Försök att ändra så det inte är hårdkodat...
            script = string.Format("[{2}] = SetNull([{1}] > {0}, [{1}])\r", (rMax + rMin) / 2, sList1[0], sList2[0]);
            #endregion

            #region CalcSlope
            rLayer = utilities.searchRasterLayer(cmbRasterList.SelectedItem.ToString());
            dataset = (IDataset)rLayer;

            inputPath = dataset.Workspace.PathName + rLayer.Name;
            outputPath = rasterPath + "\\slope.tif";
            rLayer = rasterTools.createSlope(inputPath, outputPath);

            rMax = rasterTools.rasterStatMax(rLayer.Raster);

            rList.Add(rLayer); // index 1
            sList1.Add("Slope"); // index 1
            sList2.Add("out2"); // index 1

            script += string.Format("[{2}] = SetNull([{1}] > {0} || [{1}] == 0, [{1}])\r", rMax * 0.08, sList1[1], sList2[1]);
            #endregion

            #region CalcAspect
            rLayer = utilities.searchRasterLayer(cmbRasterList.SelectedItem.ToString());
            dataset = (IDataset)rLayer;

            inputPath = dataset.Workspace.PathName + rLayer.Name;
            outputPath = rasterPath + "\\aspect.tif";
            rLayer = rasterTools.createAspect(inputPath, outputPath);

            rList.Add(rLayer); // index 2
            sList1.Add("Aspect"); // index 2
            #endregion

            #region ClassifyLand
            /*rLayer = utilities.searchRasterLayer("ortoRgb025.tif");
            rList.Add(rLayer);

            sList1.Add("LandUse"); // index 2

            sList2.Add("out2"); // index 1
            sList2.Add("out3"); // index 2
            sList2.Add("out4"); // index 3

            script += string.Format("[{1}] = Slice([{0}], Naturalbreaks, 6)\r" +
            "[{2}] = SetNull([{1}] == 1, [{1}])\r" + "[{3}] = Con([{2}] >= 2, 1, [{2}])\r", 
            sList1[1], sList2[1], sList2[2], sList2[3]);*/
            #endregion

            #region Overlay FloodingAreas
            sList2.Add("out3"); // index 2
            sList2.Add("out4"); // index 3
            sList2.Add("out5"); // index 4
            rName = "Osäkra platser";

            script += string.Format("[{0}] = [{1}] * [{2}] * [{3}]\r" + "[{4}] = Con([{0}] >= 0, 1, [{0}])\r" + "[{5}] = Int([{4}])\r",
                sList2[2], sList2[0], sList2[1], sList1[2], sList2[3], sList2[4]);

            rasterTools.rasterModel(rList, sList1, sList2, script, rName);
            #endregion
        }

        // Steg 2, slutgiltiga. D.v.s. anropar metoder som beräknar ssäkra platser.
        private void calcSafeArea() {
            #region Variables Setup
            string inputPath = null, outputPath = null,
                script = null, rName = null;
            List<string> sList1 = new List<string>();
            List<string> sList2 = new List<string>();

            List<IRasterLayer> rList = new List<IRasterLayer>();
            IRasterLayer rLayer = null;
            IDataset dataset = null;
            #endregion

            #region AddDEM
            rLayer = utilities.searchRasterLayer(cmbRasterList.SelectedItem.ToString());
            double rMax = rasterTools.rasterStatMax(rLayer.Raster);
            double rMin = rasterTools.rasterStatMin(rLayer.Raster);

            rList.Add(rLayer); // index 0
            sList1.Add("DEM"); // index 0
            sList2.Add("out1"); // index 0

            // Hårdkodat rMax värde vilket filterar bort allt över en viss höjd. Försök att ändra så det inte är hårdkodat...
            script = string.Format("[{2}] = SetNull([{1}] < {0}, [{1}])\r", (rMax + rMin) / 2, sList1[0], sList2[0]);
            #endregion

            #region CalcSlope
            rLayer = utilities.searchRasterLayer(cmbRasterList.SelectedItem.ToString());
            dataset = (IDataset)rLayer;

            inputPath = dataset.Workspace.PathName + rLayer.Name;
            outputPath = rasterPath + "\\slope.tif";
            rLayer = rasterTools.createSlope(inputPath, outputPath);

            rMax = rasterTools.rasterStatMax(rLayer.Raster);

            rList.Add(rLayer); // index 1
            sList1.Add("Slope"); // index 1
            sList2.Add("out2"); // index 1

            script += string.Format("[{2}] = SetNull([{1}] < {0} || [{1}] == 0, [{1}])\r", rMax * 0.08, sList1[1], sList2[1]);
            #endregion

            #region CalcAspect
            rLayer = utilities.searchRasterLayer(cmbRasterList.SelectedItem.ToString());
            dataset = (IDataset)rLayer;

            inputPath = dataset.Workspace.PathName + rLayer.Name;
            outputPath = rasterPath + "\\aspect.tif";
            rLayer = rasterTools.createAspect(inputPath, outputPath);

            rList.Add(rLayer); // index 2
            sList1.Add("Aspect"); // index 2
            #endregion

            #region ClassifyLand
            /*rLayer = utilities.searchRasterLayer("ortoRgb025.tif");
            rList.Add(rLayer);

            sList1.Add("LandUse"); // index 2

            sList2.Add("out2"); // index 1
            sList2.Add("out3"); // index 2
            sList2.Add("out4"); // index 3

            script += string.Format("[{1}] = Slice([{0}], Naturalbreaks, 6)\r" +
            "[{2}] = SetNull([{1}] == 1, [{1}])\r" + "[{3}] = Con([{2}] >= 2, 1, [{2}])\r", 
            sList1[1], sList2[1], sList2[2], sList2[3]);*/
            #endregion

            #region Overlay SafeAreas
            sList2.Add("out3"); // index 2
            sList2.Add("out4"); // index 3
            rName = "Säkra platser";

            script += string.Format("[{0}] = [{1}] * [{2}] * [{3}]\r" + "[{4}] = Int(Con([{0}] >= 0, 1, [{0}]))\r",
                sList2[2], sList2[0], sList2[1], sList1[2], sList2[3]);

            rasterTools.rasterModel(rList, sList1, sList2, script, rName);
            #endregion
        }

        // Steg 3-5, slutgiltiga. D.v.s. anropar metoder som beräknar least cost path.
        private void calcPath() {
            #region  Variable Setup
            string inPath = null, outPath = null, destPath = null,
                script = null, rName = null, costPath = null,
                sourcePath = null;

            List<string> sList1 = new List<string>();
            List<string> sList2 = new List<string>();

            List<IRasterLayer> rList = new List<IRasterLayer>();
            IRasterLayer rLayer = null;
            IDataset dataset = null;
            #endregion

            #region CostRaster Step 3
            #region CalcSlope for CostRaster
            rLayer = utilities.searchRasterLayer(cmbRasterList.SelectedItem.ToString());
            dataset = (IDataset)rLayer;

            inPath = dataset.Workspace.PathName + rLayer.Name;
            outPath = rasterPath + "\\slope.tif";
            rLayer = rasterTools.createSlope(inPath, outPath);

            double rMax = rasterTools.rasterStatMax(rLayer.Raster);

            rList.Add(rLayer); // index 0
            sList1.Add("Slope"); // index 0
            sList2.Add("out1"); // index 0

            script = string.Format("[{0}] = SetNull([{1}] == 0, [{1}])\r", sList2[0], sList1[0]);
            #endregion

            #region Classify land for CostRaster
            rLayer = utilities.searchRasterLayer(cmbRasterList.SelectedItem.ToString());
            rList.Add(rLayer); // index 1

            sList1.Add("LandUse"); // index 1
            sList2.Add("out2"); // index 1

            script += string.Format("[{0}] = Slice([{1}], Naturalbreaks, 6)\r", sList2[1], sList1[1]);
            #endregion

            #region Overlay CostRaster
            sList2.Add("out3"); // index 2
            rName = "Kostnadsraster";

            script += string.Format("[{0}] = Int(([{1}] * 0.66) + ([{2}] * 0.34))\r", sList2[2], sList2[0], sList2[1]);
            rasterTools.rasterModel(rList, sList1, sList2, script, rName);
            #endregion
            #endregion

            #region Step 4 - CostDistance & BackLink
            #region CostDistance
            outPath = rasterPath + "\\distance.tif";

            rasterTools.createDistance(costPath, sourcePath, outPath);
            #endregion

            #region CostBackLink
            outPath = rasterPath + "\\backlink.tif";

            rasterTools.createBackLink(costPath, sourcePath, outPath);
            #endregion
            #endregion

            #region Step 5 - CostPath
            outPath = rasterPath + "\\leastcost.tif";

            rLayer = rasterTools.createLeastCost(destPath, outPath);
            #endregion
        }

        // Steg 6-7, slutgiltiga. D.v.s. anropar metoder som beräknar statistik och spatialt filtrerar vägar. 
        private void calcStat() {
            IFeatureLayer fLayer1 = rasterTools.convertRasterToVector(vectorPath, "SafeAreas");
            IFeatureLayer fLayer2 = utilities.searchVectorLayer(cmbShapeList.SelectedItem.ToString());

            IFeatureSelection fSelect = vectorTools.spatialFilter(fLayer1, fLayer2);
            vectorTools.showStatistics(fSelect, cmbFieldList.SelectedItem.ToString());
        }
    }
}
