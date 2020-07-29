using ESRI.ArcGIS.ArcMapUI;
using ESRI.ArcGIS.Carto;
using ESRI.ArcGIS.Geodatabase;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Project {
    class Utilities {
        private IMxDocument mxDoc;
        private IMap map;

        public Utilities(IMxDocument mxDoc, IMap map) {
            this.mxDoc = mxDoc;
            this.map = map;
        }

        // Uppdaterar lagerlista.
        public void updateLayerList(ComboBox cmbList, bool isRast) {
            if (map.LayerCount != 0) {
                cmbList.Items.Clear();
                for (int i = 0; i < map.LayerCount; i++) {
                    if (map.Layer[i] is IFeatureLayer && isRast == false) {
                        IFeatureLayer fLayer = (IFeatureLayer)map.Layer[i];
                        cmbList.Items.Add(fLayer.FeatureClass.AliasName);

                    } else if (map.Layer[i] is IRasterLayer && isRast == true) {
                        IRasterLayer rLayer = (IRasterLayer)map.Layer[i];
                        cmbList.Items.Add(rLayer.Name);
                    }
                }       
                cmbList.SelectedIndex = 0;
            }
        }

        // Uppdaterar fältlista.
        public void updateFieldList(ComboBox cmbColumnName, IFeatureLayer fLayer) {
            cmbColumnName.Items.Clear();

            IFeatureClass fClass = fLayer.FeatureClass;
            for (int j = 0; j < fClass.Fields.FieldCount; j++) {
                cmbColumnName.Items.Add(fClass.Fields.Field[j].Name);
            }
            cmbColumnName.SelectedIndex = 0;
        }

        // Letar efter utvald rasterlager.
        public IRasterLayer searchRasterLayer(string layerName) {
            IRasterLayer selectedLayer = null;
            for (int i = 0; i < map.LayerCount; i++) {

                if (map.Layer[i] is IRasterLayer) {

                    IRasterLayer rLayer = (IRasterLayer)map.Layer[i];
                    if (rLayer.Name == layerName) {
                        selectedLayer = rLayer;
                        break;
                    }
                }
            }
            return selectedLayer;
        }

        // Letar efter utvald vektorlager.
        public IFeatureLayer searchVectorLayer(string layerName) {
            IFeatureLayer selectedLayer = null;
            for (int i = 0; i < map.LayerCount; i++) {

                if (map.Layer[i] is IFeatureLayer) {

                    IFeatureLayer vLayer = (IFeatureLayer)map.Layer[i];
                    if (vLayer.Name == layerName) {
                        selectedLayer = vLayer;
                        break;
                    }
                }
            }
            return selectedLayer;
        }
    }
}
