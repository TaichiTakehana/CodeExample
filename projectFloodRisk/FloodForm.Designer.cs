namespace Project {
    partial class FloodForm {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing) {
            if (disposing && (components != null)) {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent() {
            this.cmbRasterList = new System.Windows.Forms.ComboBox();
            this.cmbShapeList = new System.Windows.Forms.ComboBox();
            this.btnAddShape = new System.Windows.Forms.Button();
            this.btnAddRaster = new System.Windows.Forms.Button();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.cbxSafe = new System.Windows.Forms.CheckBox();
            this.btnFindWay = new System.Windows.Forms.Button();
            this.btnCalcSpatialData = new System.Windows.Forms.Button();
            this.cbxUnsafe = new System.Windows.Forms.CheckBox();
            this.btnClassify = new System.Windows.Forms.Button();
            this.btnCalcAspect = new System.Windows.Forms.Button();
            this.btnCalcSlope = new System.Windows.Forms.Button();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.btnCalcDem = new System.Windows.Forms.Button();
            this.groupBox4 = new System.Windows.Forms.GroupBox();
            this.btnCalcCost = new System.Windows.Forms.Button();
            this.groupBox5 = new System.Windows.Forms.GroupBox();
            this.btnCalcLeastCost = new System.Windows.Forms.Button();
            this.groupBox6 = new System.Windows.Forms.GroupBox();
            this.btnCalcAllocation = new System.Windows.Forms.Button();
            this.btnCalcDistance = new System.Windows.Forms.Button();
            this.btnCalcStat = new System.Windows.Forms.Button();
            this.groupBox7 = new System.Windows.Forms.GroupBox();
            this.cmbFieldList = new System.Windows.Forms.ComboBox();
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.groupBox10 = new System.Windows.Forms.GroupBox();
            this.groupBox8 = new System.Windows.Forms.GroupBox();
            this.label4 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.groupBox9 = new System.Windows.Forms.GroupBox();
            this.label7 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.tabPage2 = new System.Windows.Forms.TabPage();
            this.groupBox2.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.groupBox4.SuspendLayout();
            this.groupBox5.SuspendLayout();
            this.groupBox6.SuspendLayout();
            this.groupBox7.SuspendLayout();
            this.tabControl1.SuspendLayout();
            this.tabPage1.SuspendLayout();
            this.groupBox10.SuspendLayout();
            this.groupBox8.SuspendLayout();
            this.groupBox9.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.tabPage2.SuspendLayout();
            this.SuspendLayout();
            // 
            // cmbRasterList
            // 
            this.cmbRasterList.FormattingEnabled = true;
            this.cmbRasterList.Location = new System.Drawing.Point(6, 19);
            this.cmbRasterList.Name = "cmbRasterList";
            this.cmbRasterList.Size = new System.Drawing.Size(121, 21);
            this.cmbRasterList.TabIndex = 0;
            // 
            // cmbShapeList
            // 
            this.cmbShapeList.FormattingEnabled = true;
            this.cmbShapeList.Location = new System.Drawing.Point(133, 19);
            this.cmbShapeList.Name = "cmbShapeList";
            this.cmbShapeList.Size = new System.Drawing.Size(121, 21);
            this.cmbShapeList.TabIndex = 1;
            this.cmbShapeList.SelectedIndexChanged += new System.EventHandler(this.cmbShapeList_SelectedIndexChanged);
            // 
            // btnAddShape
            // 
            this.btnAddShape.Location = new System.Drawing.Point(133, 46);
            this.btnAddShape.Name = "btnAddShape";
            this.btnAddShape.Size = new System.Drawing.Size(121, 23);
            this.btnAddShape.TabIndex = 3;
            this.btnAddShape.Text = "Lägg till shape (1)";
            this.btnAddShape.UseVisualStyleBackColor = true;
            this.btnAddShape.Click += new System.EventHandler(this.btnAddShape_Click);
            // 
            // btnAddRaster
            // 
            this.btnAddRaster.Location = new System.Drawing.Point(6, 46);
            this.btnAddRaster.Name = "btnAddRaster";
            this.btnAddRaster.Size = new System.Drawing.Size(121, 23);
            this.btnAddRaster.TabIndex = 2;
            this.btnAddRaster.Text = "Lägg till raster (1)";
            this.btnAddRaster.UseVisualStyleBackColor = true;
            this.btnAddRaster.Click += new System.EventHandler(this.btnAddRaster_Click);
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.cbxSafe);
            this.groupBox2.Controls.Add(this.btnFindWay);
            this.groupBox2.Controls.Add(this.btnCalcSpatialData);
            this.groupBox2.Controls.Add(this.cbxUnsafe);
            this.groupBox2.Location = new System.Drawing.Point(348, 253);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(292, 78);
            this.groupBox2.TabIndex = 3;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Analys - Steg 2 och 3";
            // 
            // cbxSafe
            // 
            this.cbxSafe.AutoSize = true;
            this.cbxSafe.Location = new System.Drawing.Point(133, 23);
            this.cbxSafe.Name = "cbxSafe";
            this.cbxSafe.Size = new System.Drawing.Size(88, 17);
            this.cbxSafe.TabIndex = 7;
            this.cbxSafe.Text = "Säkra platser";
            this.cbxSafe.UseVisualStyleBackColor = true;
            // 
            // btnFindWay
            // 
            this.btnFindWay.Location = new System.Drawing.Point(6, 48);
            this.btnFindWay.Name = "btnFindWay";
            this.btnFindWay.Size = new System.Drawing.Size(121, 23);
            this.btnFindWay.TabIndex = 5;
            this.btnFindWay.Text = "Hitta väg (3)";
            this.btnFindWay.UseVisualStyleBackColor = true;
            this.btnFindWay.Click += new System.EventHandler(this.btnFindWay_Click);
            // 
            // btnCalcSpatialData
            // 
            this.btnCalcSpatialData.Location = new System.Drawing.Point(6, 19);
            this.btnCalcSpatialData.Name = "btnCalcSpatialData";
            this.btnCalcSpatialData.Size = new System.Drawing.Size(121, 23);
            this.btnCalcSpatialData.TabIndex = 10;
            this.btnCalcSpatialData.Text = "Skapa lager (2)";
            this.btnCalcSpatialData.UseVisualStyleBackColor = true;
            this.btnCalcSpatialData.Click += new System.EventHandler(this.btnCalcSpatialData_Click);
            // 
            // cbxUnsafe
            // 
            this.cbxUnsafe.AutoSize = true;
            this.cbxUnsafe.Location = new System.Drawing.Point(133, 52);
            this.cbxUnsafe.Name = "cbxUnsafe";
            this.cbxUnsafe.Size = new System.Drawing.Size(94, 17);
            this.cbxUnsafe.TabIndex = 6;
            this.cbxUnsafe.Text = "Osäkra platser";
            this.cbxUnsafe.UseVisualStyleBackColor = true;
            // 
            // btnClassify
            // 
            this.btnClassify.Location = new System.Drawing.Point(6, 48);
            this.btnClassify.Name = "btnClassify";
            this.btnClassify.Size = new System.Drawing.Size(121, 23);
            this.btnClassify.TabIndex = 2;
            this.btnClassify.Text = "Klassificera mark";
            this.btnClassify.UseVisualStyleBackColor = true;
            this.btnClassify.Click += new System.EventHandler(this.btnClassify_Click);
            // 
            // btnCalcAspect
            // 
            this.btnCalcAspect.Location = new System.Drawing.Point(133, 19);
            this.btnCalcAspect.Name = "btnCalcAspect";
            this.btnCalcAspect.Size = new System.Drawing.Size(121, 23);
            this.btnCalcAspect.TabIndex = 1;
            this.btnCalcAspect.Text = "Beräkna aspekt";
            this.btnCalcAspect.UseVisualStyleBackColor = true;
            this.btnCalcAspect.Click += new System.EventHandler(this.btnCalcAspect_Click);
            // 
            // btnCalcSlope
            // 
            this.btnCalcSlope.Location = new System.Drawing.Point(6, 19);
            this.btnCalcSlope.Name = "btnCalcSlope";
            this.btnCalcSlope.Size = new System.Drawing.Size(121, 23);
            this.btnCalcSlope.TabIndex = 0;
            this.btnCalcSlope.Text = "Beräkna lutning";
            this.btnCalcSlope.UseVisualStyleBackColor = true;
            this.btnCalcSlope.Click += new System.EventHandler(this.btnCalcSlope_Click);
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.btnCalcDem);
            this.groupBox3.Controls.Add(this.btnCalcSlope);
            this.groupBox3.Controls.Add(this.btnClassify);
            this.groupBox3.Controls.Add(this.btnCalcAspect);
            this.groupBox3.Location = new System.Drawing.Point(50, 64);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(261, 80);
            this.groupBox3.TabIndex = 4;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "Individuella funktioner för steg 1";
            // 
            // btnCalcDem
            // 
            this.btnCalcDem.Location = new System.Drawing.Point(133, 48);
            this.btnCalcDem.Name = "btnCalcDem";
            this.btnCalcDem.Size = new System.Drawing.Size(121, 23);
            this.btnCalcDem.TabIndex = 3;
            this.btnCalcDem.Text = "Beräkna DEM";
            this.btnCalcDem.UseVisualStyleBackColor = true;
            this.btnCalcDem.Click += new System.EventHandler(this.btnCalcDem_Click);
            // 
            // groupBox4
            // 
            this.groupBox4.Controls.Add(this.btnCalcCost);
            this.groupBox4.Location = new System.Drawing.Point(317, 64);
            this.groupBox4.Name = "groupBox4";
            this.groupBox4.Size = new System.Drawing.Size(192, 80);
            this.groupBox4.TabIndex = 5;
            this.groupBox4.TabStop = false;
            this.groupBox4.Text = "Kostnadsraster funktion";
            // 
            // btnCalcCost
            // 
            this.btnCalcCost.Location = new System.Drawing.Point(6, 19);
            this.btnCalcCost.Name = "btnCalcCost";
            this.btnCalcCost.Size = new System.Drawing.Size(180, 23);
            this.btnCalcCost.TabIndex = 0;
            this.btnCalcCost.Text = "Steg 3: Beräkna kostnadsraster";
            this.btnCalcCost.UseVisualStyleBackColor = true;
            this.btnCalcCost.Click += new System.EventHandler(this.btnCalcCost_Click);
            // 
            // groupBox5
            // 
            this.groupBox5.Controls.Add(this.btnCalcLeastCost);
            this.groupBox5.Location = new System.Drawing.Point(317, 150);
            this.groupBox5.Name = "groupBox5";
            this.groupBox5.Size = new System.Drawing.Size(192, 100);
            this.groupBox5.TabIndex = 6;
            this.groupBox5.TabStop = false;
            this.groupBox5.Text = "Least Cost Path";
            // 
            // btnCalcLeastCost
            // 
            this.btnCalcLeastCost.Location = new System.Drawing.Point(6, 19);
            this.btnCalcLeastCost.Name = "btnCalcLeastCost";
            this.btnCalcLeastCost.Size = new System.Drawing.Size(180, 23);
            this.btnCalcLeastCost.TabIndex = 0;
            this.btnCalcLeastCost.Text = "Steg 5: Beräkna least cost path";
            this.btnCalcLeastCost.UseVisualStyleBackColor = true;
            this.btnCalcLeastCost.Click += new System.EventHandler(this.btnCalcLeastCost_Click);
            // 
            // groupBox6
            // 
            this.groupBox6.Controls.Add(this.btnCalcAllocation);
            this.groupBox6.Controls.Add(this.btnCalcDistance);
            this.groupBox6.Location = new System.Drawing.Point(50, 150);
            this.groupBox6.Name = "groupBox6";
            this.groupBox6.Size = new System.Drawing.Size(261, 100);
            this.groupBox6.TabIndex = 7;
            this.groupBox6.TabStop = false;
            this.groupBox6.Text = "Distans- och allokationsraster";
            // 
            // btnCalcAllocation
            // 
            this.btnCalcAllocation.Location = new System.Drawing.Point(7, 49);
            this.btnCalcAllocation.Name = "btnCalcAllocation";
            this.btnCalcAllocation.Size = new System.Drawing.Size(247, 23);
            this.btnCalcAllocation.TabIndex = 9;
            this.btnCalcAllocation.Text = "Steg 4: Beräkna allokationsraster";
            this.btnCalcAllocation.UseVisualStyleBackColor = true;
            this.btnCalcAllocation.Click += new System.EventHandler(this.btnCalcAllocation_Click);
            // 
            // btnCalcDistance
            // 
            this.btnCalcDistance.Location = new System.Drawing.Point(6, 19);
            this.btnCalcDistance.Name = "btnCalcDistance";
            this.btnCalcDistance.Size = new System.Drawing.Size(248, 23);
            this.btnCalcDistance.TabIndex = 8;
            this.btnCalcDistance.Text = "Steg 4: Beräkna distansraster";
            this.btnCalcDistance.UseVisualStyleBackColor = true;
            this.btnCalcDistance.Click += new System.EventHandler(this.btnCalcDistance_Click);
            // 
            // btnCalcStat
            // 
            this.btnCalcStat.Location = new System.Drawing.Point(140, 18);
            this.btnCalcStat.Name = "btnCalcStat";
            this.btnCalcStat.Size = new System.Drawing.Size(114, 23);
            this.btnCalcStat.TabIndex = 8;
            this.btnCalcStat.Text = "Visa Statistik (4)";
            this.btnCalcStat.UseVisualStyleBackColor = true;
            this.btnCalcStat.Click += new System.EventHandler(this.btnCalcStat_Click);
            // 
            // groupBox7
            // 
            this.groupBox7.Controls.Add(this.cmbFieldList);
            this.groupBox7.Controls.Add(this.btnCalcStat);
            this.groupBox7.Location = new System.Drawing.Point(6, 337);
            this.groupBox7.Name = "groupBox7";
            this.groupBox7.Size = new System.Drawing.Size(324, 66);
            this.groupBox7.TabIndex = 9;
            this.groupBox7.TabStop = false;
            this.groupBox7.Text = "Statistik - Steg 4";
            // 
            // cmbFieldList
            // 
            this.cmbFieldList.FormattingEnabled = true;
            this.cmbFieldList.Location = new System.Drawing.Point(7, 20);
            this.cmbFieldList.Name = "cmbFieldList";
            this.cmbFieldList.Size = new System.Drawing.Size(121, 21);
            this.cmbFieldList.TabIndex = 9;
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tabPage1);
            this.tabControl1.Controls.Add(this.tabPage2);
            this.tabControl1.Location = new System.Drawing.Point(12, 12);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(657, 436);
            this.tabControl1.TabIndex = 11;
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this.groupBox10);
            this.tabPage1.Controls.Add(this.groupBox1);
            this.tabPage1.Controls.Add(this.groupBox7);
            this.tabPage1.Controls.Add(this.groupBox2);
            this.tabPage1.Location = new System.Drawing.Point(4, 22);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage1.Size = new System.Drawing.Size(649, 410);
            this.tabPage1.TabIndex = 0;
            this.tabPage1.Text = "tabPage1";
            this.tabPage1.UseVisualStyleBackColor = true;
            // 
            // groupBox10
            // 
            this.groupBox10.Controls.Add(this.groupBox8);
            this.groupBox10.Controls.Add(this.groupBox9);
            this.groupBox10.Location = new System.Drawing.Point(6, 6);
            this.groupBox10.Name = "groupBox10";
            this.groupBox10.Size = new System.Drawing.Size(634, 241);
            this.groupBox10.TabIndex = 12;
            this.groupBox10.TabStop = false;
            this.groupBox10.Text = "Beskrivning och Krav";
            // 
            // groupBox8
            // 
            this.groupBox8.Controls.Add(this.label4);
            this.groupBox8.Controls.Add(this.label3);
            this.groupBox8.Controls.Add(this.label2);
            this.groupBox8.Controls.Add(this.label1);
            this.groupBox8.Location = new System.Drawing.Point(6, 19);
            this.groupBox8.Name = "groupBox8";
            this.groupBox8.Size = new System.Drawing.Size(318, 210);
            this.groupBox8.TabIndex = 14;
            this.groupBox8.TabStop = false;
            this.groupBox8.Text = "Beskrivning";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(8, 142);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(251, 26);
            this.label4.TabIndex = 3;
            this.label4.Text = "Steg 4: För att se statistik om närmaste vägar som\r\nkorsar säkra platser trycks \"" +
    "Visa Statistik\"-knappen.";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(8, 106);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(243, 26);
            this.label3.TabIndex = 2;
            this.label3.Text = "Steg 3: Genom att hitta vägar från osäkra till säkra\r\nplatser trycks \"Hitta väg\"-" +
    "knappen.";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(7, 63);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(282, 39);
            this.label2.TabIndex = 1;
            this.label2.Text = "Steg 2: För att generera lager som presenterar säkra\r\noch osäkra platser ska krys" +
    "srutorna vara inbokade. Sedan\r\ntrycks \"Skapa lager\"-knappen för att skapa utvald" +
    "a lager.";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(7, 20);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(277, 39);
            this.label1.TabIndex = 0;
            this.label1.Text = "Steg 1: Lägg till höjdata och markdata (ortofoto) genom\r\natt klicka på \"Lägg till" +
    " raster\"-knappen. Sedan läggs väg-\r\nlager till genom att klicka på \"Lägg till sh" +
    "ape\"-knappen.\r\n";
            // 
            // groupBox9
            // 
            this.groupBox9.Controls.Add(this.label7);
            this.groupBox9.Controls.Add(this.label6);
            this.groupBox9.Controls.Add(this.label5);
            this.groupBox9.Location = new System.Drawing.Point(342, 19);
            this.groupBox9.Name = "groupBox9";
            this.groupBox9.Size = new System.Drawing.Size(280, 210);
            this.groupBox9.TabIndex = 15;
            this.groupBox9.TabStop = false;
            this.groupBox9.Text = "Krav";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(6, 106);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(255, 26);
            this.label7.TabIndex = 2;
            this.label7.Text = "För steg 4: Väglager måste vara valt i menyn ovanför\r\n\"Lägg till shape\"-knappen.";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(6, 58);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(271, 39);
            this.label6.TabIndex = 1;
            this.label6.Text = "För steg 3: Vid beräkning av väg från osäkra till säkra\r\nplatser. måste ortofoto " +
    "vara vald i menyn ovanför \"Lägg \r\ntill raster\"-knappen.";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(6, 16);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(261, 39);
            this.label5.TabIndex = 0;
            this.label5.Text = "För steg 2: När beräkning av säkra och osäkra platser\r\nmåste höjdata vara vald i " +
    "menyn ovanför \"Lägg till \r\nraster\"-knappen.";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.btnAddShape);
            this.groupBox1.Controls.Add(this.btnAddRaster);
            this.groupBox1.Controls.Add(this.cmbRasterList);
            this.groupBox1.Controls.Add(this.cmbShapeList);
            this.groupBox1.Location = new System.Drawing.Point(6, 253);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(324, 78);
            this.groupBox1.TabIndex = 2;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Lägg till data - Steg 1";
            // 
            // tabPage2
            // 
            this.tabPage2.Controls.Add(this.groupBox3);
            this.tabPage2.Controls.Add(this.groupBox4);
            this.tabPage2.Controls.Add(this.groupBox5);
            this.tabPage2.Controls.Add(this.groupBox6);
            this.tabPage2.Location = new System.Drawing.Point(4, 22);
            this.tabPage2.Name = "tabPage2";
            this.tabPage2.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage2.Size = new System.Drawing.Size(649, 410);
            this.tabPage2.TabIndex = 1;
            this.tabPage2.Text = "tabPage2";
            this.tabPage2.UseVisualStyleBackColor = true;
            // 
            // FloodForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(684, 461);
            this.Controls.Add(this.tabControl1);
            this.Name = "FloodForm";
            this.Text = "Information om översvämning";
            this.TopMost = true;
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            this.groupBox4.ResumeLayout(false);
            this.groupBox5.ResumeLayout(false);
            this.groupBox6.ResumeLayout(false);
            this.groupBox7.ResumeLayout(false);
            this.tabControl1.ResumeLayout(false);
            this.tabPage1.ResumeLayout(false);
            this.groupBox10.ResumeLayout(false);
            this.groupBox8.ResumeLayout(false);
            this.groupBox8.PerformLayout();
            this.groupBox9.ResumeLayout(false);
            this.groupBox9.PerformLayout();
            this.groupBox1.ResumeLayout(false);
            this.tabPage2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ComboBox cmbRasterList;
        private System.Windows.Forms.ComboBox cmbShapeList;
        private System.Windows.Forms.Button btnAddShape;
        private System.Windows.Forms.Button btnAddRaster;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Button btnCalcSlope;
        private System.Windows.Forms.Button btnCalcAspect;
        private System.Windows.Forms.Button btnClassify;
        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.Button btnCalcDem;
        private System.Windows.Forms.GroupBox groupBox4;
        private System.Windows.Forms.Button btnCalcCost;
        private System.Windows.Forms.GroupBox groupBox5;
        private System.Windows.Forms.GroupBox groupBox6;
        private System.Windows.Forms.Button btnCalcAllocation;
        private System.Windows.Forms.Button btnCalcDistance;
        private System.Windows.Forms.Button btnCalcLeastCost;
        private System.Windows.Forms.Button btnFindWay;
        private System.Windows.Forms.Button btnCalcStat;
        private System.Windows.Forms.GroupBox groupBox7;
        private System.Windows.Forms.ComboBox cmbFieldList;
        private System.Windows.Forms.CheckBox cbxSafe;
        private System.Windows.Forms.CheckBox cbxUnsafe;
        private System.Windows.Forms.Button btnCalcSpatialData;
        private System.Windows.Forms.TabControl tabControl1;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.TabPage tabPage2;
        private System.Windows.Forms.GroupBox groupBox10;
        private System.Windows.Forms.GroupBox groupBox8;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.GroupBox groupBox9;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.GroupBox groupBox1;
    }
}