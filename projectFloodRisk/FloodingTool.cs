using System;
using System.Collections.Generic;
using System.Text;
using System.IO;
using System.Windows.Forms;

namespace Project {
    public class FloodingTool : ESRI.ArcGIS.Desktop.AddIns.Tool {
        FloodForm form = null;
        public FloodingTool() {
        }

        protected override void OnActivate() {
            form = new FloodForm();
            form.Show();
        }

        protected override void OnUpdate() {
            Enabled = ArcMap.Application != null;
        }
    }

}
